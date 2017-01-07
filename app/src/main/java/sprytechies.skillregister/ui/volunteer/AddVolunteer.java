package sprytechies.skillregister.ui.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sprytechies.skillregister.R;
import sprytechies.skillregister.data.SyncService;
import sprytechies.skillregister.data.local.DatabaseHelper;
import sprytechies.skillregister.data.model.Volunteer;
import sprytechies.skillregister.data.remote.postservice.CertificatePost;
import sprytechies.skillregister.data.remote.postservice.VolunteerPost;
import sprytechies.skillregister.ui.base.BaseActivity;
import sprytechies.skillregister.util.AndroidComponentUtil;
import sprytechies.skillregister.util.NetworkUtil;
import timber.log.Timber;

public class AddVolunteer extends BaseActivity {
    @BindView(R.id.volunteer_role) EditText role;
    @BindView(R.id.volunteer_description) EditText desc;
    @BindView(R.id.volunteer_org) EditText org;
    @BindView(R.id.volunteer_role_type) EditText role_type;
    @BindView(R.id.volunteer_duration) ImageView du;
    @BindView(R.id.volunteer_text) TextView du_text;
    @BindView(R.id.add_volunteer_tool) Toolbar toolbar;
    @BindView(R.id.add_volunteer) Button add_volunteer;
    @Inject DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);setContentView(R.layout.activity_volunteers);
        ButterKnife.bind(this);setuptoolbar();
    }
    private void setuptoolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xffffffff);
        toolbar.setLogo(R.mipmap.arrowlleft);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddVolunteer.this, VolunteerActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();

            }
        });
    }

    @OnClick(R.id.add_volunteer)
    void onButtonClick() {
        if(role.getText().toString().length()==0||role_type.getText().toString().length()==0||desc.getText().toString().length()==0||org.getText().toString().length()==0){
            Toast.makeText(this,"Fields cannot be empty",Toast.LENGTH_SHORT).show();
        }else if(du_text.getText().toString().length()==0){
            Toast.makeText(this,"Please pick date",Toast.LENGTH_SHORT).show();
        }
        else{
            String[] parts = du_text.getText().toString().split("To");
            String from = parts[0];String to = parts[1];
            databaseHelper.setVolunteer(Volunteer.builder()
                    .setRole(role.getText().toString()).setOrganisation(org.getText().toString())
                    .setUpto(to).setFrom(from).setDescription(desc.getText().toString())
                    .setType(role_type.getText().toString()).setPostflag("0").setPutflag("1").setCreateflag("1").setUpdateflag("1").build());
            startActivity(new Intent(AddVolunteer.this,VolunteerActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            if (!NetworkUtil.isNetworkConnected(this)) {
                Timber.i("Volunteer Sync canceled, connection not available");
                AndroidComponentUtil.toggleComponent(this, SyncService.SyncOnConnectionAvailable.class, true);
            }else{
                startService(VolunteerPost.getStartIntent(this));
            }
        }
    }
    @OnClick(R.id.volunteer_duration)
    void onClockClick() {
        SmoothDateRangePickerFragment smoothDateRangePickerFragment =
                SmoothDateRangePickerFragment
                        .newInstance(new SmoothDateRangePickerFragment.OnDateRangeSetListener() {
                            @Override
                            public void onDateRangeSet(SmoothDateRangePickerFragment view,
                                                       int yearStart, int monthStart,
                                                       int dayStart, int yearEnd,
                                                       int monthEnd, int dayEnd) {
                                String date = dayStart + "/" + (++monthStart)
                                        + "/" + yearStart + " To " + dayEnd + "/"
                                        + (++monthEnd) + "/" + yearEnd;du_text.setText(date);
                            }
                        });
        smoothDateRangePickerFragment.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AddVolunteer.this, VolunteerActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();
    }
}
