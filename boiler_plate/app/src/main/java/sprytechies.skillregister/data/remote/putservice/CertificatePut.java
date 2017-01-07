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
import sprytechies.skillregister.data.model.Certificate;
import sprytechies.skillregister.data.model.CertificateInsert;
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

public class CertificatePut extends Service {

    @Inject DatabaseHelper databaseHelper;
    private Subscription mSubscription;
    String id, access_token;


    public static Intent getStartIntent(Context context) {
        return new Intent(context, CertificatePut.class);
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
        put_certificate();
        return START_STICKY;
    }

    private void put_certificate() {
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

                        final Date date=new Date();
                        System.out.println("certificate size" + certificate.size());
                        if (certificate.size() == 0) {
                            System.out.println("if is running in service");
                        } else {
                            System.out.println("else is running in service ");
                            PostService service = ApiClient.getClient().create(PostService.class);
                            for (int i = 0; i < certificate.size(); i++) {
                                Cert cert = new Cert(certificate.get(i));
                                Call<Cert> call = service.put_certificate(id,certificate.get(i).certificate().mongoid(),cert, access_token);
                                final int finalI = i;
                                call.enqueue(new Callback<Cert>() {
                                    @Override
                                    public void onResponse(Call<Cert> call, Response<Cert> response) {
                                        String didItWork = String.valueOf(response.isSuccessful());
                                        Log.v("SUCCESS?", didItWork);
                                        Log.v("RESPONSE_CODE", String.valueOf(response.code()));
                                        if (response.code() == 200) {
                                            Toast.makeText(CertificatePut.this, "certificate update to server successfully", Toast.LENGTH_SHORT).show();
                                            databaseHelper.update_certificate_flag(Certificate.builder()
                                                    .setPutflag("1").setPostflag("1").setDate(date.toString())
                                                    .build(), certificate.get(finalI).certificate().id());
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
