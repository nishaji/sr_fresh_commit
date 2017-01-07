package sprytechies.skillregister.ui.education;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sprytechies.skillregister.R;
import sprytechies.skillregister.data.SyncService;
import sprytechies.skillregister.data.local.DatabaseHelper;
import sprytechies.skillregister.data.model.Education;
import sprytechies.skillregister.data.model.Location;
import sprytechies.skillregister.data.remote.postservice.CertificatePost;
import sprytechies.skillregister.data.remote.postservice.EducationPost;
import sprytechies.skillregister.ui.base.BaseActivity;
import sprytechies.skillregister.util.AndroidComponentUtil;
import sprytechies.skillregister.util.NetworkUtil;
import timber.log.Timber;

public class AddEducation extends BaseActivity {

    @BindView(R.id.school_name)AutoCompleteTextView school_name;
    @BindView(R.id.course_name)AutoCompleteTextView course_name;
    @BindView(R.id.school_type)MaterialBetterSpinner school_type;
    @BindView(R.id.edu_title)EditText edu_title;
    @BindView(R.id.edu_type)MaterialBetterSpinner edu_type;
    @BindView(R.id.location_name)EditText location_name;
    @BindView(R.id.location_type)MaterialBetterSpinner location_type;
    @BindView(R.id.cgpi)EditText cgpi;
    @BindView(R.id.cgpi_type)MaterialBetterSpinner cgpi_type;
    @BindView(R.id.add_education)Button add_education;
    @BindView(R.id.edu_duration)ImageView edu_duration;
    @BindView(R.id.edu_duration_text) TextView edu_duration_text;
    @BindView(R.id.add_education_tool)Toolbar add_edu_tool;
    @Inject DatabaseHelper databaseHelper;
    Date date=new Date();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_education);
        ButterKnife.bind(this);loadSpinnerValues();
        set_auto_textview_adapter();setuptoolbar();
        edu_duration_text.setHint(new SimpleDateFormat("dd/MM/yyyy").format(date));
    }

    private void set_auto_textview_adapter() {
        school_name.setAdapter(new SchoolSuggessionAdapter(this,school_name.getText().toString()));
        course_name.setAdapter(new CourseSuggessionAdapter(this,course_name.getText().toString()));
    }

    private void setuptoolbar() {
        setSupportActionBar(add_edu_tool);
        add_edu_tool.setTitleTextColor(0xffffffff);
        add_edu_tool.setLogo(R.mipmap.arrowlleft);
        add_edu_tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddEducation.this, EducationActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();
            }
        });
    }
    public void loadSpinnerValues(){
        ArrayAdapter<String> schooladpater = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.school_type));
        school_type.setAdapter(schooladpater);
        ArrayAdapter<String> locationadpater = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.location_type));
        location_type.setAdapter(locationadpater);
        ArrayAdapter<String> edutypeadpater = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.education_type_array));
        edu_type.setAdapter(edutypeadpater);
        ArrayAdapter<String> cgpietypeadpater = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.cgpitype_array));
        cgpi_type.setAdapter(cgpietypeadpater);

    }
    @OnClick(R.id.add_education)
    void onButtonClick() {
        if(cgpi.getText().toString().length()==0||cgpi_type.getText().toString().length()==0||course_name.getText().toString().length()==0||edu_type.getText().toString().length()==0||edu_title.getText().toString().length()==0||location_name.getText().toString().length()==0||location_type.getText().toString().length()==0||school_name.getText().toString().length()==0||school_type.getText().toString().length()==0){
            Toast.makeText(AddEducation.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        }else if(edu_duration_text.getText().toString().length()==0){
            Toast.makeText(AddEducation.this, "Please pick date", Toast.LENGTH_SHORT).show();
        }
        else {
            Date date=new Date();
            String[] parts = edu_duration_text.getText().toString().split("To");
            String from = parts[0];String to = parts[1];
            databaseHelper.setEducation(Education.builder()
                    .setCgpi(cgpi.getText().toString()).setCgpitype(cgpi_type.getText().toString()).setFrom(from).setUpto(to)
                    .setCourse(course_name.getText().toString()).setEdutype(edu_type.getText().toString())
                    .setLocation(new Location(location_name.getText().toString(),location_type.getText().toString()))
                    .setSchool(school_name.getText().toString()).setSchooltype(school_type.getText().toString())
                    .setTitle(edu_title.getText().toString()).setDate(date.toString()).setPostflag("0").setPutflag("0").setCreateflag("1").setUpdateflag("0").build());
            startActivity(new Intent(AddEducation.this,EducationActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        }
    }
    @OnClick(R.id.edu_duration)
    void onImageButtonClick(){
        SmoothDateRangePickerFragment smoothDateRangePickerFragment =
                SmoothDateRangePickerFragment
                        .newInstance(new SmoothDateRangePickerFragment.OnDateRangeSetListener() {
                            @Override
                            public void onDateRangeSet(SmoothDateRangePickerFragment view,
                                                       int yearStart, int monthStart,
                                                       int dayStart, int yearEnd,
                                                       int monthEnd, int dayEnd) {
                                String date = dayStart + "/" + (++monthStart) + "/" + yearStart + " To " + dayEnd + "/"
                                        + (++monthEnd) + "/" + yearEnd;edu_duration_text.setText(date);
                            }
                        });
        smoothDateRangePickerFragment.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AddEducation.this, EducationActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();
    }
}
