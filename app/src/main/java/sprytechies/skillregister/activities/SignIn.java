package sprytechies.skillregister.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import java.util.Random;

import sprytechies.skillregister.apicallclasses.ApiData;
import sprytechies.skillregister.database.DatabaseHelper;
import sprytechies.skillsregister.R;


public class SignIn extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {

    private static final String TAG = "SignIn";
    private static final int RC_SIGN_IN = 9001;
    private static int MAX_LENGTH = 10;
    private GoogleApiClient mGoogleApiClient;
    public static final String PREFS_NAME = "MyPrefsFile";
    Toolbar toolbar;
    Button login, signup;
    EditText username, password;
    private DatabaseHelper dbHelper;
    String userName, passwordd;
    ImageView gplus, linked;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private TextView btnLogin;
    private ProgressDialog progressDialog;
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private ConnectionResult mConnectionResult;
    private ProgressDialog pDialog;
    int MY_PERMISSIONS_REQUEST_GET_ACCOUNTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_sign_in);

        pDialog = new ProgressDialog(SignIn.this, AlertDialog.THEME_HOLO_DARK);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(true);
        pDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.styeprogress));
        pDialog.setCancelable(false);
        dbHelper = new DatabaseHelper(SignIn.this);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        gplus = (ImageView) findViewById(R.id.gplus);
        linked = (ImageView) findViewById(R.id.linkedin);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setLogo(R.drawable.arrowlleft);
        toolbar.setTitle(" Login");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        gplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGplus();

            }
        });
        linked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("linked in is calling");


            }
        });
        signup = (Button) findViewById(R.id.Signup);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = username.getText().toString();
                passwordd = password.getText().toString();
                if (userName.equals("")) {
                    TastyToast.makeText(getApplicationContext(), "Searching for username", TastyToast.LENGTH_LONG,
                            TastyToast.INFO);
                } else if (passwordd.equals("")) {
                    TastyToast.makeText(getApplicationContext(), "Searching for password", TastyToast.LENGTH_LONG,
                            TastyToast.INFO);
                } else {
                    String storedPassword = dbHelper.getSinlgeEntry(userName);
                    if (passwordd.equals(storedPassword)) {
                        TastyToast.makeText(getApplicationContext(), "Congrats: Login Successfull", TastyToast.LENGTH_LONG,
                                TastyToast.SUCCESS);
                        Intent intent = new Intent(SignIn.this, LanucherActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                        finish();
                    } else {
                        TastyToast.makeText(getApplicationContext(), "username or password does not match", TastyToast.LENGTH_LONG,
                                TastyToast.ERROR);
                    }
                }


            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this, Signup.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_PROFILE).build();


    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Method to resolve any signin errors
     */
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = " + result.getErrorCode());

        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }

    }

    private void signInWithGplus() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.GET_ACCOUNTS))) {
            } else {
                //user chose not to "show" permission request again or permissions not present.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.GET_ACCOUNTS, Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_GET_ACCOUNTS);
            }
        }
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }

    }

    @Override
    public void onConnected(Bundle arg0) {
        mSignInClicked = false;
        Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

        // Get user's information
        getProfileInformation();
        Intent intent = new Intent(SignIn.this, LanucherActivity.class);
        startActivity(intent);
        // Update the UI after signin


    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
    /**
     * Updating the UI, showing/hiding buttons and profile layout
     */


    /**
     * Fetching user's information name, email, profile pic
     */
    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                pDialog.show();
                System.out.println(currentPerson + "google plus response");
                String firstName = currentPerson.getName().getGivenName();
                String lastName = currentPerson.getName().getFamilyName();
                String id = currentPerson.getId();
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                String password="qwerty";
                System.out.println("googleplus"+firstName+"firstname"+lastName+"lastname"+email+"email");
                ApiData apiData = new ApiData(getApplicationContext());
                apiData.postuser(firstName,lastName,email,password);
                apiData.getsignin(email,password);
                apiData.postlinkdata("gooleplus","oauth",id,currentPerson.toString());
                SharedPreferences settings = getSharedPreferences(SignIn.PREFS_NAME, 0); // 0 - for private mode
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("hasLoggedIn", true);
                editor.apply();
               Intent intent = new Intent(SignIn.this, LanucherActivity.class);
                startActivity(intent);

            } else {
                Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //facebookmethods//
    @Override
    protected void onResume() {
        super.onResume();
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_birthday","user_location");
        btnLogin = (TextView) findViewById(R.id.fb);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(SignIn.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                loginButton.performClick();
                loginButton.setPressed(true);
                loginButton.invalidate();
                loginButton.registerCallback(callbackManager, mCallBack);
                loginButton.setPressed(false);
                loginButton.invalidate();


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (requestCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;
            pDialog.show();

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

            progressDialog.dismiss();
            GraphRequest request = GraphRequest.newMeRequest(
                    AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {

                            Log.e("response: ", response +"facebook user result");
                            Log.e("response: ", object +"facebook user json");
                            ApiData apiData = new ApiData(getApplicationContext());
                            if (response != null) {
                                String email="",firstname="",lastname="",password="qwerty",id;
                                try {
                                    if(object.has("email")){
                                        System.out.println("true");
                                        email = object.getString("email");
                                        firstname=object.getString("first_name");
                                        lastname=object.getString("last_name");
                                        id=object.getString("id");
                                        apiData.postuser(firstname,lastname,email,password);
                                        apiData.getsignin(email,password);
                                    }else{
                                        System.out.println("false");
                                       // String random=random();
                                      //  System.out.println("random"+random);
                                        firstname=object.getString("first_name");
                                        lastname=object.getString("last_name");
                                        id=object.getString("id");
                                        String randomemail="qwertytrewq@gmail.com";
                                        System.out.println(randomemail+"random");
                                       // apiData.postuser(firstname,lastname,randomemail,password);
                                       // apiData.getsignin(randomemail,password);
                                        apiData.postlinkdata("Facebook","oAuth",id,object.toString());
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }



                               /* SharedPreferences settings = getSharedPreferences(SignIn.PREFS_NAME, 0); // 0 - for private mode
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putBoolean("hasLoggedIn", true);
                                editor.apply();*/
                               /* Intent intent = new Intent(SignIn.this, LanucherActivity.class);
                                startActivity(intent);
                                finish();*/

                            }else{

                            }
                        }

                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender, birthday,link,first_name,last_name");
            request.setParameters(parameters);
            request.executeAsync();
            System.out.println(parameters+"params");
        }

        @Override
        public void onCancel() {
            progressDialog.dismiss();
        }

        @Override
        public void onError(FacebookException e) {
            progressDialog.dismiss();
        }
    };
    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(MAX_LENGTH);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}