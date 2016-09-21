package com.example.sprydev5.skillsregister;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sprydev5.skillsregister.model.Workexperience;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;


public class ViewProfile extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    TextView t1,t2,t3,t4,t5;
    CollapsingToolbarLayout collapsingToolbarLayout;
    private String TAG="ViewProfile";
    ImageView viewimage;
    String location,email,number,position,Name,dob,usrid;
    CoordinatorLayout coordinatorLayout;
    TextView emptyView;
    byte[] byteArray;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        databaseHelper=new DatabaseHelper(this);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        emptyView = (TextView)findViewById(R.id.empty_view);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.viewprofile);
        setSupportActionBar(toolbar);
        t1=(TextView)findViewById(R.id.tvNumber1);
        t2=(TextView)findViewById(R.id.tvNumber2);
        t3=(TextView)findViewById(R.id.tvNumber3);
        t4=(TextView)findViewById(R.id.tvNumber4);
        t5=(TextView)findViewById(R.id.tvNumber5);
        viewimage=(ImageView)findViewById(R.id.detailpageimg);
        toolbar = (Toolbar) findViewById(R.id.viewprofiletool);
        toolbar.setTitle(" View Profile");
        toolbar.setLogo(R.drawable.arrowlleft);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfile.this, PersonalData.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });
try{
    ArrayList<String>people=databaseHelper.getAllpeople();
    System.out.println(people+"people");
    usrid=people.get(0);
    location=people.get(1);
    email=people.get(5);
    number=people.get(3);
    position=people.get(4);
    Name=people.get(2);
    dob=people.get(6);
    byteArray =databaseHelper.retreiveImageFromDB();
    Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    t1.setText(number);
    t2.setText(email);
    t3.setText(location);
    t4.setText(position);
    t5.setText(dob);
    collapsingToolbarLayout.setTitle(Name);
    viewimage.setImageBitmap(bmp);
    System.out.println("bytessssssssssss"+byteArray);

}catch (Exception e){
    e.printStackTrace();
}


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_viewprofile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == R.id.editprofile) {
            Intent intent=new Intent(ViewProfile.this,PersonalData.class);
            intent.putExtra("email",t2.getText().toString());
            intent.putExtra("number",t1.getText().toString());
            intent.putExtra("location",t3.getText().toString());
            intent.putExtra("position",t4.getText().toString());
            intent.putExtra("dob",t5.getText().toString());
            intent.putExtra("name",Name);
            intent.putExtra("id",usrid);
            intent.putExtra("img",byteArray);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }



}
