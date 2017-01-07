package sprytechies.skillregister.ui.launcher.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import sprytechies.skillregister.R;
import sprytechies.skillregister.data.SyncService;
import sprytechies.skillregister.data.remote.postservice.AwardPost;
import sprytechies.skillregister.ui.base.BaseActivity;
import sprytechies.skillregister.ui.launcher.DashBoard;
import sprytechies.skillregister.ui.launcher.Exportpdf.ExportPdf;
import sprytechies.skillregister.ui.launcher.Home.HomeFragment;
import sprytechies.skillregister.ui.launcher.MenuFragment;
import sprytechies.skillregister.ui.launcher.Permission.SetPermission;



public class ViewActivity extends BaseActivity {
    private DrawerLayout mDrawerLayout;
    private ImageView ivMenuUserProfilePhoto;
    NavigationView navigationView;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        activityComponent().inject(this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.mipmap.ic_menu_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.nav_drawer);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);createTabIcons();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_launcher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.sync:
                startService(SyncService.getStartIntent(this));

        }
        return super.onOptionsItemSelected(item);
    }
    private void createTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Add Profile");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.proff, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);
        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Dashboard");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.dash, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);
        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Preview Profile");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.dash, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new MenuFragment(), "Profile");
        adapter.addFragment(new DashBoard(), "Dashboard");
        adapter.addFragment(new HomeFragment(), "View Profile");viewPager.setAdapter(adapter);
    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        if (menuItem.getItemId() == R.id.set_permissions) {
                          startActivity(new Intent(ViewActivity.this,SetPermission.class));
                            overridePendingTransition(R.anim.animation, R.anim.animation2);
                        }
                        if (menuItem.getItemId() == R.id.export_pdf) {
                            startActivity(new Intent(ViewActivity.this,ExportPdf.class));
                            overridePendingTransition(R.anim.animation, R.anim.animation2);
                        }
                        if (menuItem.getItemId() == R.id.account_setting) {
                            startActivity(new Intent(ViewActivity.this,UserProfileActivity.class));
                            overridePendingTransition(R.anim.animation, R.anim.animation2);
                        }
                        return true;
                    }
                });
    }
    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert).setTitle("Closing Home")
                    .setMessage("Are you sure you want to exit?").setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null).show();
        }
    }



