package com.example.sprydev5.skillsregister;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import android.widget.LinearLayout;
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


public class SignIn extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {

    private static final String TAG = "SignIn";
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    public static final String PREFS_NAME = "MyPrefsFile";
    Toolbar toolbar;
    Button login, signup;
    EditText username, password;
    private DatabaseHelper dbHelper;
    String userName, passwordd;
    ImageView gplus,linked;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private TextView btnLogin;
    private ProgressDialog progressDialog;
    User user;
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
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();

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
      /*  Intent intent = new Intent(SignIn.this, LanucherActivity.class);
        startActivity(intent);*/
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
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                pDialog.show();
/*
                Intent intent = new Intent(SignIn.this, LanucherActivity.class);
                startActivity(intent);*/

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
        loginButton.setReadPermissions("public_profile", "email", "user_friends", "user_birthday");
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

                            Log.e("response: ", response + "");
                            if (response != null) {
                                try {
                                    user = new User();
                                    user.facebookID = object.getString("id").toString();
                                    System.out.println("hii" + user.facebookID);
                                    // user.email = object.getString("email").toString();
                                    System.out.println("hii" + user.email);
                                    user.name = object.getString("name").toString();
                                    System.out.println("hii" + user.name);
                                    user.gender = object.getString("gender").toString();
                                    System.out.println("hii" + user.gender);
                                    //  user.birthday = object.getString("birthday").toString();
                                    System.out.println("hii" + user.birthday);


                                    PrefUtils.setCurrentUser(user, SignIn.this);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(SignIn.this, "welcome " + user.name, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignIn.this, LanucherActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        }

                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender, birthday");
            request.setParameters(parameters);
            request.executeAsync();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}