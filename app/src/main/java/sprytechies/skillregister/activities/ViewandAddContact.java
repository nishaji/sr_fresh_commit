package sprytechies.skillregister.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import sprytechies.skillregister.adapters.ContactAdapter;
import sprytechies.skillregister.database.DatabaseHelper;
import sprytechies.skillregister.model.Contact;
import sprytechies.skillsregister.R;

public class ViewandAddContact extends BaseActivity implements SheetLayout.OnFabAnimationEndListener {

    private static final int REQUEST_CODE = 1;
    @Bind(R.id.contact_bottom_sheet) SheetLayout mSheetLayout;
    @Bind(R.id.contactfab)
    FloatingActionButton mFab;

    private ContactAdapter adapter;
    private RecyclerView recyclerView;
    private TextView emptyView;
    DatabaseHelper databaseHelper;
    Toolbar toolbar;
    String deleteid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewand_add_contact);
        databaseHelper=new DatabaseHelper(this);
        ButterKnife.bind(this);
        mSheetLayout.setFab(mFab);
        mSheetLayout.setFabAnimationEndListener(this);
        toolbar = (Toolbar) findViewById(R.id.contactaddtool);
        toolbar.setTitle(" Contact Details");
        toolbar.setLogo(R.drawable.arrowlleft);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewandAddContact.this, LanucherActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();

            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.contact_recycler);
        emptyView = (TextView)findViewById(R.id.empty_view);
        ArrayList<Contact> contacts = databaseHelper.getAllcontact();
        if (contacts.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        adapter = new ContactAdapter(contacts);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    @OnClick(R.id.contactfab)
    void onFabClick() {
        mSheetLayout.expandFab();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            mSheetLayout.contractFab();
        }
    }
    @Override
    public void onFabAnimationEnd() {
        Intent intent = new Intent(this, Contacts.class);
        startActivityForResult(intent, REQUEST_CODE);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_add_contact, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == R.id.addcontact) {
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ViewandAddContact.this, LanucherActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }
}
