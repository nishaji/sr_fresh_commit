package sprytechies.skillregister.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONObject;


import sprytechies.skillregister.adapters.SkillSugessionAdapeter;
import sprytechies.skillregister.database.DatabaseHelper;
import sprytechies.skillsregister.R;


public class Addskills extends AppCompatActivity {
    EditText descritpion;
    AutoCompleteTextView title;
    Button addskill;
    Toolbar toolbar;
DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addskills);
        databaseHelper=new DatabaseHelper(this);
        title=(AutoCompleteTextView)findViewById(R.id.auto_ski_title);
        title.setAdapter(new SkillSugessionAdapeter(this,title.getText().toString()));
        descritpion=(EditText)findViewById(R.id.ski_description);
        addskill=(Button)findViewById(R.id.addskill);
        toolbar = (Toolbar) findViewById(R.id.ski_tool);
        toolbar.setTitle(" Add Skill");
        toolbar.setLogo(R.drawable.arrowlleft);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Addskills.this, Skills.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();

            }
        });
        addskill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(title.getEditableText().toString().length()==0||descritpion.getEditableText().toString().length()==0){
                    TastyToast.makeText(getApplicationContext(), "Searching for required fields!", TastyToast.LENGTH_LONG,
                            TastyToast.INFO);
                } else {
                    try {
                        if (databaseHelper.insert_skills(title.getText().toString(), descritpion.getText().toString())) {

                            TastyToast.makeText(getApplicationContext(), "Skill data saved successfully !", TastyToast.LENGTH_LONG,
                                    TastyToast.SUCCESS);
                            Intent intent = new Intent(Addskills.this, Skills.class);
                            startActivity(intent);
                            finish();
                            String stskill = title.getText().toString();
                            String stdes = descritpion.getText().toString();
                            JSONObject hashmap = new JSONObject();
                            hashmap.put("type", stskill);
                            hashmap.put("level", stdes);
                            long idd=databaseHelper.getskilllastid();
                            System.out.println(idd+"lastid");
                            databaseHelper.insert_personbit(idd,"mongo","skill_bit",hashmap,"not_done","not_done","pending");                                            String skillbit = "skillbit";

                        } else {
                            TastyToast.makeText(getApplicationContext(), "Could not save skills!", TastyToast.LENGTH_LONG,
                                    TastyToast.ERROR);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

    }

}
