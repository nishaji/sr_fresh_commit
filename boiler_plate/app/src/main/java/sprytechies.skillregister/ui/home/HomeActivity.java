package sprytechies.skillregister.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sprytechies.skillregister.R;
import sprytechies.skillregister.data.PullService;
import sprytechies.skillregister.data.local.DatabaseHelper;
import sprytechies.skillregister.data.model.SaveProfile;
import sprytechies.skillregister.data.model.SignUp;
import sprytechies.skillregister.data.remote.ApiClient;
import sprytechies.skillregister.data.remote.PostService;
import sprytechies.skillregister.data.remote.remote_model.Acc;
import sprytechies.skillregister.data.remote.remote_model.Login;
import sprytechies.skillregister.data.remote.remote_model.Permi;
import sprytechies.skillregister.data.remote.remote_model.Person;
import sprytechies.skillregister.data.remote.remote_model.User;
import sprytechies.skillregister.ui.base.BaseActivity;
import sprytechies.skillregister.ui.launcher.activity.ViewActivity;
import sprytechies.skillregister.util.NetworkUtil;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.btnSignin)Button sign_up;
    @BindView(R.id.btnSignup)Button sign_in;
    private TextView tvSignupInvoker;
    private LinearLayout llSignup;
    private TextView tvSigninInvoker;
    private LinearLayout llSignin;
    private Button btnSignup;
    private Button btnSignin;
    @BindView(R.id.user_email) EditText usr_email;
    @BindView(R.id.password)EditText password;
    @BindView(R.id.first_name) EditText first_name;
    @BindView(R.id.last_name) EditText last_name;
    @BindView(R.id.email) EditText email;
    @BindView(R.id.create_pass) EditText create_pass;
    @BindView(R.id.repeat_pass) EditText repeat_pass;
    @Inject DatabaseHelper databaseHelper;
    String id, access_token;
     public static String SHARED_PREFERENCE="my_preference";
    public static String LOGIN_SHARED_PREFERENCE="login_my_preference";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        tvSignupInvoker = (TextView) findViewById(R.id.tvSignupInvoker);
        tvSigninInvoker = (TextView) findViewById(R.id.tvSigninInvoker);
        btnSignup= (Button) findViewById(R.id.btnSignup);
        btnSignin= (Button) findViewById(R.id.btnSignin);
        llSignup = (LinearLayout) findViewById(R.id.llSignup);
        llSignin = (LinearLayout) findViewById(R.id.llSignin);
        tvSignupInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignupForm();
            }
        });

        tvSigninInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSigninForm();
            }
        });
        showSigninForm();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_right_to_left);
                btnSignup.startAnimation(clockwise);
                String stpassword = create_pass.getText().toString();
                String confirmPassword = repeat_pass.getText().toString();
                if (first_name.getText().toString().length() == 0 || last_name.getText().toString().length() == 0 || usr_email.getText().toString().length() == 0 || create_pass.getText().toString().length() == 0 || repeat_pass.getText().toString().length() == 0) {
                    Toast.makeText(HomeActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (!stpassword.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(usr_email.getText().toString()).matches()) {
                    usr_email.setError("Invalid Email");
                } else if(!NetworkUtil.isNetworkConnected(HomeActivity.this)){
                    Toast.makeText(getApplicationContext(), "You are not connected to the internet", Toast.LENGTH_LONG).show();
                } else {
                    databaseHelper.setUser(SignUp.builder().setFirst_name(first_name.getText().toString()).setLast_name(last_name.getText().toString())
                            .setEmail(usr_email.getText().toString()).setPassword(create_pass.getText().toString()).build());
                    databaseHelper.setProfile(SaveProfile.builder().setType("current").build());
                    PostService service = ApiClient.getClient().create(PostService.class);
                    User user = new User(SignUp.builder().setFirst_name(first_name.getText().toString()).setLast_name(last_name.getText().toString()).setEmail(usr_email.getText().toString())
                            .setPassword(create_pass.getText().toString()).build());
                    Call<User> call = service.createUser(user);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Log.v("RESPONSE_CODE", String.valueOf(response.code()));
                            if (response.code() == 200) {
                                calllogin(usr_email.getText().toString(), create_pass.getText().toString());
                                Toast.makeText(HomeActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                            } else if (response.code() == 422) {
                                Toast.makeText(HomeActivity.this, "Email already Registered", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                        }
                    });
                }

            }
        });
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_right_to_left);
                btnSignup.startAnimation(clockwise);
                if(email.getText().toString().length()==0||password.getText().toString().length()==0){
                    Toast.makeText(HomeActivity.this, "Email & Password cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                    Toast.makeText(getApplicationContext(),"Invalid email", Toast.LENGTH_LONG).show();
                    email.setError("Invalid Email");
                }else if(!NetworkUtil.isNetworkConnected(HomeActivity.this)){
                    Toast.makeText(getApplicationContext(), "You are not connected to the internet", Toast.LENGTH_LONG).show();
                } else {
                    PostService service = ApiClient.getClient().create(PostService.class);
                    Call<Login> call = service.login(email.getText().toString(),password.getText().toString());
                    call.enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            Log.v("RESPONSE_CODE", String.valueOf(response.code()));
                            if(response.code()==200){
                                access_token=response.body().getId();String id=response.body().getProfile().getId();
                                System.out.println(access_token+ " "+ id);
                                Toast.makeText(HomeActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                                SharedPreferences login = getSharedPreferences(LOGIN_SHARED_PREFERENCE, 0);
                                SharedPreferences.Editor editor = login.edit();editor.putBoolean("hasLoggedIn", true);editor.apply();
                                SharedPreferences token_id =getSharedPreferences(SHARED_PREFERENCE, 0);
                                SharedPreferences.Editor edit = token_id.edit();edit.putString("id", id);edit.putString("access_token",access_token);edit.apply();
                                databaseHelper.setProfile(SaveProfile.builder().setType("current").build());
                                startService(PullService.getStartIntent(HomeActivity.this));
                                startActivity(new Intent(HomeActivity.this, ViewActivity.class));
                            }
                            else{
                                Toast.makeText(HomeActivity.this, "You are not Registered", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    private void showSignupForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.15f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.85f;
        llSignup.requestLayout();

        tvSignupInvoker.setVisibility(View.GONE);
        tvSigninInvoker.setVisibility(View.VISIBLE);
        Animation translate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_right_to_left);
        llSignup.startAnimation(translate);

        Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_right_to_left);
        btnSignup.startAnimation(clockwise);

    }

    private void showSigninForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.85f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.15f;
        llSignup.requestLayout();

        Animation translate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_left_to_right);
        llSignin.startAnimation(translate);

        tvSignupInvoker.setVisibility(View.VISIBLE);
        tvSigninInvoker.setVisibility(View.GONE);
        Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_left_to_right);
        btnSignin.startAnimation(clockwise);
    }
    public void calllogin(String email, String password) {
        PostService service = ApiClient.getClient().create(PostService.class);
        Call<Login> call = service.login(email, password);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Log.v("RESPONSE_CODE", String.valueOf(response.code()));
                if (response.code() == 200) {
                    access_token = response.body().getId();
                    id = response.body().getProfile().getId();put_user(id);
                    SharedPreferences token_id = getSharedPreferences(SHARED_PREFERENCE, 0);
                    SharedPreferences.Editor editor = token_id.edit();
                    editor.putString("id", id);editor.putString("access_token", access_token);editor.apply();
                    SharedPreferences login = getSharedPreferences(LOGIN_SHARED_PREFERENCE, 0); // 0 - for private mode
                    SharedPreferences.Editor editor1 = login.edit();editor1.putBoolean("hasLoggedIn", true);editor1.apply();
                    startActivity(new Intent(HomeActivity.this, ViewActivity.class));
                }
            }
            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });
    }
    public void put_user(final String id){
        ArrayList<Permi> privacy_bit = new ArrayList<>();
        Acc account=new Acc();
        try {
            privacy_bit.add(new Permi("private","education"));
            privacy_bit.add(new Permi("private","experience"));
            privacy_bit.add(new Permi("private","contacts"));
            privacy_bit.add(new Permi("private","profile"));
            privacy_bit.add(new Permi("private","project"));
            privacy_bit.add(new Permi("private","certificate"));
            privacy_bit.add(new Permi("private","awards"));
            privacy_bit.add(new Permi("private","publications"));
            privacy_bit.add(new Permi("private","volunteer"));
            account.setAccessId(id);
            account.setAccessCode("");
            account.setPrivacy("private");
            account.setSyncCanReplace("false");
            account.setFb("false");
            account.setGplus("false");
            account.setIn("false");
        }catch (Exception e){
            e.printStackTrace();
        }
        PostService service = ApiClient.getClient().create(PostService.class);
        Person person=new Person(account,privacy_bit);
        Call<Person> call = service.put_user(id,access_token,person);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                Log.v("Per_RESPONSE_CODE", String.valueOf(response.code()));
                if(response.code()==200){

                }
            }
            @Override
            public void onFailure(Call<Person> call, Throwable t) {

            }
        });
    }
}
