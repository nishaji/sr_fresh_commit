package com.example.sprydev5.skillsregister;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
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
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class Volunteers extends AppCompatActivity {
    Toolbar toolbar;
    EditText role, org,desc;
    TextView duration;
    MaterialBetterSpinner roletype;
    private DatabaseHelper dbHelper;
    Button addvolunteer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteers);
        dbHelper = new DatabaseHelper(this);
        role = (EditText) findViewById(R.id.volunrole);
        roletype = (MaterialBetterSpinner) findViewById(R.id.roletype);
        desc = (EditText) findViewById(R.id.description);
        duration = (TextView) findViewById(R.id.volunteertext);
        org = (EditText) findViewById(R.id.org);
        ArrayAdapter<String> roleadpater = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.project_role_array));
        roletype.setAdapter(roleadpater);
        addvolunteer=(Button)findViewById(R.id.addvolunteer);
        toolbar = (Toolbar) findViewById(R.id.addvoluntool);
        toolbar.setTitle(" Add Volunteers");
        toolbar.setLogo(R.drawable.arrowlleft);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Volunteers.this, ViewAndAddVolunteers.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();

            }
        });
        ImageView img=(ImageView)findViewById(R.id.volunduration);
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
        try {

            addvolunteer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String ab=duration.getText().toString();
                    String[] parts = ab.split("To");
                    String from = parts[0]; // 004
                    String to = parts[1];
                    if (role.getText().toString().length()==0 || roletype.getText().toString().length()==0 ||org.getText().toString().length()==0 || desc.getText().toString().length()==0 || duration.getText().toString().length()==0)
                    {
                        TastyToast.makeText(getApplicationContext(), "Searching for required fields !", TastyToast.LENGTH_LONG,
                                TastyToast.INFO);
                    } else {
                        try {
                            if (dbHelper.insert_volunteers(role.getText().toString(), roletype.getText().toString(),org.getText().toString(), from,to, desc.getText().toString())) {
                                TastyToast.makeText(getApplicationContext(), "Project saved successfully !", TastyToast.LENGTH_LONG,
                                        TastyToast.SUCCESS);
                                Intent intent = new Intent(Volunteers.this, ViewAndAddVolunteers.class);
                                startActivity(intent);

                            } else {
                                TastyToast.makeText(getApplicationContext(), "Could not save Project!", TastyToast.LENGTH_LONG,
                                        TastyToast.ERROR);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {

        super.onPause();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_addvolun, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == R.id.volunadd) {
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        String et1,et2,et3,et4,et5,et6;
        et1=role.getText().toString();
        et2=roletype.getText().toString();
        et3=desc.getText().toString();
        et4= duration.getText().toString();
        et5=org.getText().toString();


        if (!et1.isEmpty()||et2.isEmpty()||et3.isEmpty()||et4.isEmpty()||et5.isEmpty()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Are you sure");
            builder.setMessage("you want to go back without saving your data ");
            builder.setIcon(android.R.drawable.ic_dialog_alert);

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Volunteers.this,ViewAndAddVolunteers.class));
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

        }
        else{
            startActivity(new Intent(Volunteers.this,ViewAndAddVolunteers.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
            onBackPressed();
            System.out.println("do nothing");
        }

    }


}


