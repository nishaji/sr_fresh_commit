package sprytechies.skillregister.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment;
import com.sdsmdg.tastytoast.TastyToast;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


import org.json.JSONObject;

import java.util.Date;



import sprytechies.skillregister.database.DatabaseHelper;
import sprytechies.skillsregister.R;


public class AddProject extends AppCompatActivity {
    Toolbar toolbar;
    EditText projectname, responsibility, achievements;
    TextView duration;
    MaterialBetterSpinner role;
    private DatabaseHelper dbHelper;
    Button addproject;
    String edtid;
    final Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        projectname = (EditText) findViewById(R.id.projectname);
        role = (MaterialBetterSpinner) findViewById(R.id.role);
        responsibility = (EditText) findViewById(R.id.responsibility);
        duration = (TextView) findViewById(R.id.projectdurationtext);
        achievements = (EditText) findViewById(R.id.achievement);
        ArrayAdapter<String> roleadpater = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.project_role_array));
        role.setAdapter(roleadpater);
        addproject = (Button) findViewById(R.id.addproject);
        try {
            dbHelper = new DatabaseHelper(AddProject.this);
            addproject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Date date = new Date();

                    if (projectname.getText().toString().length() == 0 || role.getText().toString().length() == 0 || duration.getText().toString().length() == 0 || responsibility.getText().toString().length() == 0 || achievements.getText().toString().length() == 0) {
                        TastyToast.makeText(getApplicationContext(), "Searching for required fields !", TastyToast.LENGTH_LONG,
                                TastyToast.INFO);
                    } else {
                        try {
                            String ab = duration.getText().toString();
                            String[] parts = ab.split("To");
                            String from = parts[0]; // 004
                            String to = parts[1];
                            Date datefrom = new Date(from);
                            Date dateto = new Date(to);
                            if (dbHelper.insert_project(projectname.getText().toString(), role.getText().toString(), responsibility.getText().toString(), achievements.getText().toString(), duration.getText().toString())) {
                                TastyToast.makeText(getApplicationContext(), "Project saved successfully !", TastyToast.LENGTH_LONG,
                                        TastyToast.SUCCESS);
                                Intent intent = new Intent(AddProject.this, Projects.class);
                                startActivity(intent);
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("project", projectname.getText().toString());
                                jsonObject.put("from", datefrom);
                                jsonObject.put("upto", dateto);
                                jsonObject.put("role", role.getText().toString());
                                jsonObject.put("meta", responsibility.getText().toString());
                                long idd = dbHelper.getprojectlastid();
                                dbHelper.insert_personbit(idd, "mongo", "proj_bit", jsonObject, "not_done", "not_done", "pending");
                            } else {
                                TastyToast.makeText(getApplicationContext(), "Could not save Project!", TastyToast.LENGTH_LONG,
                                        TastyToast.ERROR);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        toolbar = (Toolbar) findViewById(R.id.addprojtool);
        toolbar.setTitle(" Add Project");
        toolbar.setLogo(R.drawable.arrowlleft);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProject.this, Projects.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();

            }
        });
        ImageView img = (ImageView) findViewById(R.id.projectduration);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                        duration.setText(date);
                                    }
                                });
                smoothDateRangePickerFragment.show(getFragmentManager(), "Datepickerdialog");
            }
        });

    }


    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_addproj, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == R.id.saveaddprojectdata) {
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        String et1, et2, et3, et4, et5, et6;
        et1 = projectname.getText().toString();
        et2 = role.getText().toString();
        et3 = responsibility.getText().toString();
        et4 = duration.getText().toString();
        et5 = achievements.getText().toString();


        if (!et1.isEmpty() || et2.isEmpty() || et3.isEmpty() || et4.isEmpty() || et5.isEmpty()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Are you sure");
            builder.setMessage("you want to go back without saving your data ");
            builder.setIcon(android.R.drawable.ic_dialog_alert);

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(AddProject.this, Projects.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    onBackPressed();
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setOnCancelListener(null);
            builder.show();

        } else {
            startActivity(new Intent(AddProject.this, Projects.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
            onBackPressed();
            System.out.println("do nothing");
        }

    }

}


