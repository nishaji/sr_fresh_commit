package sprytechies.skillregister.services;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;




import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import sprytechies.skillregister.apicallclasses.ApiData;
import sprytechies.skillregister.database.DatabaseHelper;


/**
 * Created by sprydev5 on 12/9/16.
 */
public class Updateservice extends Service {
    DatabaseHelper databaseHelper;
    String edubit = "edu_bit", expbit = "exp_bit", projbit = "proj_bit", certbit = "cert_bit", contactbit = "contact_bit";
    String status = "done";
    public static final long NOTIFY_INTERVAL = 60 * 1000; // 10 seconds

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Update Service was Created", Toast.LENGTH_LONG).show();
        if (mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);
    }
    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    // display toast
                    Toast.makeText(getApplicationContext(), "Post service System is calling",
                            Toast.LENGTH_SHORT).show();
                }

            });
        }
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onStart(Intent intent, int startId) {
        databaseHelper = new DatabaseHelper(this);
        Date date = new Date();
        ArrayList<HashMap<String, String>> allnotsyncdata = databaseHelper.getNotSyncData();
        System.out.println(allnotsyncdata + "datttada");
        for (int i = 0; i < allnotsyncdata.size(); i++) {
            System.out.println(allnotsyncdata.get(i).get("bittype").equals(edubit) && !allnotsyncdata.get(i).get("mongoid").equals("mongo"));
            if (allnotsyncdata.get(i).get("bittype").equals(edubit) && !allnotsyncdata.get(i).get("mongoid").equals("mongo")) {
                ArrayList<HashMap<String, String>> eduupdatedata = databaseHelper.getNotSyncEduData(edubit);
                for (int j = 0; j < eduupdatedata.size(); j++) {
                    System.out.println(eduupdatedata.get(j).get("mongoid"));
                    ApiData apiData = new ApiData(getApplicationContext());
                    apiData.updateedudata(eduupdatedata.get(j).get("mongoid"), eduupdatedata.get(j).get("data"));
                    databaseHelper.updateUpdateSyncStatus(eduupdatedata.get(j).get("mongoid"), status, date.toString());
                }
            }
            if (allnotsyncdata.get(i).get("bittype").equals(expbit) && !allnotsyncdata.get(i).get("mongoid").equals("mongo")) {
                ArrayList<HashMap<String, String>> expupdatedata = databaseHelper.getNotSyncExpData(expbit);
                for (int j = 0; j < expupdatedata.size(); j++) {
                    System.out.println(expupdatedata.get(j).get("mongoid"));
                    ApiData apiData = new ApiData(getApplicationContext());
                    apiData.updateexpdata(expupdatedata.get(j).get("mongoid"), expupdatedata.get(j).get("data"));
                    databaseHelper.updateUpdateSyncStatus(expupdatedata.get(j).get("mongoid"), status, date.toString());
                }
            }
            if (allnotsyncdata.get(i).get("bittype").equals(projbit) && !allnotsyncdata.get(i).get("mongoid").equals("mongo")) {
                ArrayList<HashMap<String, String>> projupdatedata = databaseHelper.getNotSyncProjData(projbit);
                for (int j = 0; j < projupdatedata.size(); j++) {
                    System.out.println(projupdatedata.get(j).get("mongoid"));
                    ApiData apiData = new ApiData(getApplicationContext());
                    apiData.updateprojdata(projupdatedata.get(j).get("mongoid"), projupdatedata.get(j).get("data"));
                    databaseHelper.updateUpdateSyncStatus(projupdatedata.get(j).get("mongoid"), status, date.toString());
                }
            }
            if (allnotsyncdata.get(i).get("bittype").equals(certbit) && !allnotsyncdata.get(i).get("mongoid").equals("mongo")) {
                ArrayList<HashMap<String, String>> certupdatedata = databaseHelper.getNotSyncCertData(certbit);
                for (int j = 0; j < certupdatedata.size(); j++) {
                    System.out.println(certupdatedata.get(j).get("mongoid"));
                    ApiData apiData = new ApiData(getApplicationContext());
                    apiData.updatecertdata(certupdatedata.get(j).get("mongoid"), certupdatedata.get(j).get("data"));
                    databaseHelper.updateUpdateSyncStatus(certupdatedata.get(j).get("mongoid"), status, date.toString());
                }
            } else {
                System.out.println("no any updated data");
            }
        }

        Toast.makeText(this, "Update Service Started", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Update Service Destroyed", Toast.LENGTH_LONG).show();
    }

}
