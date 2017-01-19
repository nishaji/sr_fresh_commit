package sprytechies.skillregister.data.remote.postservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
import sprytechies.skillregister.data.PullService;
import sprytechies.skillregister.data.local.DatabaseHelper;
import sprytechies.skillregister.data.model.Award;
import sprytechies.skillregister.data.model.AwardInsert;
import sprytechies.skillregister.data.model.Certificate;
import sprytechies.skillregister.data.model.LiveSync;
import sprytechies.skillregister.data.model.LiveSyncinsert;
import sprytechies.skillregister.data.remote.ApiClient;
import sprytechies.skillregister.data.remote.PostService;
import sprytechies.skillregister.data.remote.remote_model.Awrd;
import sprytechies.skillregister.data.remote.remote_model.Cert;
import sprytechies.skillregister.ui.home.HomeActivity;
import sprytechies.skillregister.util.NetworkUtil;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;


public class AwardPost extends Service {

    @Inject DataManager mDataManager;
    @Inject DatabaseHelper databaseHelper;
    private Subscription mSubscription;
    String id, access_token;
    Date date = new Date();


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
        System.out.println(access_token + " " + id);
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
        final String mongo="mongo";
        RxUtil.unsubscribe(mSubscription);
        mSubscription = databaseHelper.getLiveSync().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Subscriber<List<LiveSyncinsert>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the education.");
                    }
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onNext(final List<LiveSyncinsert> sync) {

                        String before,bit="mongo";
                        if(sync.size()==0){
                            System.out.println("hhhhhhhhhhhhhhhhhhhh"+sync.size());
                           // databaseHelper.update_award_flag(Award.builder().build(),);
                           // call_pull();
                        }else{
                            for(int i=0;i<sync.size();i++){
                                before=sync.get(i).liveSync().bitmongoid();
                                if (Objects.equals(before, bit)) {
                                    System.out.println("in if");
                                    RxUtil.unsubscribe(mSubscription);
                                    String mongo="mongo";
                                    mSubscription = databaseHelper.getAwardForPost(mongo)
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
                                                    System.out.println("award" + award);
                                                    PostService service = ApiClient.getClient().create(PostService.class);
                                                    for (int i = 0; i < award.size(); i++) {
                                                        Awrd cert = new Awrd(award.get(i));
                                                        Call<Awrd> call = service.post_award(id, access_token, cert);
                                                        final int ii=i;
                                                        call.enqueue(new Callback <Awrd>() {
                                                            @Override
                                                            public void onResponse(Call <Awrd> call, Response <Awrd> response) {
                                                                Log.v("RESPONSE_CODE", String.valueOf(response.code()));
                                                                if (response.code() == 200) {
                                                                    String mongo_id=response.body().getId();
                                                                    databaseHelper.upDatesyncstatus(LiveSync.builder().setBit("award").setBitmongoid(mongo_id).setBitafter(mongo_id).setBitbefore(mongo_id).setBitid(award.get(ii).award().id()).build(),sync.get(ii).liveSync().id());
                                                                    System.out.println("Award send to server successfully");
                                                                    Toast.makeText(AwardPost.this, "Award send to server successfully", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call <Awrd> call, Throwable t) {
                                                                System.out.println("checking failure" + t.getLocalizedMessage());
                                                            }
                                                        });
                                                    }
                                                }
                                            });



                                }else {
                                    System.out.println("in else"+sync.size());
                                    for(int j=0; j<sync.size();j++){
                                        databaseHelper.update_award_flag(Award.builder().setMongoid(sync.get(j).liveSync().bitmongoid()).build(),sync.get(j).liveSync().bitid());
                                    }
                                }
                            }

                        }

                    }
                        });

    }
    public void call_pull(){
        PostService service = ApiClient.getClient().create(PostService.class);
        Call<List<Awrd>> call = service.list_award(id, access_token);
        call.enqueue(new Callback<List<Awrd>>() {
            @Override
            public void onResponse(Call<List<Awrd>> call, Response<List<Awrd>> response) {
                Log.v("ProjectPullRESPONSECODE", String.valueOf(response.code()));
                if (response.code() == 200) {
                    List<Awrd> awards = response.body();
                    databaseHelper.flush_all_awards(Award.builder().build());
                    for (int i = 0; i < awards.size(); i++) {
                        databaseHelper.setAwards(Award.builder()
                                .setDescription(awards.get(i).getDesc()).setDuration(awards.get(i).getDate().toString())
                                .setOrganisation(awards.get(i).getOrg()).setTitle(awards.get(i).getTitle())
                                .setMongoid(awards.get(i).getId()).setDate(date.toString()).build());
                       // databaseHelper.setSyncstatus(LiveSync.builder().setBit("award").setBitmongoid(awards.get(i).getId()).setBitafter(awards.get(i).getId()).build());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Awrd>> call, Throwable t) {
                System.out.println("checking failure" + t.getMessage() + Arrays.toString(t.getStackTrace()));
                System.out.println("checking failure" + t.getLocalizedMessage());

            }
        });
    }

}















