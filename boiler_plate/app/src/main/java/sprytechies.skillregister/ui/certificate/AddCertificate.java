package sprytechies.skillregister.ui.certificate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sprytechies.skillregister.R;
import sprytechies.skillregister.data.SyncService;
import sprytechies.skillregister.data.local.DatabaseHelper;
import sprytechies.skillregister.data.model.Certificate;
import sprytechies.skillregister.data.remote.postservice.CertificatePost;
import sprytechies.skillregister.ui.base.BaseActivity;
import sprytechies.skillregister.util.AndroidComponentUtil;
import sprytechies.skillregister.util.NetworkUtil;
import timber.log.Timber;

public class AddCertificate extends BaseActivity implements DatePickerDialog.OnDateSetListener {
    @BindView(R.id.certificate_name) EditText cert_name;
    @BindView(R.id.certificate_type) MaterialBetterSpinner certificate_type;
    @BindView(R.id.rank) EditText rank;
    @BindView(R.id.authority) EditText authority;
    @BindView(R.id.add_certificate_tool)Toolbar add_cert_tool;
    @BindView(R.id.add_certificate)Button add_certificate;
    @BindView(R.id.certificate_duration)ImageView du;
    @BindView(R.id.certificate_duration_text)TextView du_text;
    @Inject DatabaseHelper databaseHelper;
    Date date=new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_certificates);
        ButterKnife.bind(this);setuptoolbar();loadSpinnerdata();
        du_text.setHint(new SimpleDateFormat("dd/MM/yyyy").format(date));

    }
    private void loadSpinnerdata() {
        ArrayAdapter<String> cert_type_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.certificate_array));
        certificate_type.setAdapter(cert_type_adapter);
    }

    private void setuptoolbar() {
        setSupportActionBar(add_cert_tool);
        add_cert_tool.setTitleTextColor(0xffffffff);
        add_cert_tool.setLogo(R.mipmap.arrowlleft);
        add_cert_tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddCertificate.this, CertificateActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();
            }
        });
    }
    @OnClick(R.id.certificate_duration)
    public void on_clock_click(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                AddCertificate.this, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(AddCertificate.this.getFragmentManager(), "Datepickerdialog");
    }
    @OnClick(R.id.add_certificate)
    void addCertificate(){
        if(authority.getText().toString().length()==0||cert_name.getText().toString().length()==0||certificate_type.getText().toString().length()==0||rank.getText().toString().length()==0){
            Toast.makeText(AddCertificate.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        }else if(du_text.getText().toString().length()==0){
            Toast.makeText(AddCertificate.this, "Please pick date", Toast.LENGTH_SHORT).show();
        } else{
            Date date=new Date();
            databaseHelper.setCertificate(Certificate.builder()
                    .setAuthority(authority.getText().toString()).setCertdate(du_text.getText().toString())
                    .setName(cert_name.getText().toString())
                    .setType(certificate_type.getText().toString()).setRank(rank.getText().toString())
                    .setDate(date.toString()).setCreateflag("1").setUpdateflag("0").setPostflag("0").setPutflag("0").build());
            startActivity(new Intent(AddCertificate.this, CertificateActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date =" "+dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        du_text.setText(date);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AddCertificate.this, CertificateActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();
    }
}
