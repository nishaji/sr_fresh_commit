package sprytechies.skillregister.ui.publication;

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
import sprytechies.skillregister.data.model.PublicationInsert;
import sprytechies.skillregister.ui.base.BaseActivity;
import sprytechies.skillregister.ui.launcher.activity.ViewActivity;
import sprytechies.skillregister.util.DialogFactory;


public class ActivityPublication extends BaseActivity implements PublicationMvpView {
    @Inject PublicationPresenter publication;
    @Inject PublicationAdapter pu_adapter;
    @BindView(R.id.view_publication_tool)Toolbar toolbar;
    @BindView(R.id.publication_recycler)RecyclerView recyclerView;
    @BindView(R.id.publication_fab)FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_view_and_add_publication);
        ButterKnife.bind(this);setuptoolbar();
        recyclerView.setAdapter(pu_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        publication.attachView(this);publication.loadpublication();

    }
    private void setuptoolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xffffffff);
        toolbar.setLogo(R.mipmap.arrowlleft);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityPublication.this, ViewActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();

            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        publication.detachView();
    }
    @Override
    public void showPublications(List<PublicationInsert> publication) {
        pu_adapter.setPublication(publication);pu_adapter.notifyDataSetChanged();
    }

    @Override
    public void showPublicationEmpty() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_publication)).show();
    }
    @Override
    public void showError() {
        pu_adapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.empty_publication, Toast.LENGTH_LONG).show();
    }
    @OnClick(R.id.publication_fab)
    void onFabClick(){
        startActivity(new Intent(ActivityPublication.this,AddPublication.class));
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }
}
