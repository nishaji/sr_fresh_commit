package sprytechies.skillregister.ui.experience;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sprytechies.skillregister.R;
import sprytechies.skillregister.data.local.DatabaseHelper;
import sprytechies.skillregister.data.model.Experience;
import sprytechies.skillregister.data.model.Location;
import sprytechies.skillregister.ui.base.BaseActivity;


public class AddExperience extends BaseActivity {
    @BindView(R.id.company_name) AutoCompleteTextView comany_name;
    @BindView(R.id.company_title) MaterialBetterSpinner company_title;
    @BindView(R.id.job) AutoCompleteTextView job;
    @BindView(R.id.job_type) MaterialBetterSpinner job_type;
    @BindView(R.id.status)EditText status;
    @BindView(R.id.exp_location)EditText exp_location;
    @BindView(R.id.exp_location_type)MaterialBetterSpinner exp_location_type;

    @BindView(R.id.add_exp) Button add_experience;
    @BindView(R.id.exp_duration)ImageView exp_duration;
    @BindView(R.id.exp_duration_text)TextView exp_duration_text;
    @BindView(R.id.add_exp_tool)Toolbar add_exp_tool;
    @Inject
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_add_work_exp);
        ButterKnife.bind(this);
        loadspinnerdata();
        setuptoolbar();
        set_auto_textview_adapter();

    }
    private void set_auto_textview_adapter() {
        comany_name.setAdapter(new CompanySuggessionAdapter(this,comany_name.getText().toString()));
        job.setAdapter(new JobSuggessionAdapter(this,job.getText().toString()));
    }

    private void setuptoolbar() {
        setSupportActionBar(add_exp_tool);
        add_exp_tool.setTitle(" Add Experience");
        add_exp_tool.setTitleTextColor(0xffffffff);
        add_exp_tool.setLogo(R.mipmap.arrowlleft);
        add_exp_tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddExperience.this, ExperienceActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();

            }
        });
    }

    private void loadspinnerdata() {
        ArrayAdapter<String> company_title_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.industry_array));
        company_title.setAdapter(company_title_adapter);
        ArrayAdapter<String> job_type_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.job_type_array));
        job_type.setAdapter(job_type_adapter);
        ArrayAdapter<String> locationadpater = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.location_type));
        exp_location_type.setAdapter(locationadpater);

    }
    @OnClick(R.id.add_exp)
    void onAddClick() {
        if(comany_name.getText().toString().length()==0||company_title.getText().toString().length()==0||exp_duration_text.getText().toString().length()==0||job.getText().toString().length()==0||job_type.getText().toString().length()==0||exp_location.getText().toString().length()==0||status.getText().toString().length()==0){
            Toast.makeText(AddExperience.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();

        }else{
            Date date=new Date();
            String[] parts = exp_duration_text.getText().toString().split("To");
            String from = parts[0]; // 004
            String to = parts[1];
            databaseHelper.setExperience(Experience.builder()
                    .setCompany(comany_name.getText().toString()).setFrom(from)
                    .setJob(job.getText().toString()).setTitle(company_title.getText().toString())
                    .setType(job_type.getText().toString()).setUpto(to)
                    .setLocation(new Location(exp_location.getText().toString(),exp_location_type.getText().toString()))
                    .setStatus(status.getText().toString()).setDate(date.toString()).setPostflag("0").setPutflag("1").setCreateflag("1").setUpdateflag("1").build());
            startActivity(new Intent(AddExperience.this,ExperienceActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

    }
    @OnClick(R.id.exp_duration)
    void onButtonClick(){
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
                                        + (++monthEnd) + "/" + yearEnd;
                                exp_duration_text.setText(date);
                            }
                        });
        smoothDateRangePickerFragment.show(getFragmentManager(), "Datepickerdialog");
    }

}
