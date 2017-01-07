package sprytechies.skillregister.data.remote.postservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import java.util.Arrays;
import java.util.Date;
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
import sprytechies.skillregister.data.model.Education;
import sprytechies.skillregister.data.model.EducationInsert;
import sprytechies.skillregister.data.model.LiveSync;
import sprytechies.skillregister.data.remote.ApiClient;
import sprytechies.skillregister.data.remote.PostService;
import sprytechies.skillregister.data.remote.remote_model.Edu;
import sprytechies.skillregister.ui.home.HomeActivity;
import sprytechies.skillregister.util.NetworkUtil;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;

/**
 * Created by sprydev5 on 23/11/16.
 */

public class EducationPost extends Service {

    @Inject DataManager mDataManager;
    @Inject DatabaseHelper databaseHelper;
    private Subscription mSubscription;
    String id, access_token;
    Date date=new Date();
    public static Intent getStartIntent(Context context) {
        return new Intent(context, EducationPost.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.i("Education Service Created..");
        BoilerplateApplication.get(this).getComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        SharedPreferences settings = this.getSharedPreferences(HomeActivity.SHARED_PREFERENCE, 0);
        id = settings.getString("id", "id");
        access_token = settings.getString("access_token", "access_token");
        System.out.println("access-token" + " " + access_token + "id" + " " + id);
        if (!NetworkUtil.isNetworkConnected(this)) {
            Timber.i("Sync canceled, connection not available");
            stopSelf(startId);
            return START_NOT_STICKY;
        }
        post_education();
        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        if (mSubscription != null)
            mSubscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void post_education() {

        Integer integer = 0;
        RxUtil.unsubscribe(mSubscription);
        mSubscription = databaseHelper.getEducationForPost(integer)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<EducationInsert>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the education.");
                    }
                    @Override
                    public void onNext(final List<EducationInsert> education) {
                        System.out.println("education size" + education.size());

                        if (education.size() == 0) {
                            System.out.println("if is running in service");
                        } else {

                            System.out.println("else is running in service ");
                            PostService service = ApiClient.getClient().create(PostService.class);
                            for (int i = 0; i < education.size(); i++) {
                                Edu user = new Edu(education.get(i));
                                Call<Edu> call = service.post_education(id, access_token, user);
                                final int finalI = i;
                                call.enqueue(new Callback<Edu>() {
                                    @Override
                                    public void onResponse(Call<Edu> call, Response<Edu> response) {
                                        String didItWork = String.valueOf(response.isSuccessful());
                                        Log.v("SUCCESS?", didItWork);
                                        Log.v("RESPONSE_CODE", String.valueOf(response.code()));
                                        if (response.code() == 200) {
                                            String id = response.body().getId();
                                            System.out.println("education send to server successfully");
                                            Toast.makeText(EducationPost.this, "Education send to server successfully", Toast.LENGTH_SHORT).show();
                                            databaseHelper.update_education_flag(Education.builder().setPostflag("1").setDate(date.toString()).setMongoid(id).build(), education.get(finalI).education().id());
                                           // databaseHelper.setSyncstatus(LiveSync.builder().setBit("education").setPost("1").build());
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Edu> call, Throwable t) {
                                        System.out.println("checking failure" + t.getMessage() + Arrays.toString(t.getStackTrace()));
                                        System.out.println("checking failure" + t.getLocalizedMessage());
                                    }
                                });
                            }
                        }
                    }
                });

    }


}
