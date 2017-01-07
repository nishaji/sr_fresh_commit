package sprytechies.skillregister.ui.launcher.Permission;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sprytechies.skillregister.R;
import sprytechies.skillregister.data.remote.ApiClient;
import sprytechies.skillregister.data.remote.PostService;
import sprytechies.skillregister.data.remote.remote_model.Permi;
import sprytechies.skillregister.data.remote.remote_model.PermissionBit;
import sprytechies.skillregister.data.remote.remote_model.Person;
import sprytechies.skillregister.ui.base.BaseActivity;
import sprytechies.skillregister.ui.home.HomeActivity;
import sprytechies.skillregister.ui.launcher.activity.ViewActivity;


/**
 * Created by sprydev5 on 24/10/16.
 */

public class SetPermission extends BaseActivity {

    @BindView(R.id.edu)MaterialBetterSpinner edu;
    @BindView(R.id.exp)MaterialBetterSpinner exp;
    @BindView(R.id.contact)MaterialBetterSpinner contact;
    @BindView(R.id.cert)MaterialBetterSpinner cert;
    @BindView(R.id.award)MaterialBetterSpinner award;
    @BindView(R.id.project)MaterialBetterSpinner project;
    @BindView(R.id.publication)MaterialBetterSpinner publication;
    @BindView(R.id.volunteer)MaterialBetterSpinner volunteer;
    @BindView(R.id.profile)MaterialBetterSpinner profile;
    String edubit,expbit,certbit,contactbit,awardbit,projectbit,publicationbit,volunteerbit,profilebit;
    ArrayList<Permi> privacy_bit = new ArrayList<>();
    String id,access_token;
    @BindView(R.id.permission_tool)Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.permission);ButterKnife.bind(this);
        SharedPreferences settings = SetPermission.this.getSharedPreferences(HomeActivity.SHARED_PREFERENCE, 0);
        id = settings.getString("id", "id");access_token = settings.getString("access_token", "access_token");
        System.out.println(id+" "+access_token);
        setuptoolbar();loadSpinner();

    }
    private void setuptoolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(" Set Permission");
        toolbar.setTitleTextColor(0xffffffff);
        toolbar.setLogo(R.mipmap.arrowlleft);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SetPermission.this, ViewActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();

            }
        });
    }
   @OnClick(R.id.save_permission)
   void save_permission(){
            Log.d("Test", "onClickListener ist gestartet");
            edubit=edu.getText().toString();expbit=exp.getText().toString();
            certbit=cert.getText().toString();contactbit=contact.getText().toString();
            awardbit=award.getText().toString();projectbit=project.getText().toString();
            publicationbit=publication.getText().toString();volunteerbit=volunteer.getText().toString();
            profilebit=profile.getText().toString();
             if (edubit != null && !edubit.isEmpty()){
                privacy_bit.add(new Permi(edubit,"education"));
            }else{
                privacy_bit.add(new Permi("private","education"));
            }
           if(expbit !=null && !expbit.isEmpty()){
                privacy_bit.add(new Permi(expbit,"experience"));
            }else {
                privacy_bit.add(new Permi("private","experience"));
            }
            if(certbit !=null && !certbit.isEmpty()){
                privacy_bit.add(new Permi(certbit,"certificate"));
            }else{
                privacy_bit.add(new Permi("private","certificate"));
            }
            if(contactbit !=null && !contactbit.isEmpty()){
                privacy_bit.add(new Permi(contactbit,"contacts"));
            }else{
                privacy_bit.add(new Permi("private","contacts"));
            }

            if(awardbit !=null && !awardbit.isEmpty()){
                privacy_bit.add(new Permi(awardbit,"awards"));
            }else{
                privacy_bit.add(new Permi("private","awards"));
            }

            if(projectbit !=null && !projectbit.isEmpty()){
                privacy_bit.add(new Permi(projectbit,"project"));
            }else{
                privacy_bit.add(new Permi("private","project"));
            }

            if(publicationbit !=null && !publicationbit.isEmpty()){
                privacy_bit.add(new Permi(publicationbit,"publications"));
            }else{
                privacy_bit.add(new Permi("private","publications"));
            }

            if(volunteerbit !=null && !volunteerbit.isEmpty()){
                privacy_bit.add(new Permi(volunteerbit,"volunteer"));
            }else{
                privacy_bit.add(new Permi("private","volunteer"));
            }

            if(profilebit !=null && !profilebit.isEmpty()){
                privacy_bit.add(new Permi(profilebit,"profile"));
            }else{
                privacy_bit.add(new Permi("private","profile"));
            }
       PostService service = ApiClient.getClient().create(PostService.class);
           PermissionBit person=new PermissionBit(privacy_bit);
            Call<PermissionBit> call = service.put_permission(id,access_token,person);
            call.enqueue(new Callback<PermissionBit>() {
                @Override
                public void onResponse(Call<PermissionBit> call, Response<PermissionBit> response) {
                    String didItWork = String.valueOf(response.isSuccessful());
                    Log.v("SUCCESS?", didItWork);
                    if (response.code() == 200) {
                        System.out.println("sucessfull save permission");
                        startActivity(new Intent(SetPermission.this,SetPermission.class));
                    }
                }
                @Override
                public void onFailure(Call<PermissionBit> call, Throwable t) {

                }
            });

   }

    private void loadSpinner() {
       final ArrayAdapter<String> privacy = new ArrayAdapter<String>(SetPermission.this,
               android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.privacy));
        edu.setAdapter(privacy);exp.setAdapter(privacy);contact.setAdapter(privacy);
        profile.setAdapter(privacy);project.setAdapter(privacy);
        project.setAdapter(privacy);cert.setAdapter(privacy);award.setAdapter(privacy);
        publication.setAdapter(privacy);volunteer.setAdapter(privacy);
        PostService service = ApiClient.getClient().create(PostService.class);
        Call<Person> call = service.get_user(id,access_token);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call <Person> call, Response<Person> response) {
                String didItWork = String.valueOf(response.isSuccessful());
                Log.v("SUCCESS?", didItWork);
                if (response.code() == 200) {
                    Person person=response.body();
                    if(!person.getPermission().isEmpty()){
                        for (int i=0; i<person.getPermission().size();i++){
                            String code=response.body().getPermission().get(i).getCode();
                            String privacyy=response.body().getPermission().get(i).getPrivacy();
                            edu.setAdapter(privacy);
                            if(code.equals("education")) {
                                if (!privacyy.equals(null)) {
                                    int spinnerPosition = privacy.getPosition(privacyy);
                                    System.out.println(spinnerPosition);
                                    try {
                                        if (privacyy.trim().length() > 0) {
                                            edu.setText(privacy.getItem(spinnerPosition));
                                        } else {
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            exp.setAdapter(privacy);
                            if(code.equals("experience")) {
                                if (!privacyy.equals(null)) {
                                    int spinnerPosition = privacy.getPosition(privacyy);
                                    try {
                                        if (privacyy.trim().length() > 0) {
                                            exp.setText(privacy.getItem(spinnerPosition));
                                        } else {
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            contact.setAdapter(privacy);
                            if(code.equals("contacts")) {
                                if (!privacyy.equals(null)) {
                                    int spinnerPosition = privacy.getPosition(privacyy);
                                    try {
                                        if (privacyy.trim().length() > 0) {
                                            contact.setText(privacy.getItem(spinnerPosition));
                                        } else {
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            profile.setAdapter(privacy);
                            if(code.equals("profile")) {
                                if (!privacyy.equals(null)) {
                                    int spinnerPosition = privacy.getPosition(privacyy);
                                    try {
                                        if (privacyy.trim().length() > 0) {
                                            profile.setText(privacy.getItem(spinnerPosition));
                                        } else {
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            project.setAdapter(privacy);
                            if(code.equals("project")) {
                                if (!privacyy.equals(null)) {
                                    int spinnerPosition = privacy.getPosition(privacyy);
                                    try {
                                        if (privacyy.trim().length() > 0) {
                                            project.setText(privacy.getItem(spinnerPosition));
                                        } else {
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            cert.setAdapter(privacy);
                            if(code.equals("certificate")) {
                                if (!privacyy.equals(null)) {
                                    int spinnerPosition = privacy.getPosition(privacyy);
                                    try {
                                        if (privacyy.trim().length() > 0) {
                                            cert.setText(privacy.getItem(spinnerPosition));
                                        } else {
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            award.setAdapter(privacy);
                            if(code.equals("awards")) {
                                if (!privacyy.equals(null)) {
                                    int spinnerPosition = privacy.getPosition(privacyy);
                                    try {
                                        if (privacyy.trim().length() > 0) {
                                            award.setText(privacy.getItem(spinnerPosition));
                                        } else {
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            publication.setAdapter(privacy);
                            if(code.equals("publications")) {
                                if (!privacyy.equals(null)) {
                                    int spinnerPosition = privacy.getPosition(privacyy);
                                    try {
                                        if (privacyy.trim().length() > 0) {
                                            publication.setText(privacy.getItem(spinnerPosition));
                                        } else {
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            volunteer.setAdapter(privacy);
                            if(code.equals("volunteer")) {
                                if (!privacyy.equals(null)) {
                                    int spinnerPosition = privacy.getPosition(privacyy);
                                    try {
                                        if (privacyy.trim().length() > 0) {
                                            volunteer.setText(privacy.getItem(spinnerPosition));
                                        } else {
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }else{
                        System.out.println("no permission data");
                    }

                }
            }@Override
            public void onFailure(Call<Person> call, Throwable t) {
            }
        });

    }
}
