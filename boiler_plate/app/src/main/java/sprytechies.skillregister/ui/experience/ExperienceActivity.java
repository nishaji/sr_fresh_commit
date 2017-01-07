package sprytechies.skillregister.ui.experience;

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
import sprytechies.skillregister.data.model.ExperienceInsert;
import sprytechies.skillregister.ui.base.BaseActivity;
import sprytechies.skillregister.ui.launcher.activity.ViewActivity;
import sprytechies.skillregister.util.DialogFactory;

public class ExperienceActivity extends BaseActivity implements ExperienceMvp {

    @Inject ExperineceAdapter experineceAdapter;
    @Inject ExperiencePresenter experiencePresenter;
    @BindView(R.id.experience_recycler) RecyclerView recyclerView;
    @BindView(R.id.experience_fab) FloatingActionButton floatingActionButton;
    @BindView(R.id.view_experience_tool)Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_work_experience);ButterKnife.bind(this);
        recyclerView.setAdapter(experineceAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        experiencePresenter.attachView(this);experiencePresenter.loadExperience();setuptoolbar();
    }
    private void setuptoolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xffffffff);
        toolbar.setLogo(R.mipmap.arrowlleft);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExperienceActivity.this, ViewActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();

            }
        });
    }
    @OnClick(R.id.experience_fab)
    void onFabClick(){
        startActivity(new Intent(ExperienceActivity.this,AddExperience.class));
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        experiencePresenter.detachView();
    }

    @Override
    public void showExperiences(List<ExperienceInsert> experienceInserts) {
        experineceAdapter.setExperience(experienceInserts);experineceAdapter.notifyDataSetChanged();
    }
    @Override
    public void showExperienceEmpty() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_experience)).show();
    }

    @Override
    public void showError() {
        experineceAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.empty_experience, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ExperienceActivity.this, ViewActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();
    }
}
