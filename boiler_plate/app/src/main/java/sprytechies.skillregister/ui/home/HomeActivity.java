package sprytechies.skillregister.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sprytechies.skillregister.R;
import sprytechies.skillregister.ui.base.BaseActivity;
import sprytechies.skillregister.ui.signin.SignActivity;
import sprytechies.skillregister.ui.signup.SignUpActivity;


public class HomeActivity extends BaseActivity {
    @BindView(R.id.sign_up)Button sign_up;
    @BindView(R.id.sign_in)Button sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

    }
@OnClick(R.id.sign_up)
    void onSignupClick(){
    startActivity(new Intent(HomeActivity.this, SignUpActivity.class));
}
    @OnClick(R.id.sign_in)
    void onSignInClick(){
        startActivity(new Intent(HomeActivity.this, SignActivity.class));
    }
}
