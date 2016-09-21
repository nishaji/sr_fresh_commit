package com.example.sprydev5.skillsregister;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.sprydev5.skillsregister.model.BaseActivity;
import com.example.sprydev5.skillsregister.model.Project;
import com.github.fabtransitionactivity.SheetLayout;


import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class Projects extends BaseActivity implements SheetLayout.OnFabAnimationEndListener  {

    private RecyclerView recyclerView;
    private ProjectAdapter adapter;
    Toolbar toolbar;
    private DatabaseHelper dbHandler;
    private TextView emptyView;
    private static final int REQUEST_CODE = 1;

    @Bind(R.id.proj_bottom_sheet) SheetLayout mSheetLayout;
    @Bind(R.id.proj_fab)
    FloatingActionButton mFab;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        ButterKnife.bind(this);
        mSheetLayout.setFab(mFab);
        mSheetLayout.setFabAnimationEndListener(this);
        dbHandler = new DatabaseHelper(Projects.this);
        toolbar = (Toolbar) findViewById(R.id.addprojtool);
        toolbar.setTitle(" Project Details");
        toolbar.setLogo(R.drawable.arrowlleft);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Projects.this, LanucherActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();

            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.proj_recycler);
        emptyView = (TextView)findViewById(R.id.empty_view);
        ArrayList<Project> projects = dbHandler.getAllProjects();
        if (projects.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        adapter = new ProjectAdapter(projects);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }
    @OnClick(R.id.proj_fab)
    void onFabClick() {
        mSheetLayout.expandFab();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_proj, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == R.id.saveprojectdata) {
       /*     Intent intent = new Intent(Projects.this, AddProject.class);
            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(Projects.this, R.anim.animation, R.anim.animation2).toBundle();
            startActivity(intent, bndlanimation);*/
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFabAnimationEnd() {
        Intent intent = new Intent(this, AddProject.class);
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
        Intent intent = new Intent(Projects.this, LanucherActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }
}
