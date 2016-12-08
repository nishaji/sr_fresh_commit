package sprytechies.skillregister.data.remote.postservice;

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
import sprytechies.skillregister.data.DataManager;
import sprytechies.skillregister.data.local.DatabaseHelper;
import sprytechies.skillregister.data.model.LiveSync;
import sprytechies.skillregister.data.model.Project;
import sprytechies.skillregister.data.model.ProjectInsert;
import sprytechies.skillregister.data.remote.ApiClient;
import sprytechies.skillregister.data.remote.PostService;
import sprytechies.skillregister.data.remote.remote_model.Pro;
import sprytechies.skillregister.ui.home.HomeActivity;
import sprytechies.skillregister.util.NetworkUtil;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;

/**
 * Created by sprydev5 on 23/11/16.
 */

public class ProjectPost extends Service {

    @Inject DataManager mDataManager;
    @Inject DatabaseHelper databaseHelper;
    private Subscription mSubscription;
    String id, access_token;
    Date date=new Date();
    public static Intent getStartIntent(Context context) {
        return new Intent(context, ProjectPost.class);
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
        post_project();

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
        Integer integer = 0;
        RxUtil.unsubscribe(mSubscription);
        mSubscription = databaseHelper.getProjectForPost(integer)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<ProjectInsert>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the projecr in post call.");
                    }
                    @Override
                    public void onNext(final List<ProjectInsert> project) {

                        if (project.size() == 0) {
                            System.out.println("no project date to post");
                        } else {

                            for(int i=0; i<=project.size(); i++){
                                PostService apiService = ApiClient.getClient().create(PostService.class);
                                Pro pro = new Pro(project.get(i));
                                Call<Pro> call = apiService.post_project(id, access_token, pro);
                                final int finalI = i;
                                call.enqueue(new Callback<Pro>() {
                                    @Override
                                    public void onResponse(Call<Pro> call, Response<Pro> response) {
                                        String didItWork = String.valueOf(response.isSuccessful());
                                        Log.v("SUCCESS?", didItWork);
                                        Log.v("RESPONSE_CODE", String.valueOf(response.code()));
                                        if(response.code()==200){
                                            String id = response.body().getId();
                                            System.out.println("project send to server successfully");
                                            Toast.makeText(ProjectPost.this, "project send to server successfully", Toast.LENGTH_SHORT).show();
                                            databaseHelper.update_project_flag(Project.builder().setPostflag("1").setDate(date.toString()).setMongoid(id).build(), project.get(finalI).project().id());
                                            databaseHelper.setSyncstatus(LiveSync.builder().setBit("project").setPost("1").build());
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Pro> call, Throwable t) {

                                    }
                                });
                            }
                        }
                    }
                });
    }

}
