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
import sprytechies.skillregister.data.model.ProjectInsert;
import sprytechies.skillregister.data.remote.ApiClient;
import sprytechies.skillregister.data.remote.PostService;
import sprytechies.skillregister.data.remote.remote_model.Pro;
import sprytechies.skillregister.ui.home.HomeActivity;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;

public class ProjectPost extends Service {

    @Inject DataManager mDataManager;@Inject DatabaseHelper databaseHelper;
    private Subscription mSubscription;
    String id, access_token,bit_type="project",status="1",pending="0";
    ArrayList<String>id_list;
    Date date=new Date();
    public static Intent getStartIntent(Context context) {
        return new Intent(context, ProjectPost.class);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.i("ProjectPost Service Created..");
        BoilerplateApplication.get(this).getComponent().inject(this);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        SharedPreferences settings = this.getSharedPreferences(HomeActivity.SHARED_PREFERENCE, 0);
        id = settings.getString("id", "id");
        access_token = settings.getString("access_token", "access_token");
        post();
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

    private void post_project() {

  /*      RxUtil.unsubscribe(mSubscription);
        mSubscription = databaseHelper.getLiveSync()
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Subscriber<List<LiveSyncinsert>>() {
                    @Override public void onCompleted() {}
                    @Override public void onError(Throwable e) {Timber.e(e, "There was an error loading the project in post call.");}
                    @Override public void onNext(final List<LiveSyncinsert> livesync) {
                        if(livesync.size()==0){
                        }else{
                            id_list=new ArrayList<String>();
                            for(int j=0;j<livesync.size();j++){
                                id_list.add(livesync.get(j).liveSync().id());
                             *//*  if(bit_type.equals(livesync.get(j).liveSync().bit()) && status.equals(livesync.get(j).liveSync().post())){
                                    System.out.println("updating post flag");
                                    databaseHelper.update_project_flag(Project.builder().setPostflag("1").setDate(date.toString()).setMongoid(livesync.get(j).liveSync().bitmongoid())
                                            .build(), livesync.get(j).liveSync().bitid());
                                } else{System.out.println("eeeeeeeeeeeeeeeeeee");}}*//*
                            post();

                        }
                    }
                });*/
    }
public void post(){
    System.out.println("calling post method");
     Integer integer=0;
    RxUtil.unsubscribe(mSubscription);
    mSubscription = databaseHelper.getProjectForPost(integer).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe(new Subscriber<List<ProjectInsert>>() {
                @Override public void onCompleted() {}
                @Override public void onError(Throwable e) {Timber.e(e, "There was an error loading the project in post call.");}
                @Override public void onNext(final List<ProjectInsert> project) {
                    if (project.size() == 0) {
                        System.out.println("no project date to post");
                    } else {
                        PostService apiService = ApiClient.getClient().create(PostService.class);
                        for(int i=0; i<project.size(); i++){
                            System.out.println("in else loop");
                            Pro pro = new Pro(project.get(i));
                            System.out.println("in else loop stage 1");
                            Call<Pro> call = apiService.post_project(id, access_token, pro);
                            System.out.println("in else loop stage 2");
                            final Integer finalI=i;
                            call.enqueue(new Callback<Pro>() {
                                @Override
                                public void onResponse(Call<Pro> call, Response<Pro> response) {
                                    System.out.println("in else loop stage 3");
                                    Log.v("RESPONSE_CODE", String.valueOf(response.code()));
                                    if(response.code()==200){
                                        String id = response.body().getId();
                                      //  String live_id=id_list.get(finalI);
                                        System.out.println(response.body().toString()+"response body");
                                        System.out.println(project.toString()+"project send to server successfully");
                                        Toast.makeText(ProjectPost.this, "project send to server successfully", Toast.LENGTH_SHORT).show();
                                       // databaseHelper.setSyncstatus(LiveSync.builder().setBit("project").setBitbefore("1").setBitid(project.get(finalI).project().id()).setBitmongoid(id).build());
                                    }
                                }
                                @Override
                                public void onFailure(Call<Pro> call, Throwable t) {System.out.println("in else loop stage 5");}
                            });System.out.println("in before ending loop stage ");
                        }System.out.println("in else loop stage 5 loop end");
                    }System.out.println("in else loop stage 6");
                }});
}
}
