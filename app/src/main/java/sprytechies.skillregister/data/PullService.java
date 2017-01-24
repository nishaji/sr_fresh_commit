package sprytechies.skillregister.data;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
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
import sprytechies.skillregister.data.model.LiveSync;
import sprytechies.skillregister.data.model.LiveSyncinsert;
import sprytechies.skillregister.data.remote.ApiClient;
import sprytechies.skillregister.data.remote.PostService;
import sprytechies.skillregister.data.remote.remote_model.Awrd;
import sprytechies.skillregister.ui.home.HomeActivity;
import sprytechies.skillregister.util.AndroidComponentUtil;
import sprytechies.skillregister.util.NetworkUtil;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;


public class PullService  extends Service {

    @Inject DataManager mDataManager;
    @Inject DatabaseHelper databaseHelper;
    private Subscription mSubscription;
    String id,access_token;
    ArrayList<String> ski;
    ArrayList<String> lan;
    String profile_id;
   String sq_mongo,web_mongo;
    JSONObject parse_award=new JSONObject();
    JSONObject parse_web_award=new JSONObject();
    JSONObject sq_awrd=new JSONObject();
    JSONObject parse_sq_awrd=new JSONObject();
    JSONArray award_array=new JSONArray();
    JSONArray sq_award_array=new JSONArray();

    public static Intent getStartIntent(Context context) {
        return new Intent(context, PullService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.i(" Pull Service Created..");
        BoilerplateApplication.get(this).getComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        SharedPreferences settings = this.getSharedPreferences(HomeActivity.SHARED_PREFERENCE, 0);
        id = settings.getString("id", "id");
        access_token = settings.getString("access_token", "access_token");
        System.out.println("access-token in pull service" + " " + access_token + "id" + " " + id);
        if (!NetworkUtil.isNetworkConnected(this)) {
            Timber.i("Sync canceled, connection not available");
            AndroidComponentUtil.toggleComponent(this, PullService.SyncOnConnectionAvailable.class, true);
            stopSelf(startId);
            return START_NOT_STICKY;
        }
        pull_award();
        return START_NOT_STICKY;
    }

    private void pull_award() {
        PostService service = ApiClient.getClient().create(PostService.class);
        Call<List<Awrd>> call = service.list_award(id, access_token);
        call.enqueue(new Callback<List<Awrd>>() {
            @Override
            public void onResponse(Call<List<Awrd>> call, Response<List<Awrd>> response) {
                Log.v("ProjectPullRESPONSECODE", String.valueOf(response.code()));
                if (response.code() == 200) {
                    List<Awrd> award = response.body();
                      award_array = new JSONArray(new ArrayList<String>());
                        for(int i=0; i<award.size();i++){
                            sq_awrd=new JSONObject();
                            try {
                                sq_awrd.put("title",award.get(i).getTitle());
                                sq_awrd.put("org",award.get(i).getOrg());
                                sq_awrd.put("des",award.get(i).getDesc());
                                sq_awrd.put("date",award.get(i).getDate());
                                sq_awrd.put("mongo_id",award.get(i).getId());
                                award_array.put(sq_awrd);

                            } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ab();

                }
            }

            @Override
            public void onFailure(Call<List<Awrd>> call, Throwable t) {
                System.out.println("checking failure" + t.getMessage() + Arrays.toString(t.getStackTrace()));
                System.out.println("checking failure" + t.getLocalizedMessage());

            }
        });


    }
    public void ab() {
        System.out.println("ab");
        RxUtil.unsubscribe(mSubscription);
        mSubscription = databaseHelper.getLiveSync().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Subscriber<List<LiveSyncinsert>>() {
                    @Override
                    public void onCompleted() {
                        update();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the education.");
                    }

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onNext(final List<LiveSyncinsert> sync) {
                        System.out.println("ab"+sync);
                        sq_award_array = new JSONArray(new ArrayList<String>());
                        for (int i = 0; i < sync.size(); i++) {
                            try {
                                parse_award = new JSONObject(sync.get(i).liveSync().bitbefore());
                                sq_award_array.put(parse_award);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                });
comapare_date();

    }
    public void comapare_date(){

       for (int i = 0; i < award_array.length(); i++) {
            System.out.println(i + "iiiiiiiiiiiiiiii");
            try {
                parse_web_award=award_array.getJSONObject(i);
                web_mongo=parse_web_award.getString("mongo_id");
                 System.out.println(web_mongo+"web");
                // System.out.println("that"+parse_web_award);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int j = 0; j < sq_award_array.length(); j++) {
                System.out.println(j + "jjjjjjjjjjjjjjjjjjjj");
                try {
                    parse_sq_awrd=sq_award_array.getJSONObject(j);
                    sq_mongo=parse_sq_awrd.getString("mongo_id");
                    System.out.println(sq_mongo+"sqlite"+sq_mongo.equals(web_mongo));
                    if(sq_mongo.equals(web_mongo)){
                        System.out.println("this");
                    }
                    else {
                        System.out.println("that");
                        //System.out.println("that"+web_mongo);
                        // System.out.println("that"+parse_web_award);
                        //  databaseHelper.setSyncstatus(LiveSync.builder().setBitmongoid(web_mongo).setBitbefore(parse_web_award.toString()).build());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }
    }
    public void update(){
        System.out.println("fsddddddddddddd");
    }
    @Override
    public void onDestroy() {
        if (mSubscription != null) mSubscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static class SyncOnConnectionAvailable extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)
                    && NetworkUtil.isNetworkConnected(context)) {
                Timber.i("Connection is now available, triggering sync...");
                AndroidComponentUtil.toggleComponent(context, this.getClass(), false);
                context.startService(getStartIntent(context));
            }
        }
    }
}
