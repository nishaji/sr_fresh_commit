package sprytechies.skillregister.ui.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sprytechies.skillregister.R;
import sprytechies.skillregister.data.model.volunteerInsert;
import sprytechies.skillregister.ui.base.BaseActivity;
import sprytechies.skillregister.ui.launcher.activity.ViewActivity;
import sprytechies.skillregister.util.DialogFactory;


public class VolunteerActivity extends BaseActivity implements VolunteerMvpView {

    @Inject VolunteerPresenter volunteerPresenter;
    @Inject VolunteerAdapter volunteerAdapter;
    @BindView(R.id.view_volunteer_tool)Toolbar toolbar;
    @BindView(R.id.volunteer_recycler) RecyclerView recyclerView;
    @BindView(R.id.volunteer_fab) FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_view_and_add_volunteers);
        ButterKnife.bind(this);
        recyclerView.setAdapter(volunteerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        volunteerPresenter.attachView(this);
        volunteerPresenter.loadvolunteer();
        setuptoolbar();

    }
    private void setuptoolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xffffffff);
        toolbar.setLogo(R.mipmap.arrowlleft);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VolunteerActivity.this, ViewActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();

            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        volunteerPresenter.detachView();
    }
    @OnClick(R.id.volunteer_fab)
    void onFabClick(){
        startActivity(new Intent(VolunteerActivity.this,AddVolunteer.class));
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }
    @Override
    public void showVolunteers(List<volunteerInsert> volunteer) {
        volunteerAdapter.setVolunteer(volunteer);
        volunteerAdapter.notifyDataSetChanged();

    }

    @Override
    public void showVolunteerEmpty() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_volunteer))
                .show();
    }

    @Override
    public void showError() {
        volunteerAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.empty_volunteer, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(VolunteerActivity.this, ViewActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();
    }
}
