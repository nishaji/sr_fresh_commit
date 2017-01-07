package sprytechies.skillregister.ui.launcher.Exportpdf;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sprytechies.skillregister.R;
import sprytechies.skillregister.data.SyncService;
import sprytechies.skillregister.data.UpdateService;
import sprytechies.skillregister.data.local.DatabaseHelper;
import sprytechies.skillregister.data.model.EducationInsert;
import sprytechies.skillregister.data.model.LiveSyncinsert;
import sprytechies.skillregister.data.remote.ApiClient;
import sprytechies.skillregister.data.remote.PostService;
import sprytechies.skillregister.data.remote.remote_model.ExportResume;
import sprytechies.skillregister.ui.base.BaseActivity;
import sprytechies.skillregister.ui.home.HomeActivity;
import sprytechies.skillregister.ui.launcher.activity.ViewActivity;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;


/**
 * Created by sprydev5 on 8/11/16.
 */

public class ExportPdf extends BaseActivity implements View.OnClickListener {
   int MY_PERMISSIONS_REQUEST_READ_CONTACTS ;
    @BindView(R.id.button1)Button one;
    @BindView(R.id.button2)Button two;
    @BindView(R.id.export_pdf_tool)Toolbar toolbar;
    String id,access_token;
    @Inject DatabaseHelper databaseHelper;
    private Subscription mSubscription;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.export_pdf);
        ButterKnife.bind(this);
        if (ContextCompat.checkSelfPermission(ExportPdf.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ExportPdf.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(ExportPdf.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
        SharedPreferences settings = this.getSharedPreferences(HomeActivity.SHARED_PREFERENCE, 0);
        id = settings.getString("id", "id");
        access_token = settings.getString("access_token", "access_token");
        setuptoolbar();one.setOnClickListener(this);two.setOnClickListener(this);
    }
    private void setuptoolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(" Export Pdf");
        toolbar.setTitleTextColor(0xffffffff);
        toolbar.setLogo(R.mipmap.arrowlleft);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(ExportPdf.this, ViewActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();

            }
        });
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button1:
                get_pdf();
                break;
            case R.id.button2:
                File pdfFile = new File(Environment.getExternalStorageDirectory() + "/resume/" + "resume.pdf");  // -> filename = maven.pdf
                Uri path = Uri.fromFile(pdfFile);
                Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                pdfIntent.setDataAndType(path, "application/pdf");pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                try{
                    startActivity(pdfIntent);
                }catch(ActivityNotFoundException e){
                    Toast.makeText(ExportPdf.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
                }                break;
        }

    }

    private void get_pdf() {
        pDialog = new ProgressDialog(ExportPdf.this);
        pDialog.setMessage("Downloading pdf...");
        pDialog.show();
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
                    @Override
                    public void onNext(List<LiveSyncinsert> sync) {
                        System.out.println(sync);
                        if(sync.size()==0){
                            PostService service = ApiClient.getClient().create(PostService.class);
                            Call<ExportResume> call = service.export_resume(id,"2","false",access_token);
                            call.enqueue(new Callback<ExportResume>() {
                                @Override
                                public void onResponse(Call<ExportResume> call, Response<ExportResume> response) {
                                    Log.v("RESPONSE_CODE", String.valueOf(response.code()));
                                    if(response.code()==200){
                                        String path=response.body().getPath();
                                        new DownloadFile().execute(path, "resume.pdf");
                                    }
                                }
                                @Override
                                public void onFailure(Call<ExportResume> call, Throwable t) {
                                    System.out.println("checking failure" + t.getLocalizedMessage());

                                }
                            });
                        }else{
                            Toast.makeText(ExportPdf.this, "Some data need to sync",Toast.LENGTH_LONG).show();

                        }

                    }

                    });

    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            System.out.println("file downloaded");
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];// -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "resume");
            folder.mkdir();File pdfFile = new File(folder, fileName);
            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            boolean z=FileDownloader.downloadFile(fileUrl, pdfFile);
            if(z){
                Handler handler =  new Handler(ExportPdf.this.getMainLooper());
                handler.post( new Runnable(){
                    public void run(){
                        Toast.makeText(ExportPdf.this, "Resume Downloaded successfully",Toast.LENGTH_LONG).show();
                        two.setVisibility(View.VISIBLE);pDialog.dismiss();
                    }
                });
            }
            return null;
        }
    }

}
