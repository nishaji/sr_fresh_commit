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
import sprytechies.skillregister.data.model.Certificate;
import sprytechies.skillregister.data.model.CertificateInsert;
import sprytechies.skillregister.data.model.LiveSync;
import sprytechies.skillregister.data.remote.ApiClient;
import sprytechies.skillregister.data.remote.PostService;
import sprytechies.skillregister.data.remote.remote_model.Cert;
import sprytechies.skillregister.ui.home.HomeActivity;
import sprytechies.skillregister.util.NetworkUtil;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;

/**
 * Created by sprydev5 on 23/11/16.
 */

public class CertificatePost extends Service {

    @Inject DataManager mDataManager;
    @Inject DatabaseHelper databaseHelper;
    private Subscription mSubscription;
    String id, access_token;
    Date date=new Date();
    public static Intent getStartIntent(Context context) {
        return new Intent(context, CertificatePost.class);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.i("Certificate Service Created..");
        BoilerplateApplication.get(this).getComponent().inject(this);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        SharedPreferences settings = this.getSharedPreferences(HomeActivity.SHARED_PREFERENCE, 0);
        id = settings.getString("id", "id");
        access_token = settings.getString("access_token", "access_token");
        if (!NetworkUtil.isNetworkConnected(this)) {
            Timber.i("Certificate Sync canceled, connection not available");
            stopSelf(startId);
            return START_NOT_STICKY;
        }
        post_certificate();
        return START_STICKY;
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

    private void post_certificate() {
        Integer integer = 0;
        RxUtil.unsubscribe(mSubscription);
        mSubscription = databaseHelper.getCertificateForPost(integer)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<CertificateInsert>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the certificate in post call.");
                    }
                    @Override
                    public void onNext(final List<CertificateInsert> certificate) {
                        if (certificate.size() == 0) {
                        } else {
                            PostService service = ApiClient.getClient().create(PostService.class);
                            for (int i = 0; i < certificate.size(); i++) {
                                Cert cert = new Cert(certificate.get(i));
                                Call<Cert> call = service.post_certificate(id, access_token, cert);
                                final int finalI = i;
                                call.enqueue(new Callback<Cert>() {
                                    @Override
                                    public void onResponse(Call<Cert> call, Response<Cert> response) {
                                        Log.v("RESPONSE_CODE", String.valueOf(response.code()));
                                        if (response.code() == 200) {
                                            String id = response.body().getId();
                                            System.out.println("certificate send to server successfully");
                                            Toast.makeText(CertificatePost.this, "certificate send to server successfully", Toast.LENGTH_SHORT).show();
                                            databaseHelper.update_certificate_flag(Certificate.builder().setPostflag("1").setDate(date.toString()).setMongoid(id)
                                                    .build(), certificate.get(finalI).certificate().id());
                                            databaseHelper.setSyncstatus(LiveSync.builder().setBit("certificate").setPost("1").build());
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Cert> call, Throwable t) {
                                        System.out.println("checking failure" + t.getLocalizedMessage());
                                    }
                                });
                            }
                        }

                    }
                });
    }


}
