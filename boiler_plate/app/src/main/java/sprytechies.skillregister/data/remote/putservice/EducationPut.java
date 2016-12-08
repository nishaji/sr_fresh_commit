package sprytechies.skillregister.data.remote.putservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
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
import sprytechies.skillregister.data.local.DatabaseHelper;
import sprytechies.skillregister.data.model.Education;
import sprytechies.skillregister.data.model.EducationInsert;
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

public class EducationPut extends Service {
    @Inject
    DatabaseHelper databaseHelper;
    private Subscription mSubscription;
    String id, access_token;


    public static Intent getStartIntent(Context context) {
        return new Intent(context, EducationPut.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.i("Service Created..");
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
        put_education();
        return START_STICKY;
    }

    private void put_education() {
        Integer integer = 0;
        RxUtil.unsubscribe(mSubscription);
        mSubscription = databaseHelper.getEducationForPut(integer)
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

                        final Date date=new Date();
                        System.out.println("education size for put call" + education.size());
                        if (education.size() == 0) {
                            System.out.println("if is running in put education service");
                        } else {
                            System.out.println("else is running in put education service ");
                            PostService service = ApiClient.getClient().create(PostService.class);
                            for (int i = 0; i < education.size(); i++) {
                                Edu user = new Edu(education.get(i));
                                Call<Edu> call = service.put_education(id,education.get(i).education().mongoid(),user,access_token );
                                final int finalI = i;
                                call.enqueue(new Callback<Edu>() {
                                    @Override
                                    public void onResponse(Call<Edu> call, Response<Edu> response) {
                                        String didItWork = String.valueOf(response.isSuccessful());
                                        Log.v("SUCCESS?", didItWork);
                                        Log.v("RESPONSE_CODE", String.valueOf(response.code()));
                                        if (response.code() == 200) {
                                            Toast.makeText(EducationPut.this, "Education update to server successfully", Toast.LENGTH_SHORT).show();
                                            databaseHelper.update_education_flag(Education.builder()
                                                    .setPutflag("1").setPostflag("1").setDate(date.toString())
                                                    .build(), education.get(finalI).education().id());
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Edu> call, Throwable t) {
                                        System.out.println("checking failure" + t.getLocalizedMessage());
                                    }
                                });
                            }
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
