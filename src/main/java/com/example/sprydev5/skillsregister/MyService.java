package com.example.sprydev5.skillsregister;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.sprydev5.skillsregister.data.ApiData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MyService extends Service {

    DatabaseHelper databaseHelper;
    String edubit="edu_bit",expbit="exp_bit",certbit="cert_bit",projbit="proj_bit",contactbit="contact_bit";
    String notsynced="not_synced";
    String status="done";

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate() {
        Toast.makeText(this, "Service was Created", Toast.LENGTH_LONG).show();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onStart(Intent intent, int startId) {
        databaseHelper=new DatabaseHelper(this);
        Date date=new Date();
        ArrayList<HashMap<String,String>>allnotsyncdat=databaseHelper.getNotSyncData();
        for(int i=0; i<allnotsyncdat.size();i++){
            if(allnotsyncdat.get(i).get("bittype").equals(edubit)){
                ArrayList<HashMap<String, String>> edupostdata=databaseHelper.getNotSyncEduData(edubit);
                for (int j=0; j<edupostdata.size(); j++){
                    ApiData apiData = new ApiData(getApplicationContext());
                    apiData.postedudata(edupostdata.get(j).get("data"));
                    databaseHelper.updatePostSyncStatus(edupostdata.get(j).get("id"),status,date.toString());
                }
            } if(allnotsyncdat.get(i).get("bittype").equals(expbit)){
                ArrayList<HashMap<String, String>> exppostdata=databaseHelper.getNotSyncExpData(expbit);
                for (int j=0; j<exppostdata.size(); j++){
                     ApiData apiData = new ApiData(getApplicationContext());
                     apiData.postexpdata(exppostdata.get(j).get("data"));
                     databaseHelper.updatePostSyncStatus(exppostdata.get(j).get("id"),status,date.toString());
                }
            }
            if(allnotsyncdat.get(i).get("bittype").equals(projbit)){
                ArrayList<HashMap<String, String>> projpostdata=databaseHelper.getNotSyncProjData(projbit);
                for (int j=0; j<projpostdata.size(); j++){
                    ApiData apiData = new ApiData(getApplicationContext());
                    apiData.postprojdata(projpostdata.get(j).get("data"));
                    databaseHelper.updatePostSyncStatus(projpostdata.get(j).get("id"),status,date.toString());
                }
            }
            if(allnotsyncdat.get(i).get("bittype").equals(certbit)){
                ArrayList<HashMap<String, String>> certpostdata=databaseHelper.getNotSyncCertData(certbit);
                for (int j=0; j<certpostdata.size(); j++){
                    ApiData apiData = new ApiData(getApplicationContext());
                    apiData.postcertificatedata(certpostdata.get(j).get("data"));
                    databaseHelper.updatePostSyncStatus(certpostdata.get(j).get("id"),status,date.toString());
                }
            }
            if(allnotsyncdat.get(i).get("bittype").equals(contactbit)){
                ArrayList<HashMap<String, String>> contactpostdata=databaseHelper.getNotSyncContactData(contactbit);
                for (int j=0; j<contactpostdata.size(); j++){
                    ApiData apiData = new ApiData(getApplicationContext());
                    apiData.postcontactdata(contactpostdata.get(j).get("data"));
                    databaseHelper.updatePostSyncStatus(contactpostdata.get(j).get("id"),status,date.toString());
                }
            }

        }
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

}
