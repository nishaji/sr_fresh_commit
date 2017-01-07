package sprytechies.skillregister.data.remote.postservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import java.util.List;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sprytechies.skillregister.BoilerplateApplication;
import sprytechies.skillregister.data.DataManager;
import sprytechies.skillregister.data.local.DatabaseHelper;
import sprytechies.skillregister.data.model.SaveProfileInsert;
import sprytechies.skillregister.data.remote.ApiClient;
import sprytechies.skillregister.data.remote.PostService;
import sprytechies.skillregister.data.remote.remote_model.SaveProfilePost;
import sprytechies.skillregister.ui.home.HomeActivity;
import sprytechies.skillregister.util.NetworkUtil;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;

/**
 * Created by sprydev5 on 2/12/16.
 */

public class PostProfile extends Service {

    @Inject DataManager mDataManager;
    @Inject DatabaseHelper databaseHelper;
    private Subscription mSubscription;
    String id, access_token;


    public static Intent getStartIntent(Context context) {
        return new Intent(context, PostProfile.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.i("PostProfile  Service Created..");
        BoilerplateApplication.get(this).getComponent().inject(this);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        SharedPreferences settings = this.getSharedPreferences(HomeActivity.SHARED_PREFERENCE, 0);
        id = settings.getString("id", "id");
        access_token = settings.getString("access_token", "access_token");
        System.out.println(id+" "+access_token);
        if (!NetworkUtil.isNetworkConnected(this)) {
            Timber.i("Profile Sync canceled, connection not available");
            stopSelf(startId);
            return START_NOT_STICKY;
        }
        post_profile();
        return START_NOT_STICKY;
    }

    private void post_profile() {
        RxUtil.unsubscribe(mSubscription);
        mSubscription = databaseHelper.getSavedProile()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<SaveProfileInsert>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the award for post.");
                    }
                    @Override
                    public void onNext(final List<SaveProfileInsert> profile) {
                        System.out.println("ppppppppppppppppppp"+profile);
                        if (profile.size() == 0) {
                            System.out.println("if is running in profile post service");
                        } else {
                            PostService service = ApiClient.getClient().create(PostService.class);
                            SaveProfilePost awrd = new SaveProfilePost(profile.get(0));
                                Call<SaveProfilePost> call = service.post_profile(id, access_token, awrd);
                                call.enqueue(new Callback<SaveProfilePost>() {
                                    @Override
                                    public void onResponse(Call<SaveProfilePost> call, Response<SaveProfilePost> response) {
                                        String didItWork = String.valueOf(response.isSuccessful());
                                        Log.v("SUCCESS?", didItWork);
                                        Log.v("RESPONSE_CODE", String.valueOf(response.code()));
                                        if (response.code() == 200) {
                                            Toast.makeText(PostProfile.this, "Profile send to server successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<SaveProfilePost> call, Throwable t) {
                                        System.out.println("checking failure" + t.getLocalizedMessage());

                                    }
                                });
                            }

                    }


                });
    }


    @Override
    public void onDestroy() {
        if (mSubscription != null)
            mSubscription.unsubscribe();
        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
