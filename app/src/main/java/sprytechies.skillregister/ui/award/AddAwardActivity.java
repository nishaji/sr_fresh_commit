package sprytechies.skillregister.ui.award;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sprytechies.skillregister.R;
import sprytechies.skillregister.data.local.DatabaseHelper;
import sprytechies.skillregister.data.model.Award;
import sprytechies.skillregister.ui.base.BaseActivity;


public class AddAwardActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.award_title) EditText award_title;
    @BindView(R.id.award_organisation) EditText award_org;
    @BindView(R.id.award_description) EditText award_desc;
    @BindView(R.id.award_duration) ImageView award_duration;
    @BindView(R.id.award_duration_text) TextView award_du_text;
    @BindView(R.id.add_award) Button add_award;
    @BindView(R.id.add_award_tool)Toolbar add_tool;
    @Inject DatabaseHelper databaseHelper;Award award;
    Date date=new Date();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_awards);
        ButterKnife.bind(this);setuptoolbar();
        award_du_text.setHint(new SimpleDateFormat("dd/MM/yyyy").format(date));
    }
    private void setuptoolbar() {
        setSupportActionBar(add_tool);add_tool.setTitleTextColor(0xffffffff);
        add_tool.setLogo(R.mipmap.arrowlleft);
        add_tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddAwardActivity.this, AwardActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();
            }
        });
    }
    @OnClick(R.id.add_award)
    void onFabClick() {
        if(award_title.getText().toString().length() == 0||award_org.getText().toString().length() == 0||award_desc.getText().toString().length() == 0){
            Toast.makeText(AddAwardActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        }else if(award_du_text.getText().toString().length() == 0){
            Toast.makeText(AddAwardActivity.this, "Please pick date", Toast.LENGTH_SHORT).show();
        }
        else {
            Date date=new Date();
            databaseHelper.setAwards(Award.builder()
                    .setTitle(award_title.getText().toString()).setOrganisation(award_org.getText().toString())
                    .setDescription(award_desc.getText().toString()).setDuration(award_du_text.getText().toString())
                    .setDate(date.toString()).setCreateflag("1").setUpdateflag("0").setPostflag("0").setPutflag("0").build());
                     startActivity(new Intent(AddAwardActivity.this,AwardActivity.class));
                     overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        }
    }
    @OnClick(R.id.award_duration)
    void onClockClick() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                AddAwardActivity.this, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(AddAwardActivity.this.getFragmentManager(), "Datepickerdialog");
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date =" "+dayOfMonth + "/" + (++monthOfYear) + "/" + year;award_du_text.setText(date);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AddAwardActivity.this, AwardActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();
    }
}


