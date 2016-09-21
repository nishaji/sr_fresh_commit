package com.example.sprydev5.skillsregister;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.sprydev5.skillsregister.model.BaseActivity;
import com.example.sprydev5.skillsregister.model.Certificate;
import com.github.fabtransitionactivity.SheetLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewAndAddVolunteers extends BaseActivity implements SheetLayout.OnFabAnimationEndListener {
    @Bind(R.id.volun_bottom_sheet) SheetLayout mSheetLayout;
    @Bind(R.id.volunfab)
    FloatingActionButton mFab;
    DatabaseHelper databaseHelper;
    Toolbar toolbar;
    private VolunTeerAdapter adapter;
    private RecyclerView recyclerView;
    TextView emptyView;
    private static final int REQUEST_CODE = 1;
    String eduid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_and_add_volunteers);
        databaseHelper=new DatabaseHelper(this);
        ButterKnife.bind(this);
        mSheetLayout.setFab(mFab);
        mSheetLayout.setFabAnimationEndListener(this);
        toolbar = (Toolbar) findViewById(R.id.volunteertool);
        toolbar.setTitle(" Volunteers Details");
        toolbar.setLogo(R.drawable.arrowlleft);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAndAddVolunteers.this, LanucherActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();

            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.volunteerrecycler);
        emptyView = (TextView)findViewById(R.id.empty_view);
        ArrayList<Volunteer> contacts = databaseHelper.getallvolunteer();
        if (contacts.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        adapter = new VolunTeerAdapter(contacts);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    @OnClick(R.id.volunfab)
    void onFabClick() {
        mSheetLayout.expandFab();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.volun_view_cert, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == R.id.volun) {
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onFabAnimationEnd() {
        Intent intent = new Intent(this, Volunteers.class);
        startActivityForResult(intent, REQUEST_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            mSheetLayout.contractFab();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ViewAndAddVolunteers.this, LanucherActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }
}

