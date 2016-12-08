package sprytechies.skillregister.data;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sprytechies.skillregister.BoilerplateApplication;
import sprytechies.skillregister.data.local.DatabaseHelper;
import sprytechies.skillregister.data.model.Award;
import sprytechies.skillregister.data.model.Certificate;
import sprytechies.skillregister.data.model.Education;
import sprytechies.skillregister.data.model.Experience;
import sprytechies.skillregister.data.model.LiveSync;
import sprytechies.skillregister.data.model.Location;
import sprytechies.skillregister.data.model.Project;
import sprytechies.skillregister.data.model.SaveProfile;
import sprytechies.skillregister.data.model.SaveProfileInsert;
import sprytechies.skillregister.data.model.Volunteer;
import sprytechies.skillregister.data.remote.ApiClient;
import sprytechies.skillregister.data.remote.PostService;
import sprytechies.skillregister.data.remote.remote_model.Awrd;
import sprytechies.skillregister.data.remote.remote_model.Cert;
import sprytechies.skillregister.data.remote.remote_model.Edu;
import sprytechies.skillregister.data.remote.remote_model.Exp;
import sprytechies.skillregister.data.remote.remote_model.GetProfile;
import sprytechies.skillregister.data.remote.remote_model.Pro;
import sprytechies.skillregister.data.remote.remote_model.Volun;
import sprytechies.skillregister.ui.home.HomeActivity;
import sprytechies.skillregister.util.AndroidComponentUtil;
import sprytechies.skillregister.util.NetworkUtil;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;

/**
 * Created by sprydev5 on 16/11/16.
 */

public class PullService  extends Service {

