package sprytechies.skillregister.ui.launcher.Exportpdf;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import sprytechies.skillregister.R;
import sprytechies.skillregister.ui.base.BaseActivity;
import sprytechies.skillregister.ui.launcher.activity.ViewActivity;


/**
 * Created by sprydev5 on 8/11/16.
 */

public class ExportPdf extends BaseActivity implements View.OnClickListener {
   int MY_PERMISSIONS_REQUEST_READ_CONTACTS ;
    @BindView(R.id.button1)Button one;
    @BindView(R.id.button2)Button two;
    @BindView(R.id.export_pdf_tool)Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.export_pdf);
        ButterKnife.bind(this);
        setuptoolbar();
        one.setOnClickListener(this); // calling onClick() method
        two.setOnClickListener(this);
    }
    private void setuptoolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(" Export Pdf");
        toolbar.setTitleTextColor(0xffffffff);
        toolbar.setLogo(R.mipmap.arrowlleft);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExportPdf.this, ViewActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();

            }
        });
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button1:
                new DownloadFile().execute("http://sr.api.sprytechies.net:3003/api/containers/media5821bcd065b0136375a7c28c/download/resume.pdf", "resume.pdf");
                break;
            case R.id.button2:
                if (ContextCompat.checkSelfPermission(ExportPdf.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(ExportPdf.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                        // Show an expanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {

                        // No explanation needed, we can request the permission.

                        ActivityCompat.requestPermissions(ExportPdf.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                }
                File pdfFile = new File(Environment.getExternalStorageDirectory() + "/profile/" + "resume.pdf");  // -> filename = maven.pdf
                Uri path = Uri.fromFile(pdfFile);
                Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                pdfIntent.setDataAndType(path, "application/pdf");
                pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                try{
                    startActivity(pdfIntent);
                }catch(ActivityNotFoundException e){
                    Toast.makeText(ExportPdf.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
                }                break;
        }

    }
    private class DownloadFile extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            System.out.println("file downloaded");
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            System.out.println(fileUrl+fileName+"response");
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "profile");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

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
                        two.setVisibility(View.VISIBLE);
                    }
                });
            }
            System.out.println(z+"dfklsdhkf");



            return null;
        }
    }
   public void shoToast(){
       Toast.makeText(ExportPdf.this, "Resume Downloaded Successfully", Toast.LENGTH_SHORT).show();
   }
}
