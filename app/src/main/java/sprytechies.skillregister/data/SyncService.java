package sprytechies.skillregister.data;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sprytechies.skillregister.BoilerplateApplication;
import sprytechies.skillregister.data.local.DatabaseHelper;
import sprytechies.skillregister.data.model.LiveSyncinsert;
import sprytechies.skillregister.data.remote.postservice.AwardPost;
import sprytechies.skillregister.data.remote.postservice.CertificatePost;
import sprytechies.skillregister.data.remote.postservice.ContactPost;
import sprytechies.skillregister.data.remote.postservice.EducationPost;
import sprytechies.skillregister.data.remote.postservice.ExperiencePost;
import sprytechies.skillregister.data.remote.postservice.PostProfile;
import sprytechies.skillregister.data.remote.postservice.ProjectPost;
import sprytechies.skillregister.util.AndroidComponentUtil;
import sprytechies.skillregister.util.NetworkUtil;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;

public class SyncService extends Service {

    @Inject DataManager mDataManager;
    @Inject DatabaseHelper databaseHelper;
    private Subscription mSubscription;
    String bit_type="project",status="0";

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SyncService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.i("Service Created..");
        BoilerplateApplication.get(this).getComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {

        if (!NetworkUtil.isNetworkConnected(this)) {
            Timber.i("Sync canceled, connection not available");
            AndroidComponentUtil.toggleComponent(this, SyncOnConnectionAvailable.class, true);
            stopSelf(startId);
            return START_NOT_STICKY;
        }
        /*RxUtil.unsubscribe(mSubscription);
        mSubscription = databaseHelper.getLiveSync()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<LiveSyncinsert>>() {
                @Override public void onCompleted() {}
                @Override public void onError(Throwable e) {}
                @Override public void onNext(final List<LiveSyncinsert> livesync) {
                    if(livesync.size()==0){}else{
                        for(int j=0; j<livesync.size();j++){
                            System.out.println(livesync.get(j).liveSync().post()+"live sync size");
                            if(bit_type.equals(livesync.get(j).liveSync().bit()) && status.equals(livesync.get(j).liveSync().post())){
                                startService(ProjectPost.getStartIntent(SyncService.this));
                                System.out.println("project post is calling");
                                j=livesync.size();
                            }else {
                                System.out.println("project post service is not running"+livesync.get(j).liveSync().post());
                            }
                        }

                    }
                }});*/
        //  startService(AwardPost.getStartIntent(this));
        startService(EducationPost.getStartIntent(this));
         // startService(ExperiencePost.getStartIntent(this));
         //startService(ProjectPost.getStartIntent(this));

         // startService(ContactPost.getStartIntent(this));
         // startService(CertificatePost.getStartIntent(this));
          //startService(PostProfile.getStartIntent(this));
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