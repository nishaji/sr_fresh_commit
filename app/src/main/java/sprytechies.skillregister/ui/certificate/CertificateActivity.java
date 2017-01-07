package sprytechies.skillregister.ui.certificate;

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
import sprytechies.skillregister.data.model.CertificateInsert;
import sprytechies.skillregister.ui.base.BaseActivity;
import sprytechies.skillregister.ui.launcher.activity.ViewActivity;
import sprytechies.skillregister.util.DialogFactory;

public class CertificateActivity extends BaseActivity implements CertificateMvpView {

    @Inject CertificateAdapter certificateAdapter;
    @Inject CertificatePresenter certificatePresenter;
    @BindView(R.id.view_certificate_tool) Toolbar toolbar;
    @BindView(R.id.certificate_recycler) RecyclerView recyclerView;
    @BindView(R.id.certificate_fab) FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_viewand_edit_certificate);
        ButterKnife.bind(this);setuptoolbar();
        recyclerView.setAdapter(certificateAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        certificatePresenter.attachView(this);certificatePresenter.loadCertificate();
    }

    private void setuptoolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xffffffff);
        toolbar.setLogo(R.mipmap.arrowlleft);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CertificateActivity.this, ViewActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        certificatePresenter.detachView();
    }
    @Override
    public void showCertificates(List<CertificateInsert> certificateInserts) {
        certificateAdapter.setCertificates(certificateInserts);certificateAdapter.notifyDataSetChanged();
    }
    @Override
    public void showCertificateEmpty() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_certificate)).show();
    }

    @Override
    public void showError() {
        certificateAdapter.setCertificates(Collections.<CertificateInsert>emptyList());
        certificateAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.empty_certificate, Toast.LENGTH_LONG).show();
    }
    @OnClick(R.id.certificate_fab)
    void certi_fab_click(){
        startActivity(new Intent(CertificateActivity.this, AddCertificate.class));
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CertificateActivity.this, ViewActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();
    }
}
