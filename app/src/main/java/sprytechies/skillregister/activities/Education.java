package sprytechies.skillregister.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

//

import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment;
import com.sdsmdg.tastytoast.TastyToast;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


import org.json.JSONObject;

import java.util.Date;


import sprytechies.skillregister.adapters.SchoolSugessionAdapetr;
import sprytechies.skillregister.database.DatabaseHelper;
import sprytechies.skillsregister.R;

public class Education extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    EditText cgpi,locationname;
    AutoCompleteTextView schoolname;
    MaterialBetterSpinner schooltype,coursetype,educationtype,cgpitype,locationtype,educationtitle;
    Toolbar toolbar;
    Button addedu;
    TextView range;
   String from,to;
    String TAG="Education";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
        dbHelper = new DatabaseHelper(Education.this);
        toolbar = (Toolbar) findViewById(R.id.edu_tool);
        toolbar.setLogo(R.drawable.arrowlleft);
        toolbar.setTitle(" Add Education");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Education.this, ViewandAddeducation.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });
        schooltype = (MaterialBetterSpinner) findViewById(R.id.schooltype);
        schoolname = (AutoCompleteTextView) findViewById(R.id.autoschoolname);
        schoolname.setAdapter(new SchoolSugessionAdapetr(this,schoolname.getText().toString()));
        coursetype = (MaterialBetterSpinner) findViewById(R.id.coursetype);
        educationtitle = (MaterialBetterSpinner) findViewById(R.id.edutitle);
        cgpi = (EditText) findViewById(R.id.cgpi);
        cgpitype = (MaterialBetterSpinner) findViewById(R.id.cgpitype);
        locationname = (EditText) findViewById(R.id.locationname);
        locationtype = (MaterialBetterSpinner) findViewById(R.id.locationtype);
        addedu=(Button)findViewById(R.id.addeducation);
        educationtype=(MaterialBetterSpinner)findViewById(R.id.edutype);
        range = (TextView) findViewById(R.id.educationtdurationtext);
        ArrayAdapter<String> schooladpater = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.school_type));
        schooltype.setAdapter(schooladpater);
        ArrayAdapter<String> locationadpater = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.location_type));
        locationtype.setAdapter(locationadpater);
        ArrayAdapter<String> edutypeadpater = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.education_type_array));
        educationtype.setAdapter(edutypeadpater);
        ArrayAdapter<String> edutitleadpater = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.education_title_array));
        educationtitle.setAdapter(edutitleadpater);
        ArrayAdapter<String> coursetypeadpater = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.course_type_array));
        coursetype.setAdapter(coursetypeadpater);
        ArrayAdapter<String> cgpietypeadpater = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.cgpitype_array));
        cgpitype.setAdapter(cgpietypeadpater);
        ImageView img=(ImageView)findViewById(R.id.eduduration);
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
                                        range.setText(date);
                                    }
                                });
                smoothDateRangePickerFragment.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        try{
            addedu.setOnClickListener(new View.OnClickListener() {
                SharedPreferences prefs = getSharedPreferences("edumongoid", MODE_PRIVATE);
               // String mongoid = prefs.getString("edumongoid","id");
                String mongoid="notsynced";

                    @Override
                    public void onClick(View v) {
                        System.out.println(mongoid+"mongoid");
                        try{
                            if (schoolname.getText().toString().length() == 0 || schooltype.getText().toString().length() == 0 || coursetype.getText().toString().length() == 0 || educationtitle.getText().toString().length() == 0 ||educationtype.getText().toString().length() == 0|| cgpi.getText().toString().length() == 0 || cgpitype.getText().toString().length() == 0 || locationtype.getText().toString().length() == 0 || locationname.getText().toString().length() == 0 || range.getText().toString().length() == 0) {
                                TastyToast.makeText(getApplicationContext(), "Searching for required fields !", TastyToast.LENGTH_LONG,
                                        TastyToast.INFO);
                            } else if(mongoid.length()==0) {
                                TastyToast.makeText(getApplicationContext(), "Searching for mongoid fields !", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                System.out.println(TAG+"" +mongoid+" "+"mongoidcheck");
                            }
                             else{
                                String ab=range.getText().toString();
                                String[] parts = ab.split("To");
                                from = parts[0]; // 004
                                to = parts[1];
                                String school=schoolname.getText().toString().trim();
                                String schooltypee=schooltype.getText().toString().trim();
                                String type=coursetype.getText().toString().trim();
                                String title=educationtitle.getText().toString().trim();
                                String edutype=educationtype.getText().toString().trim();
                                String cgpii=cgpi.getText().toString();
                                String cgtype=cgpitype.getText().toString();
                                String loc=locationname.getText().toString();
                                String loctype=locationtype.getText().toString();
                                JSONObject jsonObject=new JSONObject();
                                jsonObject.put("name",loc);
                                jsonObject.put("type",loctype);
                                Date date=new Date(from);
                                Date date1=new Date(to);
                                if (dbHelper.insert_education(schoolname.getText().toString(), schooltype.getText().toString(), coursetype.getText().toString(), educationtitle.getText().toString(),educationtype.getText().toString(), cgpi.getText().toString(), cgpitype.getText().toString(), locationname.getText().toString(), locationtype.getText().toString(), from,to,mongoid)) {
                                    TastyToast.makeText(getApplicationContext(), "Education data saved successfully !", TastyToast.LENGTH_LONG,
                                            TastyToast.SUCCESS);
                                    JSONObject hashmap=new JSONObject();
                                    JSONObject hashmap1=new JSONObject();
                                    hashmap.put("school",school);
                                    hashmap.put("from",date.toString());
                                    hashmap.put("upto",date1.toString());
                                    hashmap.put("course",type);
                                    hashmap.put("type",schooltypee);
                                    hashmap.put("title",title);
                                    hashmap.put("edutype",edutype);
                                    hashmap.put("cgpi",cgpii);
                                    hashmap.put("cgpitype",cgtype);
                                    hashmap.put("location",jsonObject);
                                    hashmap1.put("edu_bit",hashmap);
                                    long id=dbHelper.geteducationlastid();
                                    System.out.println(id+"lastid");
                                    dbHelper.insert_personbit(id,"mongo","edu_bit",hashmap,"not_done","not_done","pending");
                                    //String edubit="edubit";
                                   // Intent serviceIntent = new Intent(Education.this,MyService.class);
                                   // serviceIntent.putExtra("edudata", edubit);
                                   // startService(serviceIntent);

                                    System.out.println(hashmap+"educationdataaaaaaaaaaaaaaaaaaaaa");
                                    Intent intent=new Intent(Education.this,ViewandAddeducation.class);
                                    startActivity(intent);


                                } else {
                                    TastyToast.makeText(getApplicationContext(), "Could not insert education", TastyToast.LENGTH_LONG,
                                            TastyToast.ERROR);
                                }

                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });

        }

        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == R.id.saveeducationdata) {
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        String et1,et2,et3,et4,et5,et6,et7,et8,et9;
        et1=schoolname.getText().toString();
        et2=schooltype.getText().toString();
        et3=coursetype.getText().toString();
        et4= educationtitle.getText().toString();
        et5=cgpi.getText().toString();
        et6=cgpitype.getText().toString();
        et7=locationname.getText().toString();
        et8=locationtype.getText().toString();
        et9=range.getText().toString();

        if (!et1.isEmpty()||et2.isEmpty()||et3.isEmpty()||et4.isEmpty()||et5.isEmpty()||et6.isEmpty()||et7.isEmpty()||et8.isEmpty()||et9.isEmpty()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Are you sure");
            builder.setMessage("you want to go back without saving your data ");
            builder.setIcon(android.R.drawable.ic_dialog_alert);

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Education.this,ViewandAddeducation.class));
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
            startActivity(new Intent(Education.this,ViewandAddeducation.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
            System.out.println("do nothing");
        }

    }

}


