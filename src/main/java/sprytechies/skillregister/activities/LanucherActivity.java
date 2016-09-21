package sprytechies.skillregister.activities;


import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;




import sprytechies.skillregister.fragments.MenuFragment;
import sprytechies.skillregister.fragments.Permissionfragment;
import sprytechies.skillregister.fragments.ResumeList;
import sprytechies.skillregister.fragments.SyncDataFragment;
import sprytechies.skillregister.residemenu.ResideMenu;
import sprytechies.skillregister.residemenu.ResideMenuItem;
import sprytechies.skillsregister.R;

public class LanucherActivity extends FragmentActivity implements View.OnClickListener{

    private ResideMenu resideMenu;
    private LanucherActivity mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemCalendar;
    private ResideMenuItem itemSettings;
    private ResideMenuItem itemPermissions;
    String acesstoken;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homefragment);
        mContext = this;
        setUpMenu();
        if( savedInstanceState == null )
            changeFragment(new MenuFragment());
        SharedPreferences prefs = this.getSharedPreferences("accesstocken", MODE_PRIVATE);
        acesstoken = prefs.getString("acesstocken","acesstoken");
        System.out.println(acesstoken+"hhhhhhhhhhhh");
    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setUse3D(true);
        resideMenu.setBackground(R.color.irctc_menu_bg_color);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemHome     = new ResideMenuItem(this, R.drawable.icon_home,     "Home");
        itemProfile  = new ResideMenuItem(this, R.drawable.listt,  "Resume "+"\n"+"List");
        itemCalendar = new ResideMenuItem(this, R.drawable.refresh, "Sync");
        itemSettings = new ResideMenuItem(this, R.drawable.helpp, "Help");
        itemPermissions=new ResideMenuItem(this,R.drawable.filled,"Permisssion");

        itemHome.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
        itemCalendar.setOnClickListener(this);
        itemSettings.setOnClickListener(this);
        itemPermissions.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemPermissions, ResideMenu.DIRECTION_LEFT);

        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
       /* findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });*/
    }



    @Override
    public void onClick(View view) {

        if (view == itemHome){
            changeFragment(new MenuFragment());
        }else if (view == itemProfile){
            changeFragment(new ResumeList());
        }else if (view == itemCalendar){
            changeFragment(new SyncDataFragment());
        }else if (view == itemSettings){
            changeFragment(new SyncDataFragment());}
        else if (view == itemPermissions){
            changeFragment(new Permissionfragment());
        }

        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
            Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu(){
        return resideMenu;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
