package sprytechies.skillregister.activities;

import android.annotation.TargetApi;
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

import com.github.fabtransitionactivity.SheetLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sprytechies.skillregister.adapters.ViewEduAdapter;
import sprytechies.skillregister.database.DatabaseHelper;
import sprytechies.skillregister.model.Edu;
import sprytechies.skillsregister.R;


public class ViewandAddeducation extends AppCompatActivity implements SheetLayout.OnFabAnimationEndListener {
    private RecyclerView recyclerView;
    private ViewEduAdapter adapter;
    Toolbar toolbar;
    private DatabaseHelper dbHandler;
    TextView emptyView;
    String eduid;
    @Bind(R.id.edu_bottom_sheet)
    SheetLayout mSheetLayout;
    @Bind(R.id.addedufab)
    FloatingActionButton mFab;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewand_addeducation);
        ButterKnife.bind(this);
        mSheetLayout.setFab(mFab);
        mSheetLayout.setFabAnimationEndListener(this);
        dbHandler = new DatabaseHelper(ViewandAddeducation.this);
        System.out.println(eduid+"iddddd");
        toolbar = (Toolbar) findViewById(R.id.viewedutool);
        toolbar.setTitle(" Education Details");
        toolbar.setLogo(R.drawable.arrowlleft);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewandAddeducation.this, LanucherActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();

            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.viewedurecycler);
        emptyView = (TextView)findViewById(R.id.empty_view);
        ArrayList<Edu> users = dbHandler.getallpersonaldata();
        if (users.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        adapter = new ViewEduAdapter(users);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }
    @OnClick(R.id.addedufab)
    void onFabClick() {
        mSheetLayout.expandFab();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_edu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == R.id.addedu) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFabAnimationEnd() {
        Intent intent = new Intent(this, Education.class);
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
        Intent intent = new Intent(ViewandAddeducation.this, LanucherActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }
}
