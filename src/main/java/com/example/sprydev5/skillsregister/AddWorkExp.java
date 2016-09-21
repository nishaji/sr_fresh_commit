package com.example.sprydev5.skillsregister;


import android.app.AlertDialog;
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

import org.json.JSONObject;
import java.util.Date;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class AddWorkExp extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    private EditText companyname,jobtitle,status,locationn;
    TextView expduration;
    Toolbar toolbar;
    Button addexp;
    String edtid;
    MaterialBetterSpinner jobtype,companytitle;
    String expfrom,expto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work_exp);
        dbHelper = new DatabaseHelper(AddWorkExp.this);
        companyname = (EditText) findViewById(R.id.companyname);
        companytitle = (MaterialBetterSpinner) findViewById(R.id.companytitle);
        jobtitle = (EditText) findViewById(R.id.jobtitle);
        jobtype = (MaterialBetterSpinner) findViewById(R.id.jobtype);
        status=(EditText)findViewById(R.id.status);
        expduration=(TextView) findViewById(R.id.expdurationtext);
        locationn=(EditText) findViewById(R.id.explocation);
        ArrayAdapter<String> comtitleada = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.industry_array));
        companytitle.setAdapter(comtitleada);
        ArrayAdapter<String> jobtypeada = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.job_type_array));
        jobtype.setAdapter(jobtypeada);
        addexp=(Button)findViewById(R.id.addexp);
        try {
            addexp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (companyname.getText().toString().length() == 0 || companytitle.getText().toString().length() == 0 || jobtitle.getText().toString().length() == 0 || jobtype.getText().toString().length() == 0 || expduration.getText().toString().length() == 0) {
                            TastyToast.makeText(getApplicationContext(), "Searching for required fields", TastyToast.LENGTH_LONG,
                                    TastyToast.INFO);
                        } else {
                            try {
                                String ab=expduration.getText().toString();
                                String[] parts = ab.split("To");
                                String from = parts[0]; // 004
                                String to = parts[1];
                                if (dbHelper.insert_work_exp(from,to,status.getText().toString(),companytitle.getText().toString(), companyname.getText().toString(), jobtitle.getText().toString(), jobtype.getText().toString(),locationn.getText().toString())) {
                                    TastyToast.makeText(getApplicationContext(), "Experience saved successfully !", TastyToast.LENGTH_LONG,
                                            TastyToast.SUCCESS);
                                    Intent intent = new Intent(AddWorkExp.this, WorkExperience.class);
                                    startActivity(intent);
                                    Date date=new Date(from);
                                    Date date1=new Date(to);
                                    JSONObject hashMap=new JSONObject();
                                    hashMap.put("from",date.toString());
                                    hashMap.put("upto",date1.toString());
                                    hashMap.put("status",status.getText().toString());
                                    hashMap.put("type",jobtype.getText().toString());
                                    hashMap.put("title",companytitle.getText().toString());
                                    hashMap.put("company",companyname.getText().toString());
                                    hashMap.put("job",jobtitle.getText().toString());
                                    hashMap.put("loctation",locationn.getText().toString());
                                    long id=dbHelper.getlastid();
                                    System.out.println(id+"lastid");
                                    dbHelper.insert_personbit(id,"mongo","exp_bit",hashMap,"not_done","not_done","pending");

                                } else {
                                    TastyToast.makeText(getApplicationContext(), "Could't insert experience !", TastyToast.LENGTH_LONG,
                                            TastyToast.SUCCESS);
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

        toolbar = (Toolbar) findViewById(R.id.addexptool);
        toolbar.setTitle(" Add Experience");
        toolbar.setLogo(R.drawable.arrowlleft);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddWorkExp.this, WorkExperience.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

        expduration = (TextView) findViewById(R.id.expdurationtext);
        ImageView img=(ImageView)findViewById(R.id.exptduration);
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
                                        expduration.setText(date);
                                    }
                                });
                smoothDateRangePickerFragment.show(getFragmentManager(), "Datepickerdialog");
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_addworkexp, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == R.id.saveaddexpdata) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        String et1,et2,et3,et4,et5;
        et1=companyname.getText().toString();
        et2=companytitle.getText().toString();
        et3=jobtype.getText().toString();
        et4= jobtitle.getText().toString();
        et5=expduration.getText().toString();


        if (!et1.isEmpty()||et2.isEmpty()||et3.isEmpty()||et4.isEmpty()||et5.isEmpty()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Are you sure");
            builder.setMessage("you want to go back without saving your data ");
            builder.setIcon(android.R.drawable.ic_dialog_alert);

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(AddWorkExp.this,WorkExperience.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
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
            startActivity(new Intent(AddWorkExp.this,WorkExperience.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
            System.out.println("do nothing");
        }

    }

}
