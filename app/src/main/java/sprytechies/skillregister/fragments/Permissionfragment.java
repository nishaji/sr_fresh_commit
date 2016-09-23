package sprytechies.skillregister.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sprytechies.skillregister.adapters.PermissionAdapter;
import sprytechies.skillregister.model.PermissionModel;
import sprytechies.skillsregister.R;


/**
 * Created by sprydev5 on 14/9/16.
 */
public class Permissionfragment extends Fragment {
    RecyclerView recyclerView;
    PermissionAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.permission, container, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.gmail_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        List<String> listData = new ArrayList<String>();
        int ct = 0;
        for (int i = 0; i < PermissionModel.data.length; i++) {
            listData.add(PermissionModel.data[ct]);
            ct++;
            if (ct == PermissionModel.data.length) {
                ct = 0;
            }
        }

        if (adapter == null) {
            adapter = new PermissionAdapter(Permissionfragment.this.getContext(), listData);
            recyclerView.setAdapter(adapter);
        }
        return v;
    }

}
