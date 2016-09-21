package com.example.sprydev5.skillsregister;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sprydev5.skillsregister.model.Updateservice;

/**
 * Created by sprydev5 on 27/7/16.
 */
public class SyncDataFragment extends Fragment {
    DatabaseHelper databaseHelper;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.syncdata, container, false);
        databaseHelper=new DatabaseHelper(this.getActivity());
        Toast.makeText(this.getActivity(), databaseHelper.getSyncStatus(), Toast.LENGTH_LONG).show();
        if (isNetworkAvailable()){
            if (databaseHelper.dbSyncCount()==0) {
                System.out.println("hiiiii");
                getActivity().stopService(new Intent(SyncDataFragment.this.getActivity(), MyService.class));
            }else{
                System.out.println("hello");
                getActivity().startService(new Intent(SyncDataFragment.this.getActivity(), MyService.class));
                getActivity().startService(new Intent(SyncDataFragment.this.getActivity(), Updateservice.class));

            }
        }else{
            Toast.makeText(context, "Not Connected to the internet", Toast.LENGTH_LONG).show();

        }
        return v;
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
