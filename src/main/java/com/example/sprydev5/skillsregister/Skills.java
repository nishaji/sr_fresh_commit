package com.example.sprydev5.skillsregister;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sprydev5.skillsregister.model.Skill;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Exchanger;


public class Skills extends AppCompatActivity {
    final Context context = this;
    private RecyclerView recyclerView;
    private SkillAdapter adapter;
    Toolbar toolbar;
    private DatabaseHelper dbHandler;
    TextView emptyView;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);
        dbHandler = new DatabaseHelper(Skills.this);
        toolbar = (Toolbar) findViewById(R.id.skilltool);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.addskillfab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        toolbar.setTitle(" Your Skills");
        toolbar.setLogo(R.drawable.arrowlleft);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Skills.this, LanucherActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.skill_recycler);
        emptyView = (TextView)findViewById(R.id.empty_view);
        ArrayList<Skill> users = dbHandler.getallSkills();
        if (users.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        adapter = new SkillAdapter(users);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_addskill, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == R.id.saveskilltdata) {

        }
        return super.onOptionsItemSelected(item);
    }

    private void Createdialog() {
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.skill_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText skill = (EditText) promptsView.findViewById(R.id.ski_title);
        final EditText description = (EditText) promptsView.findViewById(R.id.ski_description);
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

Date date=new Date();
                                if(skill.getEditableText().toString().length()==0||description.getEditableText().toString().length()==0){
                                    TastyToast.makeText(getApplicationContext(), "Searching for required fields!", TastyToast.LENGTH_LONG,
                                            TastyToast.INFO);
                                } else {
                                    try {
                                        if (dbHandler.insert_skills(skill.getText().toString(), description.getText().toString())) {
                                            adapter.notifyDataSetChanged();
                                            TastyToast.makeText(getApplicationContext(), "Skill data saved successfully !", TastyToast.LENGTH_LONG,
                                                    TastyToast.SUCCESS);
                                            Intent intent = new Intent(Skills.this, Skills.class);
                                            startActivity(intent);
                                            finish();
                                            String stskill = skill.getText().toString();
                                            String stdes = description.getText().toString();
                                            JSONObject hashmap = new JSONObject();
                                            hashmap.put("type", stskill);
                                            hashmap.put("level", stdes);
                                            long idd=dbHandler.getlastid();
                                            System.out.println(id+"lastid");
                                            dbHandler.insert_personbit(idd,"mongo","skill_bit",hashmap,"not_done","not_done","pending");                                            String skillbit = "skillbit";

                                        } else {
                                            TastyToast.makeText(getApplicationContext(), "Could not save skills!", TastyToast.LENGTH_LONG,
                                                    TastyToast.ERROR);
                                        }
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }


                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Skills.this, LanucherActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }
}
