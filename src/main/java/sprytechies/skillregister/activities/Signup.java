package sprytechies.skillregister.activities;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.sdsmdg.tastytoast.TastyToast;

import sprytechies.skillregister.apicallclasses.ApiData;
import sprytechies.skillregister.database.DatabaseHelper;
import sprytechies.skillsregister.R;


public class Signup extends AppCompatActivity {
    Toolbar toolbar;
    Button createacc,gobacklogin;
    EditText username,f_name,l_name,pass,repeatpass,email;
    private DatabaseHelper dbHelper;
String fname,lname,stemail,stpassword;
    public static final String PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        dbHelper = new DatabaseHelper(Signup.this);
        createacc = (Button) findViewById(R.id.createacc);
        gobacklogin = (Button) findViewById(R.id.gobacktologin);
        username=(EditText)findViewById(R.id.username);
        f_name=(EditText)findViewById(R.id.fname);
        l_name=(EditText)findViewById(R.id.lastname);
        pass=(EditText)findViewById(R.id.createpass);
        repeatpass=(EditText)findViewById(R.id.repeatpass);
        email=(EditText)findViewById(R.id.email);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setLogo(R.drawable.arrowlleft);
        toolbar.setTitle(" Sign up");
        toolbar.setTitleTextColor(0xffffffff);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, SignIn.class);
                startActivity(intent);
                overridePendingTransition(R.anim.swipe_down,R.anim.no_change);
                finish();
            }
        });
        setSupportActionBar(toolbar);
        createacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
try{
    fname=f_name.getText().toString();
    lname=l_name.getText().toString();
    stpassword = pass.getText().toString();
    String confirmPassword = repeatpass.getText().toString();
    stemail=email.getText().toString();
    if ( pass.getText().toString().length()==0
            || repeatpass.getText().toString().length()==0||f_name.getText().toString().length()==0||l_name.getText().toString().length()==0||email.getText().toString().length()==0) {
        TastyToast.makeText(getApplicationContext(), "Searching for required fields", TastyToast.LENGTH_LONG,
                TastyToast.INFO);

    }
    else if (!stpassword.equals(confirmPassword)) {
        TastyToast.makeText(getApplicationContext(),"Password does not match", TastyToast.LENGTH_LONG,
                TastyToast.ERROR);
    }
    else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
        TastyToast.makeText(getApplicationContext(),"Invalid Email", TastyToast.LENGTH_LONG, TastyToast.ERROR);
        email.setError("Invalid Email");
    }
    else {
        if (dbHelper.create_account(f_name.getText().toString(), l_name.getText().toString(), pass.getText().toString(), email.getText().toString())) {
            TastyToast.makeText(getApplicationContext(), "Account created successfully", TastyToast.LENGTH_LONG,
                    TastyToast.SUCCESS);
                        Intent intent = new Intent(Signup.this, LanucherActivity.class);
                        startActivity(intent);
                   overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);
                   finish();

            try{
                ApiData apiData = new ApiData(getApplicationContext());
                if(isNetworkAvailable()){
                    apiData.postuser(fname,lname,stemail,stpassword);
                    apiData.getsignin(stemail,stpassword);
                }else{
                    TastyToast.makeText(getApplicationContext(), "No internet connection", TastyToast.LENGTH_LONG,
                            TastyToast.ERROR);
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            SharedPreferences settings = getSharedPreferences(SignIn.PREFS_NAME, 0); // 0 - for private mode
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("hasLoggedIn", true);
            editor.apply();
        } else {
            TastyToast.makeText(getApplicationContext(), "could not insert data", TastyToast.LENGTH_LONG,
                    TastyToast.ERROR);
                          }
    }

}catch (Exception e){
    e.printStackTrace();
}


            }
        });
        gobacklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, SignIn.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);
            }
        });

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Signup.this, SignIn.class);
        startActivity(intent);
        overridePendingTransition(R.anim.swipe_down,R.anim.no_change);
        finish();
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        dbHelper.close();
    }



}


