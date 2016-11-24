package sprytechies.skillregister.ui.signup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sprytechies.skillregister.R;
import sprytechies.skillregister.data.local.DatabaseHelper;
import sprytechies.skillregister.data.model.SaveProfile;
import sprytechies.skillregister.data.model.SignUp;
import sprytechies.skillregister.data.remote.ApiClient;
import sprytechies.skillregister.data.remote.PostService;
import sprytechies.skillregister.data.remote.remote_model.Login;
import sprytechies.skillregister.data.remote.remote_model.User;
import sprytechies.skillregister.ui.base.BaseActivity;
import sprytechies.skillregister.ui.base.MvpView;
import sprytechies.skillregister.ui.launcher.activity.ViewActivity;
import sprytechies.skillregister.ui.signin.SignActivity;


public class SignUpActivity extends BaseActivity implements MvpView {
    @BindView(R.id.first_name) EditText first_name;
    @BindView(R.id.last_name) EditText last_name;
    @BindView(R.id.email) EditText email;
    @BindView(R.id.create_pass) EditText create_pass;
    @BindView(R.id.repeat_pass) EditText repeat_pass;
    @BindView(R.id.create_acc) Button create_account;
    @Inject DatabaseHelper databaseHelper;
    public static final String PREFS_NAME = "MyPrefsFile";
    String id, access_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.create_acc)
    void createAccount() {
        String stpassword = create_pass.getText().toString();
        String confirmPassword = repeat_pass.getText().toString();
        if (first_name.getText().toString().length() == 0 || last_name.getText().toString().length() == 0 || email.getText().toString().length() == 0 || create_pass.getText().toString().length() == 0 || repeat_pass.getText().toString().length() == 0) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (!stpassword.equals(confirmPassword)) {
            Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            Toast.makeText(getApplicationContext(), "Invalid email does not match", Toast.LENGTH_LONG).show();
            email.setError("Invalid Email");
        } else {
            databaseHelper.setUser(SignUp.builder().setFirst_name(first_name.getText().toString()).setLast_name(last_name.getText().toString())
                    .setEmail(email.getText().toString()).setPassword(create_pass.getText().toString()).build());
            databaseHelper.setProfile(SaveProfile.builder().setMeta("").setType("current").build());
            PostService service = ApiClient.getClient().create(PostService.class);
            User user = new User(SignUp.builder().setFirst_name(first_name.getText().toString()).setLast_name(last_name.getText().toString()).setEmail(email.getText().toString())
                    .setPassword(create_pass.getText().toString()).build());
            Call<User> call = service.createUser(user);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Log.v("RESPONSE_CODE", String.valueOf(response.code()));
                    if (response.code() == 200) {
                        calllogin(email.getText().toString(), create_pass.getText().toString());
                        Toast.makeText(SignUpActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, ViewActivity.class));
                        SharedPreferences settings = getSharedPreferences(SignUpActivity.PREFS_NAME, 0); // 0 - for private mode
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("hasLoggedIn", true);
                        editor.apply();
                    } else if (response.code() == 422) {
                        Toast.makeText(SignUpActivity.this, "Email already Registered", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                }
            });
        }
    }
    @OnClick(R.id.go_back_to_login)
    void gobaktologin() {
        startActivity(new Intent(SignUpActivity.this, SignActivity.class));
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
                    id = response.body().getProfile().getId();
                    SharedPreferences token_id = getSharedPreferences(SignActivity.PREFS_NAME, 0);
                    SharedPreferences.Editor editor = token_id.edit();
                    editor.putString("id", id);
                    editor.putString("access_token", access_token);
                    editor.apply();
                }
            }
            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });
    }
}
