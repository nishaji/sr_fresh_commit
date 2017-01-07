package sprytechies.skillregister.data.remote.putservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
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
import sprytechies.skillregister.data.model.Award;
import sprytechies.skillregister.data.model.AwardInsert;
import sprytechies.skillregister.data.remote.ApiClient;
import sprytechies.skillregister.data.remote.PostService;
import sprytechies.skillregister.data.remote.remote_model.Awrd;
import sprytechies.skillregister.ui.home.HomeActivity;
import sprytechies.skillregister.util.NetworkUtil;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;

/**
 * Created by sprydev5 on 23/11/16.
 */

public class AwardPut extends Service {


    @Inject DatabaseHelper databaseHelper;
    private Subscription mSubscription;
    String id, access_token;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, AwardPut.class);
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
        put_award();
        return START_STICKY;
    }

    private void put_award() {
        Integer integer = 0;
        RxUtil.unsubscribe(mSubscription);
        mSubscription = databaseHelper.getAwardForPost(integer)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<AwardInsert>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the award for post.");
                    }
                    @Override
                    public void onNext(final List<AwardInsert> award) {
                        final Date date=new Date();
                        System.out.println("award size" + award.size());
                        if (award.size() == 0) {
                            System.out.println("if is running in award post service");
                        } else {
                            PostService service = ApiClient.getClient().create(PostService.class);
                            for (int i = 0; i < award.size(); i++) {
                                Awrd awrd = new Awrd(award.get(i));
                                Call<Awrd> call = service.put_awards(id,award.get(i).award().mongoid(),awrd, access_token);
                                final int finalI = i;
                                call.enqueue(new Callback<Awrd>() {
                                    @Override
                                    public void onResponse(Call<Awrd> call, Response<Awrd> response) {
                                        String didItWork = String.valueOf(response.isSuccessful());
                                        Log.v("SUCCESS?", didItWork);
                                        Log.v("RESPONSE_CODE", String.valueOf(response.code()));
                                        if (response.code() == 200) {
                                            Toast.makeText(AwardPut.this, "award update to server successfully", Toast.LENGTH_SHORT).show();
                                            databaseHelper.update_award_flag(Award.builder()
                                                    .setPutflag("1").setPostflag("1").setDate(date.toString())
                                                    .build(), award.get(finalI).award().id());
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Awrd> call, Throwable t) {
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


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}