    @Inject DataManager mDataManager;
    @Inject DatabaseHelper databaseHelper;
    private Subscription mSubscription;
    Date date=new Date();
    String id,access_token;
    ArrayList<String> ski;
    ArrayList<String> lan;
    String profile_id;
    Integer post_flag=1;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, PullService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.i(" Pull Service Created..");
        BoilerplateApplication.get(this).getComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        SharedPreferences settings = this.getSharedPreferences(HomeActivity.SHARED_PREFERENCE, 0);
        id = settings.getString("id", "id");
        access_token = settings.getString("access_token", "access_token");
        System.out.println("access-token in pull service" + " " + access_token + "id" + " " + id);
        if (!NetworkUtil.isNetworkConnected(this)) {
            Timber.i("Sync canceled, connection not available");
            AndroidComponentUtil.toggleComponent(this, PullService.SyncOnConnectionAvailable.class, true);
            stopSelf(startId);
            return START_NOT_STICKY;
        }
        get_profile_data();
        return START_STICKY;
    }

    private void get_profile_data() {
        RxUtil.unsubscribe(mSubscription);
        mSubscription = databaseHelper.getSavedProile()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<SaveProfileInsert>>() {
                               @Override
                               public void onCompleted() {
                               }

                               @Override
                               public void onError(Throwable e) {
                                   Timber.e(e, "There was an error loading the profile data.");
                               }

                               @Override
                               public void onNext(List<SaveProfileInsert> profile) {
                                   if (!profile.get(0).saveProfile().id().isEmpty()) {
                                       profile_id = profile.get(0).saveProfile().id();
                                   }
                               }
                           });
        PostService service = ApiClient.getClient().create(PostService.class);
        Call<List<Awrd>> call = service.list_award(id, access_token);
        call.enqueue(new Callback<List<Awrd>>() {
            @Override
            public void onResponse(Call<List<Awrd>> call, Response<List<Awrd>> response) {
                Log.v("RESPONSE_CALLED", "ON_RESPONSE_CALLED");
                String didItWork = String.valueOf(response.isSuccessful());
                Log.v("SUCCESS?", didItWork);
                Log.v("RESPONSE_CODE", String.valueOf(response.code()));
                if (response.code() == 200) {
                    List<Awrd> awards = response.body();
                    databaseHelper.flush_awards(Award.builder().build(),post_flag);
                    for (int i = 0; i < awards.size(); i++) {
                        databaseHelper.setAwards(Award.builder()
                                .setDescription(awards.get(i).getDesc()).setDuration(awards.get(i).getDate().toString())
                                .setOrganisation(awards.get(i).getOrg()).setTitle(awards.get(i).getTitle())
                                .setMongoid(awards.get(i).getId()).setDate(date.toString())
                                .setCreateflag("1").setUpdateflag("1").setPostflag("1").setPutflag("1").build());
                        databaseHelper.setSyncstatus(LiveSync.builder().setBit("award").setPost("1").build());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Awrd>> call, Throwable t) {
                System.out.println("checking failure" + t.getMessage() + Arrays.toString(t.getStackTrace()));
                System.out.println("checking failure" + t.getLocalizedMessage());

            }
        });
        Call<List<Cert>> call1 = service.list_certificate(id, access_token);
        call1.enqueue(new Callback<List<Cert>>() {
            @Override
            public void onResponse(Call<List<Cert>> call, Response<List<Cert>> response) {
                Log.v("RESPONSE_CALLED", "ON_RESPONSE_CALLED");
                String didItWork = String.valueOf(response.isSuccessful());
                Log.v("SUCCESS?", didItWork);
                Log.v("RESPONSE_CODE", String.valueOf(response.code()));
                if (response.code() == 200) {
                    List<Cert> cert = response.body();
                    databaseHelper.flush_certificate(Certificate.builder().build());
                    for (int i = 0; i < cert.size(); i++) {
                        databaseHelper.setCertificate(Certificate.builder()
                                .setDate(date.toString())
                                .setAuthority(cert.get(i).getAuthority()).setMongoid(cert.get(i).getId())
                                .setCreateflag("1").setUpdateflag("1").setPostflag("1").setPutflag("1")
                                .setCertdate(cert.get(i).getCertdate().toString()).setName(cert.get(i).getName())
                                .setRank(cert.get(i).getRank()).setType(cert.get(i).getType()).build());
                        databaseHelper.setSyncstatus(LiveSync.builder().setBit("certificate").setPost("1").build());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Cert>> call, Throwable t) {
                System.out.println("checking failure" + t.getMessage() + Arrays.toString(t.getStackTrace()));
                System.out.println("checking failure" + t.getLocalizedMessage());

            }
        });
        Call<List<Pro>> call2 = service.list_project(id, access_token);
        call2.enqueue(new Callback<List<Pro>>() {
            @Override
            public void onResponse(Call<List<Pro>> call, Response<List<Pro>> response) {
                Log.v("RESPONSE_CALLED", "ON_RESPONSE_CALLED");
                String didItWork = String.valueOf(response.isSuccessful());
                Log.v("SUCCESS?", didItWork);
                Log.v("RESPONSE_CODE", String.valueOf(response.code()));
                if (response.code() == 200) {
                    List<Pro> pros = response.body();
                    databaseHelper.flush_project(Project.builder().build(),post_flag);
                    for (int i = 0; i < pros.size(); i++) {
                        databaseHelper.setProject(Project.builder()
                                .setDate(date.toString()).setCreateflag("1").setUpdateflag("1").setPostflag("1").setPutflag("1")
                                .setFrom(pros.get(i).getFrom().toString()).setUpto(pros.get(i).getUpto().toString())
                                .setRole(pros.get(i).getRole()).setProject(pros.get(i).getProject())
                                .setMongoid(pros.get(i).getId()).build());
                        databaseHelper.setSyncstatus(LiveSync.builder().setBit("project").setPost("1").build());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Pro>> call, Throwable t) {
                System.out.println("checking failure" + t.getMessage() + Arrays.toString(t.getStackTrace()));
                System.out.println("checking failure" + t.getLocalizedMessage());

            }
        });
        Call<List<Edu>> call3 = service.list_education(id, access_token);
        call3.enqueue(new Callback<List<Edu>>() {
            @Override
            public void onResponse(Call<List<Edu>> call, Response<List<Edu>> response) {
                Log.v("RESPONSE_CALLED", "ON_RESPONSE_CALLED");
                String didItWork = String.valueOf(response.isSuccessful());
                Log.v("SUCCESS?", didItWork);
                Log.v("RESPONSE_CODE", String.valueOf(response.code()));
                if (response.code() == 200) {
                    List<Edu> edus = response.body();
                    databaseHelper.flush_education(Education.builder().build(),post_flag);
                    for (int i = 0; i < edus.size(); i++) {
                        databaseHelper.setEducation(Education.builder()
                                .setDate(date.toString()).setCreateflag("1").setUpdateflag("1").setPostflag("1").setPutflag("1")
                                .setFrom(edus.get(i).getFrom().toString())
                                .setUpto(edus.get(i).getUpto().toString())
                                .setLocation(new Location(edus.get(i).getLocation().getName(),edus.get(i).getLocation().getName()))
                                .setMongoid(edus.get(i).getId()).setSchool(edus.get(i).getSchool())
                                .setSchooltype(edus.get(i).getType()).setCgpi(edus.get(i).getCgpi()).setCgpitype(edus.get(i).getCgpitype())
                                .setCourse(edus.get(i).getCourse()).setTitle(edus.get(i).getTitle()).setEdutype(edus.get(i).getEdutype())
                                .build());
                        databaseHelper.setSyncstatus(LiveSync.builder().setBit("education").setPost("1").build());
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Edu>> call, Throwable t) {
                System.out.println("checking education failure" + t.getMessage());
            }

    });
        Call<List<Exp>> call4 = service.list_experience(id, access_token);
        call4.enqueue(new Callback<List<Exp>>() {
            @Override
            public void onResponse(Call<List<Exp>> call, Response<List<Exp>> response) {
                Log.v("RESPONSE_CALLED", "ON_RESPONSE_CALLED");
                String didItWork = String.valueOf(response.isSuccessful());
                Log.v("SUCCESS?", didItWork);
                Log.v("RESPONSE_CODE", String.valueOf(response.code()));
                if (response.code() == 200) {
                    List<Exp> edus = response.body();
                    databaseHelper.flush_experience(Experience.builder().build(),post_flag);
                    for (int i = 0; i < edus.size(); i++) {
                        databaseHelper.setExperience(Experience.builder()
                                .setDate(date.toString()).setCreateflag("1").setUpdateflag("1").setPostflag("1").setPutflag("1")
                                .setFrom(edus.get(i).getFrom().toString()).setUpto(edus.get(i).getUpto().toString())
                                .setLocation(new Location(edus.get(i).getLocation().getName(),edus.get(i).getLocation().getName()))
                                .setMongoid(edus.get(i).getId()).setJob(edus.get(i).getJob()).setType(edus.get(i).getType())
                                .setCompany(edus.get(i).getCompany()).setTitle(edus.get(i).getTitle()).setStatus(edus.get(i).getStatus())
                                .build());
                        databaseHelper.setSyncstatus(LiveSync.builder().setBit("experience").setPost("1").build());
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Exp>> call, Throwable t) {
                System.out.println("checking education failure" + t.getMessage());
            }

        });
        Call<List<Volun>> call5 = service.list_volunteer(id, access_token);
        call5.enqueue(new Callback<List<Volun>>() {
            @Override
            public void onResponse(Call<List<Volun>> call, Response<List<Volun>> response) {
                Log.v("RESPONSE_CALLED", "ON_RESPONSE_CALLED");
                String didItWork = String.valueOf(response.isSuccessful());
                Log.v("SUCCESS?", didItWork);
                Log.v("RESPONSE_CODE", String.valueOf(response.code()));
                if (response.code() == 200) {
                    List<Volun> voluns = response.body();
                    databaseHelper.flush_volunteer(Volunteer.builder().build());
                    for (int i = 0; i < voluns.size(); i++) {
                                 databaseHelper.setVolunteer(Volunteer.builder()
                                .setDate(date.toString()).setCreateflag("1").setUpdateflag("1").setPostflag("1").setPutflag("1")
                                .setFrom(voluns.get(i).getFrom().toString()).setUpto(voluns.get(i).getUpto().toString())
                                .setRole(voluns.get(i).getRole()).setMongoid(voluns.get(i).getId()).setType(voluns.get(i).getType())
                                .setOrganisation(voluns.get(i).getOrganization()).setDescription(voluns.get(i).getDesc()).build());
                        databaseHelper.setSyncstatus(LiveSync.builder().setBit("volunteer").setPost("1").build());
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Volun>> call, Throwable t) {
                System.out.println("checking education failure" + t.getMessage());
            }

        });
        JSONObject json=new JSONObject();
        JSONObject para=new JSONObject();
        try{
            json.put("type","current");
            para.put("where",json);
            System.out.println(json+" "+para);
        }catch (Exception e){
            e.printStackTrace();
        }

        Call<List<GetProfile>> call6 = service.get_profile(id,para,access_token);
        call6.enqueue(new Callback<List<GetProfile>>() {
            @Override
            public void onResponse(Call<List<GetProfile>> call, Response<List<GetProfile>> response) {
                Log.v("RESPONSE_CALLED", "ON_RESPONSE_CALLED");
                String didItWork = String.valueOf(response.isSuccessful());
                Log.v("SUCCESS?", didItWork);
                Log.v("RESPONSE_CODE", String.valueOf(response.code()));
                if (response.code() == 200) {
                    List<GetProfile> awards = response.body();
                    JSONObject skill;
                    JSONObject language;
                    JSONObject metaa,routine;
                    JSONArray skill_array = new JSONArray();
                    JSONArray language_array = new JSONArray();
                    JSONArray routine_array = new JSONArray();
                    for (int i = 0; i < awards.size(); i++) {
                          metaa=new JSONObject();
                        try {
                            metaa.put("books", awards.get(i).getMeta().getBooks().toString());
                            metaa.put("passion", awards.get(i).getMeta().getPassion().toString());
                            metaa.put("strength", awards.get(i).getMeta().getStrength().toString());
                            for (int l = 0; l < awards.get(i).getMeta().getRoutine().size(); l++) {
                                routine=new JSONObject();
                                routine.put("activity",awards.get(i).getMeta().getRoutine().get(l).getActivity());
                                routine.put("time",awards.get(i).getMeta().getRoutine().get(l).getTime());
                                routine_array.put(routine);
                                metaa.put("routine", routine_array);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        for (int k = 0; k < awards.get(i).getSkill().size(); k++) {
                            skill = new JSONObject();
                            try {
                                skill.put("code", awards.get(i).getSkill().get(k).getCode());
                                skill.put("level", awards.get(i).getSkill().get(k).getLevel());
                                skill.put("type", awards.get(i).getSkill().get(k).getType());
                                skill_array.put(skill);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        for (int j = 0; j < awards.get(i).getLanguage().size(); j++) {
                            language = new JSONObject();
                            try {
                                language.put("name", awards.get(i).getLanguage().get(j).getName());
                                language.put("level", awards.get(i).getLanguage().get(j).getLevel());
                                language_array.put(language);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        String meta=metaa.toString();
                        System.out.println(meta);
                        String languages = language_array.toString();
                        String skills = skill_array.toString();
                        databaseHelper.saveProfile(SaveProfile.builder().setType(awards.get(i).getType()).setMeta(meta).setskill(skills).setLan(languages).build(),profile_id);
                        databaseHelper.setSyncstatus(LiveSync.builder().setBit("profile").setPost("1").build());
                    }
                }
            }
            @Override
            public void onFailure(Call<List<GetProfile>> call, Throwable t) {
                System.out.println("checking failure" + t.getMessage() + Arrays.toString(t.getStackTrace()));
                System.out.println("checking failure" + t.getLocalizedMessage());

            }
        });
    }
    @Override
    public void onDestroy() {
        if (mSubscription != null) mSubscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static class SyncOnConnectionAvailable extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)
                    && NetworkUtil.isNetworkConnected(context)) {
                Timber.i("Connection is now available, triggering sync...");
                AndroidComponentUtil.toggleComponent(context, this.getClass(), false);
                context.startService(getStartIntent(context));
            }
        }
    }
}
