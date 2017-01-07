package sprytechies.skillregister.ui.signin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import sprytechies.skillregister.R;
import sprytechies.skillregister.data.PullService;
import sprytechies.skillregister.data.local.DatabaseHelper;
import sprytechies.skillregister.data.remote.ApiClient;
import sprytechies.skillregister.data.remote.PostService;
import sprytechies.skillregister.data.remote.remote_model.Acc;
import sprytechies.skillregister.data.remote.remote_model.Login;
import sprytechies.skillregister.data.remote.remote_model.Permi;
import sprytechies.skillregister.data.remote.remote_model.Person;
import sprytechies.skillregister.ui.base.BaseActivity;
import sprytechies.skillregister.ui.launcher.activity.ViewActivity;
import timber.log.Timber;


public class SignActivity  extends BaseActivity {
    @BindView(R.id.username) EditText email;
    @BindView(R.id.password)EditText password;
    @BindView(R.id.login)Button login;
    @Inject
    DatabaseHelper databaseHelper;
    public static final String PREFS_NAME = "MyPrefsFile";
    String access_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.login)
    void onLoginClick(){
        if(email.getText().toString().length()==0||password.getText().toString().length()==0){
            Toast.makeText(SignActivity.this, "Email & Password cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
            Toast.makeText(getApplicationContext(),"Invalid email", Toast.LENGTH_LONG).show();
            email.setError("Invalid Email");
        }else {
            System.out.println("checking register user");

            PostService service = ApiClient.getClient().create(PostService.class);
            Call<Login> call = service.login(email.getText().toString(),password.getText().toString());
            call.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {
                    Log.v("RESPONSE_CODE", String.valueOf(response.code()));
                    if(response.code()==200){
                        startService(PullService.getStartIntent(SignActivity.this));
                        access_token=response.body().getId();
                        String id=response.body().getProfile().getId();
                        put_user(id);
                        Toast.makeText(SignActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignActivity.this, ViewActivity.class));
                        SharedPreferences settings = getSharedPreferences(SignActivity.PREFS_NAME, 0); // 0 - for private mode
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("hasLoggedIn", true);
                        editor.apply();
                        SharedPreferences token_id =getSharedPreferences(SignActivity.PREFS_NAME, 0);
                        SharedPreferences.Editor edit = token_id.edit();
                        edit.putString("id", id);
                        edit.putString("access_token",access_token);
                        edit.apply();
                    }
                    else{
                        Toast.makeText(SignActivity.this, "You are not Registered", Toast.LENGTH_SHORT).show();

                    }
                }
                @Override
                public void onFailure(Call<Login> call, Throwable t) {

                }
            });
        }

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
        String API_URL = "http://sr.api.sprytechies.net:3003/api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostService service = retrofit.create(PostService.class);
        Person person=new Person(account,privacy_bit);
        Call<Person> call = service.put_user(id,access_token,person);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                Log.v("RESPONSE_CODE", String.valueOf(response.code()));
            }
            @Override
            public void onFailure(Call<Person> call, Throwable t) {

            }
        });
    }
}
