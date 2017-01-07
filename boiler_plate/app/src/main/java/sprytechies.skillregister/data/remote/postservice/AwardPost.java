package sprytechies.skillregister.data.remote.postservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
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

public class AwardPost extends Service {

    @Inject DataManager mDataManager;
    @Inject DatabaseHelper databaseHelper;
    private Subscription mSubscription;
    String id, access_token,awards;
    Date date=new Date();
    List<String>award_id=new ArrayList<>();

    public static Intent getStartIntent(Context context) {
        return new Intent(context, AwardPost.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.i("Award Service Created..");
        BoilerplateApplication.get(this).getComponent().inject(this);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        SharedPreferences settings = this.getSharedPreferences(HomeActivity.SHARED_PREFERENCE, 0);
        id = settings.getString("id", "id");
        access_token = settings.getString("access_token", "access_token");
        System.out.println(access_token+" "+id);
        if (!NetworkUtil.isNetworkConnected(this)) {
            Timber.i("Award Sync canceled, connection not available");
            stopSelf(startId);
            return START_NOT_STICKY;
        }
        post_award();
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

    private void post_award() {
        System.out.println("1");
        Integer integer = 0;
        RxUtil.unsubscribe(mSubscription);
        mSubscription = databaseHelper.getAwardForPost(integer)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<AwardInsert>>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("on complete");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the award for post.");
                        System.out.println("3");
                    }
                    @Override
                    public void onNext(final List<AwardInsert> award) {
                        System.out.println("award size" + award);
                        System.out.println("on next");
                        if (award.size() == 0) {
                            System.out.println("if is running in award post service");
                            System.out.println("5");
                        } else {
                            PostService service = ApiClient.getClient().create(PostService.class);
                            for (int i = 0; i < award.size(); i++) {
                                System.out.println("loop started"+i);
                                award_id.add(award.get(i).award().id());
                                Awrd awrd = new Awrd(award.get(i));
                                Call<Awrd> call = service.post_award(id, access_token, awrd);
                                call.enqueue(new Callback<Awrd>() {
                                    @Override
                                    public void onResponse(Call<Awrd> call, Response<Awrd> response) {
                                        Log.v("RESPONSE_CODE", String.valueOf(response.code()));
                                        if (response.code() == 200) {
                                            System.out.println("in response");
                                            String id = response.body().getId();
                                            Toast.makeText(AwardPost.this, "Award send to server successfully........", Toast.LENGTH_SHORT).show();
                                              // databaseHelper.update_award_flag(Award.builder().setPostflag("1").setDate(date.toString()).setMongoid(id).build(), award.get(finalI).award().id());
                                            // databaseHelper.setSyncstatus(LiveSync.builder().setBit("award").setPost("1").build());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Awrd> call, Throwable t) {
                                        System.out.println("checking failure" + t.getLocalizedMessage());

                                    }
                                });

                            }
                            System.out.println("loop ended");
                        }
                    }


                });


    }
}
