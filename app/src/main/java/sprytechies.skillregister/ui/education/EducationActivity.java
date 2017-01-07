package sprytechies.skillregister.ui.education;

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
import sprytechies.skillregister.data.model.EducationInsert;
import sprytechies.skillregister.ui.base.BaseActivity;
import sprytechies.skillregister.ui.launcher.activity.ViewActivity;
import sprytechies.skillregister.util.DialogFactory;

public class EducationActivity extends BaseActivity implements EducationMvpView {

    @Inject EducationAdapter educationAdapter;
    @Inject EducationPresenter educationPresenter;
    @BindView(R.id.education_recycler) RecyclerView recyclerView;
    @BindView(R.id.education_fab) FloatingActionButton floatingActionButton;
    @BindView(R.id.view_education_tool)Toolbar toolbar;
    @Inject DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_viewand_addeducation);
        ButterKnife.bind(this);setuptoolbar();
        recyclerView.setAdapter(educationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        educationPresenter.attachView(this);educationPresenter.loadEducation();
    }
    private void setuptoolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xffffffff);
        toolbar.setLogo(R.mipmap.arrowlleft);
        toolbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(EducationActivity.this, ViewActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();

                }
            });
        }
    @OnClick(R.id.education_fab)
    void onFabClick(){
        startActivity(new Intent(EducationActivity.this,AddEducation.class));
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        educationPresenter.detachView();
    }
    @Override
    public void showEducations(List<EducationInsert> educationInserts) {
        educationAdapter.setEducation(educationInserts);educationAdapter.notifyDataSetChanged();
    }
    @Override
    public void showEducationEmpty() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_education)).show();
    }
    @Override
    public void showError() {
        educationAdapter.setEducation(Collections.<EducationInsert>emptyList());
        educationAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.empty_education, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EducationActivity.this, ViewActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();
    }
}
