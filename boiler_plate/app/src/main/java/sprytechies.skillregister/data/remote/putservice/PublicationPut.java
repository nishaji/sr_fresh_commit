package sprytechies.skillregister.data.remote.putservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import java.util.Arrays;
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
import sprytechies.skillregister.data.model.Publication;
import sprytechies.skillregister.data.model.PublicationInsert;
import sprytechies.skillregister.data.remote.ApiClient;
import sprytechies.skillregister.data.remote.PostService;
import sprytechies.skillregister.data.remote.remote_model.Pub;
import sprytechies.skillregister.ui.home.HomeActivity;
import sprytechies.skillregister.util.NetworkUtil;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;

/**
 * Created by sprydev5 on 23/11/16.
 */

public class PublicationPut extends Service {

    @Inject DatabaseHelper databaseHelper;
    private Subscription mSubscription;
    String id, access_token;


    public static Intent getStartIntent(Context context) {
        return new Intent(context, PublicationPut.class);
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
        put_publication();
        return START_STICKY;
    }

    private void put_publication() {
        Integer integer = 0;
        RxUtil.unsubscribe(mSubscription);
        mSubscription = databaseHelper.getPublicationForPut(integer).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<PublicationInsert>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the certificate in post call.");
                    }
                    @Override
                    public void onNext(final List<PublicationInsert> publication) {
                        final Date date=new Date();
                        System.out.println("publication size" + publication.size());
                        if (publication.size() == 0) {
                            System.out.println("if is running in service");
                        } else {
                            System.out.println("else is running in service ");
                            PostService service = ApiClient.getClient().create(PostService.class);
                            for (int i = 0; i < publication.size(); i++) {
                                Pub cert = new Pub(publication.get(i));
                                Call<Pub> call = service.put_publication(id,publication.get(i).publication().mongoid(),cert,access_token);
                                final int finalI = i;
                                call.enqueue(new Callback<Pub>() {
                                    @Override
                                    public void onResponse(Call<Pub> call, Response<Pub> response) {
                                        String didItWork = String.valueOf(response.isSuccessful());
                                        Log.v("SUCCESS?", didItWork);
                                        Log.v("RESPONSE_CODE", String.valueOf(response.code()));
                                        if (response.code() == 200) {
                                            Toast.makeText(PublicationPut.this, "publication update to server successfully", Toast.LENGTH_SHORT).show();
                                            databaseHelper.update_publication_flag(Publication.builder()
                                                    .setPutflag("1").setPostflag("1").setDate(date.toString())
                                                    .setDate(date.toString())
                                                    .build(), publication.get(finalI).publication().id());
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Pub> call, Throwable t) {
                                        System.out.println("checking failure" + t.getMessage() + Arrays.toString(t.getStackTrace()));
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
