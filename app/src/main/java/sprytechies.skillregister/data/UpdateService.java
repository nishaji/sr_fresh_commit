package sprytechies.skillregister.data;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.IBinder;
import javax.inject.Inject;
import rx.Subscription;
import sprytechies.skillregister.BoilerplateApplication;
import sprytechies.skillregister.data.local.DatabaseHelper;
import sprytechies.skillregister.data.remote.putservice.AwardPut;
import sprytechies.skillregister.data.remote.putservice.CertificatePut;
import sprytechies.skillregister.data.remote.putservice.EducationPut;
import sprytechies.skillregister.data.remote.putservice.ExperiencePut;
import sprytechies.skillregister.data.remote.putservice.ProjectPut;
import sprytechies.skillregister.data.remote.putservice.PublicationPut;
import sprytechies.skillregister.data.remote.putservice.contactPut;
import sprytechies.skillregister.util.AndroidComponentUtil;
import sprytechies.skillregister.util.NetworkUtil;
import timber.log.Timber;

/**
 * Created by sprydev5 on 9/11/16.
 */

public class UpdateService extends Service {
    private Subscription mSubscription;
    public static Intent getStartIntent(Context context) {
        return new Intent(context, UpdateService.class);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.i(" Update Service Created..");
        BoilerplateApplication.get(this).getComponent().inject(this);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        if (!NetworkUtil.isNetworkConnected(this)) {
            Timber.i("Sync canceled, connection not available");
            AndroidComponentUtil.toggleComponent(this, UpdateService.SyncOnConnectionAvailable.class, true);
            stopSelf(startId);
            return START_NOT_STICKY;
        }
        startService(AwardPut.getStartIntent(this));
        startService(EducationPut.getStartIntent(this));
        startService(ExperiencePut.getStartIntent(this));
        startService(ProjectPut.getStartIntent(this));
        startService(contactPut.getStartIntent(this));
        startService(CertificatePut.getStartIntent(this));
        startService(PublicationPut.getStartIntent(this));


        return START_STICKY;
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
