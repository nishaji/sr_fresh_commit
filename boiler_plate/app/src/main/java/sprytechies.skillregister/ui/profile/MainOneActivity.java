package sprytechies.skillregister.ui.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import sprytechies.skillregister.R;
import sprytechies.skillregister.data.PullService;
import sprytechies.skillregister.ui.home.HomeActivity;
import sprytechies.skillregister.ui.launcher.activity.ViewActivity;
import sprytechies.skillregister.util.NetworkUtil;


public class MainOneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_one);
        SharedPreferences settings = getSharedPreferences(HomeActivity.LOGIN_SHARED_PREFERENCE, 0);
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);
        if (hasLoggedIn) {
            startActivity(new Intent(MainOneActivity.this, ViewActivity.class));
            if(!NetworkUtil.isNetworkConnected(this)){
                startService(PullService.getStartIntent(this));
            }

        } else {
            Thread timerThread = new Thread() {
                public void run() {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        startActivity(new Intent(MainOneActivity.this, HomeActivity.class));
                    }
                }
            };
            timerThread.start();
        }
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}






