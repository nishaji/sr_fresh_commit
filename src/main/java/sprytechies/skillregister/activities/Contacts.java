package sprytechies.skillregister.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.sdsmdg.tastytoast.TastyToast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONObject;

import java.util.Date;



import sprytechies.skillregister.database.DatabaseHelper;
import sprytechies.skillsregister.R;


public class Contacts extends AppCompatActivity {
String [] arr={"Primary","Alternate"};
    EditText contact,status;
    MaterialBetterSpinner type,category;
    Toolbar toolbar;
    Button addcontact;
    DatabaseHelper databaseHelper;
    String editid,deleteid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        databaseHelper=new DatabaseHelper(this);
        toolbar = (Toolbar) findViewById(R.id.contacttool);
        contact=(EditText)findViewById(R.id.contact);
        category=(MaterialBetterSpinner) findViewById(R.id.category);
        status=(EditText)findViewById(R.id.contactstatus);
        type=(MaterialBetterSpinner) findViewById(R.id.contacttype);
        addcontact=(Button)findViewById(R.id.addcontact);
        ArrayAdapter<String> catadpater = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.contact_category_array));
        category.setAdapter(catadpater);
        ArrayAdapter<String> typeadpater = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.contact_array));
        type.setAdapter(typeadpater);
        toolbar.setTitle("Contact Details");
        toolbar.setLogo(R.drawable.arrowlleft);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Contacts.this, ViewandAddContact.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
            }
        });
        addcontact.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Date date=new Date();
              try{
                  if (contact.getText().toString().length() == 0 || category.getText().toString().length() == 0 || status.getText().toString().length() == 0 || type.getText().toString().length() == 0) {
                      TastyToast.makeText(getApplicationContext(), "Searching for required fields", TastyToast.LENGTH_LONG,
                              TastyToast.INFO);
                  } else {
                      if(databaseHelper.insert_contact(contact.getText().toString(),category.getText().toString(),status.getText().toString(),type.getText().toString())) {
                          TastyToast.makeText(getApplicationContext(), "Contact saved successfully !", TastyToast.LENGTH_LONG,
                                  TastyToast.SUCCESS);
                          Intent intent = new Intent(Contacts.this, ViewandAddContact.class);
                          startActivity(intent);
                          overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                          long idd=databaseHelper.getlastid();
                          JSONObject jsonObject=new JSONObject();
                          jsonObject.put("contact",contact.getText().toString());
                          jsonObject.put("category",category.getText().toString());
                          jsonObject.put("status",status.getText().toString());
                          jsonObject.put("type",type.getText().toString());
                          databaseHelper.insert_personbit(idd,"mongo","contact_bit",jsonObject,"not_done","not_done","pending");
                      }
                      else {
                          TastyToast.makeText(getApplicationContext(), "Could not save Project!", TastyToast.LENGTH_LONG,
                                  TastyToast.ERROR);
                      }
                  }
              }catch (Exception e){
                  e.printStackTrace();
              }


          }
      });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == R.id.contact) {
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Contacts.this, LanucherActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }
}
