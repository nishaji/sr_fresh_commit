package sprytechies.skillregister.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;



import sprytechies.skillregister.database.DatabaseHelper;
import sprytechies.skillsregister.R;


public class Certificates extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {
    EditText certname,certstatus,authority,rank;
    String stcertname,stcertstatus,stcertdate,stcerttype,stauthority,strank;
    MaterialBetterSpinner certtype;
    Button addcertficate;
     Toolbar toolbar;
    DatabaseHelper databaseHelper;
    TextView certdate;
    String edtid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificates);
        databaseHelper=new DatabaseHelper(this);
        toolbar = (Toolbar) findViewById(R.id.certificatetool);
        toolbar.setTitle(" Your Certificates");
        toolbar.setLogo(R.drawable.arrowlleft);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Certificates.this, LanucherActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
            }
        });
        certname=(EditText)findViewById(R.id.certificatename);
        certstatus=(EditText)findViewById(R.id.certstatus);
        certtype=(MaterialBetterSpinner) findViewById(R.id.certificatetype);
        authority=(EditText)findViewById(R.id.authority);
        rank=(EditText)findViewById(R.id.rank);
        certdate=(TextView)findViewById(R.id.certdate);
        addcertficate=(Button)findViewById(R.id.addcertificate);
        ArrayAdapter<String> certadpater = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.certificate_array));
        certtype.setAdapter(certadpater);
        addcertficate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Date date=new Date();
                    if (certname.getText().toString().length()==0 || certstatus.getText().toString().length()==0 || certtype.getText().toString().length()==0 || authority.getText().toString().length()==0 || rank.getText().toString().length()==0)
                    {
                        TastyToast.makeText(getApplicationContext(), "Searching for required fields !", TastyToast.LENGTH_LONG,
                                TastyToast.INFO);
                    }else{
                        try{
                            if(databaseHelper.insert_certificate(certname.getText().toString(),certstatus.getText().toString(),certtype.getText().toString(),certdate.getText().toString(),authority.getText().toString(),rank.getText().toString())){
                                TastyToast.makeText(getApplicationContext(), "Certificate added successfully !", TastyToast.LENGTH_LONG,
                                        TastyToast.SUCCESS);
                                Intent intent = new Intent(Certificates.this, ViewandEditCertificate.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                finish();
                                Date date1=new Date(certdate.getText().toString());
                                JSONObject jsonObject=new JSONObject();
                                jsonObject.put("name",certname.getText().toString());
                                jsonObject.put("status",certstatus.getText().toString());
                                jsonObject.put("type",certtype.getText().toString());
                                jsonObject.put("certdate",date1);
                                jsonObject.put("authority",authority.getText().toString());
                                jsonObject.put("rank",rank.getText().toString());
                                long idd=databaseHelper.getlastid();
                                databaseHelper.insert_personbit(idd,"mongo","cert_bit",jsonObject,"not_done","not_done","pending");
                            }else {
                                TastyToast.makeText(getApplicationContext(), "Could not save certificate!", TastyToast.LENGTH_LONG,
                                        TastyToast.ERROR);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            });
        ImageView img=(ImageView)findViewById(R.id.btcertdate);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        Certificates.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(Certificates.this.getFragmentManager(), "Datepickerdialog");
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Certificates.this, LanucherActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date =" "+dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        certdate.setText(date);
    }
}
