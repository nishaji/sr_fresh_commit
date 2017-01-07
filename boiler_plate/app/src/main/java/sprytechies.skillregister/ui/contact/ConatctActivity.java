package sprytechies.skillregister.ui.contact;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sprytechies.skillregister.R;
import sprytechies.skillregister.data.model.ContactInsert;
import sprytechies.skillregister.ui.base.BaseActivity;
import sprytechies.skillregister.ui.launcher.activity.ViewActivity;
import sprytechies.skillregister.util.DialogFactory;


public class ConatctActivity extends BaseActivity implements ContactMvpView {

    @Inject ContactAdapter contactAdapter;
    @Inject ContactPresenter contactPresenter;
    @BindView(R.id.view_contact_tool) Toolbar toolbar;
    @BindView(R.id.contact_recycler) RecyclerView recyclerView;
    @BindView(R.id.contact_fab) FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_viewand_add_contact);
        ButterKnife.bind(this);setuptoolbar();
        recyclerView.setAdapter(contactAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactPresenter.attachView(this);contactPresenter.loadContact();
    }
    private void setuptoolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xffffffff);
        toolbar.setLogo(R.mipmap.arrowlleft);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConatctActivity.this, ViewActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contactPresenter.detachView();
    }
    @Override
    public void showConatcts(List<ContactInsert> contactInserts) {
        contactAdapter.setContacts(contactInserts);contactAdapter.notifyDataSetChanged();
    }

    @Override
    public void showConatctEmpty() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_contact)).show();
    }
    @Override
    public void showError() {
        contactAdapter.setContacts(Collections.<ContactInsert>emptyList());
        contactAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.empty_contact, Toast.LENGTH_LONG).show();
    }
    @OnClick(R.id.contact_fab)
    void contact_fab_click(){
        startActivity(new Intent(ConatctActivity.this, AddContact.class));
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ConatctActivity.this, ViewActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();
    }
}
