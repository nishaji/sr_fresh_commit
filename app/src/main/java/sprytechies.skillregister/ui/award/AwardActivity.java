package sprytechies.skillregister.ui.award;

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
import sprytechies.skillregister.data.local.DatabaseHelper;
import sprytechies.skillregister.data.model.AwardInsert;
import sprytechies.skillregister.ui.base.BaseActivity;
import sprytechies.skillregister.ui.launcher.activity.ViewActivity;
import sprytechies.skillregister.util.DialogFactory;

public class AwardActivity extends BaseActivity implements AwardMvpView {

    @Inject AwardAdapter awardAdapter;
    @Inject AwardPresenter awardPresenter;
    @Inject DatabaseHelper databaseHelper;
    @BindView(R.id.award_recycler) RecyclerView recyclerView;
    @BindView(R.id.award_fab) FloatingActionButton floatingActionButton;
    @BindView(R.id.view_award_tool) Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_view_and_add_awards);
        ButterKnife.bind(this);setuptoolbar();
        recyclerView.setAdapter(awardAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        awardPresenter.attachView(this);awardPresenter.loadAwards();
    }

    private void setuptoolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xffffffff);
        toolbar.setLogo(R.mipmap.arrowlleft);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AwardActivity.this, ViewActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        awardPresenter.detachView();
    }
    @Override
    public void showAwards(List<AwardInsert> awardInserts) {
        awardAdapter.setAwards(awardInserts);awardAdapter.notifyDataSetChanged();
    }
    @Override
    public void showAwardEmpty() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_awards)).show();
    }
    @Override
    public void showError() {
        awardAdapter.setAwards(Collections.<AwardInsert>emptyList());
        awardAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.empty_awards, Toast.LENGTH_LONG).show();
    }
    @OnClick (R.id.award_fab)
    void addAward(){
        startActivity(new Intent(AwardActivity.this, AddAwardActivity.class));
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AwardActivity.this, ViewActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();
    }
}
