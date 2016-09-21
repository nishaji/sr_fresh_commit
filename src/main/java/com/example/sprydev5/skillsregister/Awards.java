package com.example.sprydev5.skillsregister;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class Awards extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

Toolbar toolbar;
    EditText title,org,desc;
    TextView time;
    ImageView timeimg;
    Button addaward;
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awards);
        dbHelper=new DatabaseHelper(this);
        toolbar=(Toolbar)findViewById(R.id.addawardtool);
        title=(EditText)findViewById(R.id.awardtitle);
        org=(EditText)findViewById(R.id.awardorganisation);
        desc=(EditText)findViewById(R.id.awardescription);
        time=(TextView)findViewById(R.id.awarddurationtext);
        timeimg=(ImageView)findViewById(R.id.awardduration);
        addaward=(Button)findViewById(R.id.addaward);
        toolbar.setTitle(" Add Awards");
        toolbar.setLogo(R.drawable.arrowlleft);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Awards.this, ViewAndAddAwards.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();

            }
        });
        try {
            dbHelper = new DatabaseHelper(Awards.this);
            addaward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Date date=new Date();

                    if (title.getText().toString().length()==0 || org.getText().toString().length()==0 || desc.getText().toString().length()==0 || time.getText().toString().length()==0)
                    {
                        TastyToast.makeText(getApplicationContext(), "Searching for required fields !", TastyToast.LENGTH_LONG,
                                TastyToast.INFO);
                    } else {
                        try {
                            if (dbHelper.insert_awards(title.getText().toString(), org.getText().toString(), time.getText().toString(), desc.getText().toString())) {
                                TastyToast.makeText(getApplicationContext(), "Project saved successfully !", TastyToast.LENGTH_LONG,
                                        TastyToast.SUCCESS);
                                Intent intent = new Intent(Awards.this, ViewAndAddAwards.class);
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
        timeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        Awards.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(Awards.this.getFragmentManager(), "Datepickerdialog");
            }
        });

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date =" "+dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        time.setText(date);
    }
}
