package sprytechies.skillregister.ui.project;

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
import sprytechies.skillregister.data.model.ProjectInsert;
import sprytechies.skillregister.ui.base.BaseActivity;
import sprytechies.skillregister.ui.launcher.activity.ViewActivity;
import sprytechies.skillregister.util.DialogFactory;


public class ProjectActivity extends BaseActivity implements ProjectMvpView {

    @Inject ProjectPresenter projectPresenter;
    @Inject ProjectAdapter projectAdapter;
    @BindView(R.id.project_recycler)RecyclerView recyclerView;
    @BindView(R.id.view_project_tool)Toolbar toolbar;
    @BindView(R.id.project_fab)FloatingActionButton project_fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_projects);
        ButterKnife.bind(this);setuptoolbar();
        recyclerView.setAdapter(projectAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        projectPresenter.attachView(this);projectPresenter.loadProject();
    }

    private void setuptoolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xffffffff);
        toolbar.setLogo(R.mipmap.arrowlleft);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProjectActivity.this, ViewActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        projectPresenter.detachView();
    }
    @Override
    public void showProjectss(List<ProjectInsert> project) {
        projectAdapter.setProjects(project);projectAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProjectEmpty() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_projects)).show();
    }
    @Override
    public void showError() {
        projectAdapter.setProjects(Collections.<ProjectInsert>emptyList());
        projectAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.empty_awards, Toast.LENGTH_LONG).show();
    }
    @OnClick(R.id.project_fab)
    void addAward(){
        startActivity(new Intent(ProjectActivity.this, AddProject.class));
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ProjectActivity.this, ViewActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();
    }
}
