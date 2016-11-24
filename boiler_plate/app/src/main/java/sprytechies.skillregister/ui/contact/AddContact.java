package sprytechies.skillregister.ui.contact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sprytechies.skillregister.R;
import sprytechies.skillregister.data.local.DatabaseHelper;
import sprytechies.skillregister.data.model.Contact;
import sprytechies.skillregister.ui.base.BaseActivity;


public class AddContact extends BaseActivity {

    @BindView(R.id.add_contact_tool)Toolbar add_contact_tool;
    @BindView(R.id.contact)EditText contact;
    @BindView(R.id.contact_type)MaterialBetterSpinner contact_type;
    @BindView(R.id.category)MaterialBetterSpinner category;
    @BindView(R.id.add_contact)Button add_contact;
    @Inject
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
        setuptoolbar();
        loadSpinnerdata();

    }
    private void loadSpinnerdata() {
        ArrayAdapter<String> contact_type_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.contact_array));
        contact_type.setAdapter(contact_type_adapter);
        ArrayAdapter<String> contact_category_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.contact_category_array));
        category.setAdapter(contact_category_adapter);
    }

    private void setuptoolbar() {
        setSupportActionBar(add_contact_tool);
        add_contact_tool.setTitle(" Add Contact");
        add_contact_tool.setTitleTextColor(0xffffffff);
        add_contact_tool.setLogo(R.mipmap.arrowlleft);
        add_contact_tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddContact.this, ConatctActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();

            }
        });
    }
    @OnClick(R.id.add_contact)
    void add_contact(){
        if(contact.getText().toString().length()==0||contact_type.getText().toString().length()==0||category.getText().toString().length()==0){
            Toast.makeText(AddContact.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        }else{
            Date date=new Date();
            databaseHelper.setContact(Contact.builder().setContact(contact.getText().toString())
                    .setType(contact_type.getText().toString()).setStatus("pending").setCategory(category.getText().toString())
                    .setDate(date.toString()).setPostflag("0").setPutflag("1").setCreateflag("1").setUpdateflag("1").build());
            startActivity(new Intent(AddContact.this, ConatctActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }
}
