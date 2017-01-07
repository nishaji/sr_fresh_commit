package sprytechies.skillregister.ui.project;

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
import sprytechies.skillregister.data.model.Project;
import sprytechies.skillregister.data.remote.postservice.CertificatePost;
import sprytechies.skillregister.data.remote.postservice.ProjectPost;
import sprytechies.skillregister.ui.base.BaseActivity;
import sprytechies.skillregister.util.AndroidComponentUtil;
import sprytechies.skillregister.util.NetworkUtil;
import timber.log.Timber;


public class AddProject extends BaseActivity {
    @BindView(R.id.add_project_tool)Toolbar add_pro_tool;
    @BindView(R.id.project_name)EditText project_name;
    @BindView(R.id.role)MaterialBetterSpinner role;
    @BindView(R.id.responsibility)EditText responsibility;
    @BindView(R.id.add_project)Button add_project;
    @BindView(R.id.project_duration)ImageView du;
    @BindView(R.id.project_duration_text)TextView du_text;
    @Inject DatabaseHelper databaseHelper;
    Date date=new Date();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);setContentView(R.layout.activity_add_project);
        ButterKnife.bind(this);setuptoolbar();loadspinnerdata();
        du_text.setHint(new SimpleDateFormat("dd/MM/yyyy").format(date));
    }

    private void loadspinnerdata() {
        ArrayAdapter<String> role_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.project_role_array));
        role.setAdapter(role_adapter);
    }

    private void setuptoolbar() {
        setSupportActionBar(add_pro_tool);
        add_pro_tool.setTitleTextColor(0xffffffff);
        add_pro_tool.setLogo(R.mipmap.arrowlleft);
        add_pro_tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddProject.this, ProjectActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();
            }
        });
    }
    @OnClick(R.id.add_project)
    void onButtonClick(){
        if(project_name.getText().toString().length()==0||responsibility.getText().toString().length()==0||role.getText().toString().length()==0){
            Toast.makeText(AddProject.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        }else if(du_text.getText().toString().length()==0){
            Toast.makeText(AddProject.this, "Please pick date", Toast.LENGTH_SHORT).show();
        }
        else{
            Date date=new Date();
            String[] parts = du_text.getText().toString().split("To");
            String from = parts[0];String to = parts[1];
            databaseHelper.setProject(Project.builder()
                    .setProject(project_name.getText().toString()).setMeta(responsibility.getText().toString())
                    .setRole(role.getText().toString()).setFrom(from).setUpto(to)
                    .setDate(date.toString()).setPostflag("0").setPutflag("0").setCreateflag("1").setUpdateflag("0").build());
            startActivity(new Intent(AddProject.this,ProjectActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        }
    }
    @OnClick(R.id.project_duration)
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
                                        + (++monthEnd) + "/" + yearEnd;du_text.setText(date);
                            }
                        });
        smoothDateRangePickerFragment.show(getFragmentManager(), "Datepickerdialog");
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AddProject.this, ProjectActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();
    }
}
