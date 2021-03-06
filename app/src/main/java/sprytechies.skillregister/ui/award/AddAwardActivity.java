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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sprytechies.skillregister.R;
import sprytechies.skillregister.data.local.DatabaseHelper;
import sprytechies.skillregister.data.model.Award;
import sprytechies.skillregister.data.model.LiveSync;
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
    JSONObject awrd=new JSONObject();
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
            databaseHelper.setAwards(Award.builder()
                    .setTitle(award_title.getText().toString()).setOrganisation(award_org.getText().toString())
                    .setDescription(award_desc.getText().toString()).setDuration(award_du_text.getText().toString())
                    .setMongoid("mongo").build());
                     startActivity(new Intent(AddAwardActivity.this,AwardActivity.class));
                     overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            try {
                awrd.put("title",award_title.getText().toString());
                awrd.put("org",award_org.getText().toString());
                awrd.put("des",award_desc.getText().toString());
                awrd.put("date",award_du_text.getText().toString());
                databaseHelper.setSyncstatus(LiveSync.builder().setBit("award").setBitmongoid("mongo").setBitbefore(awrd.toString()).build());
            } catch (JSONException e) {
                e.printStackTrace();
            }


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
        String date =" "+ year + "-" + (++monthOfYear) + "-" + dayOfMonth;
        System.out.println(date);
        Date da=new Date();
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            da = dateFormatGmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        award_du_text.setText(da.toString());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AddAwardActivity.this, AwardActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();
    }
}


