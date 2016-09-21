package com.example.sprydev5.skillsregister;

import android.app.LauncherActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;


public class Objective extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    FloatingActionButton samples;
    EditText edt;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objective);
        dbHelper = new DatabaseHelper(Objective.this);
        toolbar = (Toolbar) findViewById(R.id.ob_tool);
        toolbar.setLogo(R.drawable.arrowlleft);
        toolbar.setTitle(" Objective");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Objective.this, LanucherActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();

            }
        });

        samples = (FloatingActionButton) findViewById(R.id.samples);
        samples.setOnClickListener(this);
        edt=(EditText)findViewById(R.id.objective);

    }
    @Override
    public void onClick(View v) {

        final CharSequence[] items = getResources().getStringArray(R.array.objective_array);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Make your selection");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                edt.setText(items[item]);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_objective, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == R.id.saveobjective) {
            if(edt.getText().toString().length()==0){
                TastyToast.makeText(getApplicationContext(), "Searching for Objective", TastyToast.LENGTH_LONG,
                        TastyToast.INFO);
            }else{
                if (dbHelper.insert_objective(edt.getText().toString())) {
                    TastyToast.makeText(getApplicationContext(), "Objective saved successfully !", TastyToast.LENGTH_LONG,
                            TastyToast.SUCCESS);
                    Intent intent = new Intent(Objective.this, LanucherActivity.class);
                    startActivity(intent);
                } else {
                    TastyToast.makeText(getApplicationContext(), "Could not insert objective!", TastyToast.LENGTH_LONG,
                            TastyToast.ERROR);
                }
            }

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        String et1;
        et1=edt.getText().toString();
        if (!et1.isEmpty()) {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("Are you sure");
            builder.setMessage("you want to go back without saving your data ");
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Objective.this,LanucherActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setOnCancelListener(null);
            builder.show();

        }
        else{
            startActivity(new Intent(Objective.this,LanucherActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
            System.out.println("do nothing");
        }
finish();
    }
}

