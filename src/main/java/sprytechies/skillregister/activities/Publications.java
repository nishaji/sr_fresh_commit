package sprytechies.skillregister.activities;

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

import java.util.Calendar;
import java.util.Date;

import sprytechies.skillregister.database.DatabaseHelper;
import sprytechies.skillsregister.R;


public class Publications extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Toolbar toolbar;
    EditText title,org,desc;
    TextView time;
    ImageView timeimg;
    Button addpublication;
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publications);
        dbHelper = new DatabaseHelper(Publications.this);
        toolbar=(Toolbar)findViewById(R.id.addpublicationtool);
        title=(EditText)findViewById(R.id.publicationtitle);
        org=(EditText)findViewById(R.id.publicationorganisation);
        desc=(EditText)findViewById(R.id.publicationdescription);
        time=(TextView)findViewById(R.id.publicationtext);
        timeimg=(ImageView)findViewById(R.id.publicationduration);
        addpublication=(Button)findViewById(R.id.addpublication);
        toolbar.setTitle(" Add Awards");
        toolbar.setLogo(R.drawable.arrowlleft);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Publications.this, ViewAndAddPublication.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();

            }
        });
        try {

            addpublication.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Date date=new Date();

                    if (title.getText().toString().length()==0 || org.getText().toString().length()==0 || desc.getText().toString().length()==0 || time.getText().toString().length()==0)
                    {
                        TastyToast.makeText(getApplicationContext(), "Searching for required fields !", TastyToast.LENGTH_LONG,
                                TastyToast.INFO);
                    } else {
                        try {
                            if (dbHelper.insert_publication(title.getText().toString(), org.getText().toString(), time.getText().toString(), desc.getText().toString())) {
                                TastyToast.makeText(getApplicationContext(), "Project saved successfully !", TastyToast.LENGTH_LONG,
                                        TastyToast.SUCCESS);
                                Intent intent = new Intent(Publications.this, ViewAndAddPublication.class);
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
                        Publications.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(Publications.this.getFragmentManager(), "Datepickerdialog");
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date =" "+dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        time.setText(date);

    }
}
