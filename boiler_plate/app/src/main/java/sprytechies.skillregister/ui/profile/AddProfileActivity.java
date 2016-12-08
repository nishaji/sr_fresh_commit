package sprytechies.skillregister.ui.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sprytechies.skillregister.R;
import sprytechies.skillregister.data.local.DatabaseHelper;
import sprytechies.skillregister.data.model.SaveProfile;
import sprytechies.skillregister.data.model.SaveProfileInsert;
import sprytechies.skillregister.data.remote.postservice.PostProfile;
import sprytechies.skillregister.ui.base.BaseActivity;
import sprytechies.skillregister.ui.launcher.activity.ViewActivity;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;


public class AddProfileActivity extends BaseActivity {

    @Inject DatabaseHelper databaseHelper;
    private Subscription mSubscription;
    @BindView(R.id.profile_fab) FloatingActionButton fab;
    @BindView(R.id.add_profile_tool) Toolbar toolbar;
    @BindView(R.id.book_card) CardView book_card;
    @BindView(R.id.skills_card) CardView skills_card;
    @BindView(R.id.passion_card) CardView passion_card;
    @BindView(R.id.strength_card) CardView strength_card;
    @BindView(R.id.language_card) CardView language_card;
    @BindView(R.id.routine_card) CardView routine_card;
    @BindView(R.id.empty_text)TextView empty;
    String books,book, profile_id , strengths, passions, skills, languages,meta,routine;
    String book_arry[],strength_arry[],passion_arry[];
    ArrayList<String>skill_name,skill_name_two,lan,lan_two,routine_date,routine_data_two;
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.personaldata);
        ButterKnife.bind(this);setuptoolbar();set_up_card();
    }

    private void set_up_card() {
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
                        if(books != null || passions != null || strengths !=null || skills != null || languages !=null || routine !=null) {
                            System.out.println("do nothing");
                        }else{
                            empty.setVisibility(View.VISIBLE);
                        }
                        meta=profile.get(0).saveProfile().meta();
                        try {
                            JSONObject obj=new JSONObject();
                            if (meta !=null && !meta.isEmpty()) {
                                obj = new JSONObject(meta);
                                if(!obj.getString("books").isEmpty())
                                books = obj.getString("books");
                                if(!obj.getString("strength").isEmpty())
                                    strengths = obj.getString("strength");
                                if(!obj.getString("passion").isEmpty())
                                    passions = obj.getString("passion");
                                if(!obj.getString("routine").isEmpty())
                                    routine = obj.getString("routine");
                            }else{
                                 meta="";
                            }
                            if (books !=null && !books.isEmpty()) {

                                books = obj.getString("books");
                            }else{
                                books="";
                            }
                            if (strengths !=null && !strengths.isEmpty()) {
                                strengths = obj.getString("strength");

                            }else{
                                strengths="";
                            }
                            if (passions !=null && !passions.isEmpty()) {
                                passions = obj.getString("passion");
                            }else{
                               passions="";
                            }if (routine !=null && !routine.isEmpty()) {
                                routine = obj.getString("routine");
                            }else{
                                routine="";
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        skills  = profile.get(0).saveProfile().skill();
                        languages  = profile.get(0).saveProfile().lan();

                        if (!profile.get(0).saveProfile().id().isEmpty()) {
                            profile_id = profile.get(0).saveProfile().id();
                        }
                        if (books != null && !books.isEmpty()) {
                            book_card.setVisibility(View.VISIBLE);
                            int indexOfOpenBracket = books.indexOf("[");
                            int indexOfLastBracket = books.lastIndexOf("]");
                             book = books.substring(indexOfOpenBracket + 1, indexOfLastBracket);
                             book_arry = book.split(",");
                            for (int i = 0; i < 3; i++) {
                                LinearLayout row = new LinearLayout(AddProfileActivity.this);
                                row.setOrientation(LinearLayout.VERTICAL);
                                row.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                TextView book_header = new TextView(AddProfileActivity.this);
                                book_header.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                book_header.setText(" " + "Books");
                                params.setMargins(20,30,0,0);
                                book_header.setLayoutParams(params);
                                book_header.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.result_font));
                                book_header.setTextColor(BLUE);
                                row.addView(book_header);
                                for (int j = 0; j < book_arry.length; j++) {
                                    String ab = book_arry[j];
                                    TextView book_text = new TextView(AddProfileActivity.this);
                                    book_text.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                    book_text.setText("\u25CF" + " " + ab.trim() + " ");
                                    book_text.setTextColor(BLACK);
                                    row.addView(book_text);
                                }
                                book_card.addView(row);
                            }

                        }
                        if (strengths != null && !strengths.isEmpty()) {
                            strength_card.setVisibility(View.VISIBLE);
                            int indexOfOpenBracket = strengths.indexOf("[");
                            int indexOfLastBracket = strengths.lastIndexOf("]");
                            String strength = strengths.substring(indexOfOpenBracket + 1, indexOfLastBracket);
                            strength_arry = strength.split(",");
                            for (int i = 0; i < 3; i++) {
                                LinearLayout row = new LinearLayout(AddProfileActivity.this);
                                row.setOrientation(LinearLayout.VERTICAL);
                                row.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                TextView strength_header = new TextView(AddProfileActivity.this);
                                strength_header.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                strength_header.setText(" " + "Strength");
                                params.setMargins(20,30,0,0);
                                strength_header.setLayoutParams(params);
                                strength_header.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.result_font));
                                strength_header.setTextColor(BLUE);
                                row.addView(strength_header);
                                for (int j = 0; j < strength_arry.length; j++) {
                                    String ab = strength_arry[j];
                                    TextView book_text = new TextView(AddProfileActivity.this);
                                    book_text.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                    book_text.setText("\u25CF" + " " + ab.trim() + " ");
                                    book_text.setTextColor(BLACK);
                                    row.addView(book_text);
                                }
                                strength_card.addView(row);
                            }
                        }
                        if (passions != null && !passions.isEmpty()) {
                            passion_card.setVisibility(View.VISIBLE);
                            int indexOfOpenBracket = passions.indexOf("[");
                            int indexOfLastBracket = passions.lastIndexOf("]");
                            String passion = passions.substring(indexOfOpenBracket + 1, indexOfLastBracket);
                            passion_arry = passion.split(",");
                            for (int i = 0; i < 3; i++) {
                                LinearLayout row = new LinearLayout(AddProfileActivity.this);
                                row.setOrientation(LinearLayout.VERTICAL);
                                row.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                TextView passion_header = new TextView(AddProfileActivity.this);
                                passion_header.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                passion_header.setText(" " + "Passion");
                                params.setMargins(20,30,0,0);
                                passion_header.setLayoutParams(params);
                                passion_header.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.result_font));
                                passion_header.setTextColor(BLUE);
                                row.addView(passion_header);
                                for (int j = 0; j < passion_arry.length; j++) {
                                    String ab = passion_arry[j];
                                    TextView book_text = new TextView(AddProfileActivity.this);
                                    book_text.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                    book_text.setText("\u25CF" + " " + ab.trim() + " ");
                                    book_text.setTextColor(BLACK);
                                    row.addView(book_text);
                                }
                                passion_card.addView(row);
                            }
                        }
                        if (skills != null && !skills.isEmpty()) {
                            skills_card.setVisibility(View.VISIBLE);
                            skill_name = new ArrayList<String>();
                        try {
                            JSONArray jsonarray = new JSONArray(skills);
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                String codee = jsonobject.getString("code");
                                skill_name.add(codee.concat(",").concat(jsonobject.getString("level")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                            for (int i = 0; i < 3; i++) {
                                LinearLayout row = new LinearLayout(AddProfileActivity.this);
                                row.setOrientation(LinearLayout.VERTICAL);
                                row.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                TextView skills_header = new TextView(AddProfileActivity.this);
                                skills_header.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                skills_header.setText(" " + "Skills");
                                params.setMargins(20,30,0,0);
                                skills_header.setLayoutParams(params);
                                skills_header.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.result_font));
                                skills_header.setTextColor(BLUE);
                                row.addView(skills_header);
                                for (int j = 0; j < skill_name.size(); j++) {
                                    String ab1 = skill_name.get(j);
                                    TextView book_text = new TextView(AddProfileActivity.this);
                                    book_text.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                    book_text.setText("\u25CF" + " " + ab1.trim() + " ");
                                    book_text.setTextColor(BLACK);
                                    row.addView(book_text);
                                }
                                skills_card.addView(row);
                            }
                        }
                        if (routine != null && !routine.isEmpty()) {
                            routine_card.setVisibility(View.VISIBLE);
                            routine_date = new ArrayList<String>();
                            try {
                                JSONArray jsonarray = new JSONArray(routine);
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    String codee = jsonobject.getString("activity");
                                    routine_date.add(codee.concat(",").concat(jsonobject.getString("time")));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            for (int i = 0; i < 3; i++) {
                                LinearLayout row = new LinearLayout(AddProfileActivity.this);
                                row.setOrientation(LinearLayout.VERTICAL);
                                row.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                TextView routine_header = new TextView(AddProfileActivity.this);
                                routine_header.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                routine_header.setText(" " + "Routine");
                                params.setMargins(20,30,0,0);
                                routine_header.setLayoutParams(params);
                                routine_header.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.result_font));
                                routine_header.setTextColor(BLUE);
                                row.addView(routine_header);
                                for (int j = 0; j < routine_date.size(); j++) {
                                    String ab1 = routine_date.get(j);
                                    TextView book_text = new TextView(AddProfileActivity.this);
                                    book_text.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                    book_text.setText("\u25CF" + " " + ab1.trim() + " ");
                                    book_text.setTextColor(BLACK);
                                    row.addView(book_text);
                                }
                                routine_card.addView(row);
                            }
                        }
                        if (languages != null && !languages.isEmpty()) {
                            language_card.setVisibility(View.VISIBLE);
                            lan = new ArrayList<String>();
                            try {
                                JSONArray jsonarray = new JSONArray(languages);
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    String name = jsonobject.getString("name");
                                    lan.add(name);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            for (int i = 0; i < 3; i++) {
                                LinearLayout row = new LinearLayout(AddProfileActivity.this);
                                row.setOrientation(LinearLayout.VERTICAL);
                                row.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                TextView languages_header = new TextView(AddProfileActivity.this);
                                languages_header.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                languages_header.setText(" " + "Language");
                                languages_header.setTextColor(BLUE);
                                row.addView(languages_header);
                                for (int j = 0; j < lan.size(); j++) {
                                    String ab1 = lan.get(j);
                                    TextView book_text = new TextView(AddProfileActivity.this);
                                    book_text.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                    book_text.setText("\u25CF" + " " + ab1.trim() + " ");
                                    book_text.setTextColor(BLACK);
                                    row.addView(book_text);
                                }
                                language_card.addView(row);
                            }
                        }
                    }
                });
    }


    private void setuptoolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xffffffff);
        toolbar.setLogo(R.mipmap.arrowlleft);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddProfileActivity.this, ViewActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();

            }
        });
    }
    @OnClick(R.id.profile_fab)
    void addProfile() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(AddProfileActivity.this);
        builderSingle.setTitle("Add Profile Data:-");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                AddProfileActivity.this,
                android.R.layout.simple_expandable_list_item_1);
        arrayAdapter.add("Book");
        arrayAdapter.add("Skills");
        arrayAdapter.add("Strength");
        arrayAdapter.add("Languages");
        arrayAdapter.add("Passion");
        arrayAdapter.add("Routine");
        builderSingle.setNegativeButton(
                "cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                final LayoutInflater book = LayoutInflater.from(AddProfileActivity.this);
                                View book_view = book.inflate(R.layout.bookdialog, null);
                                AlertDialog.Builder book_builder = new AlertDialog.Builder(AddProfileActivity.this);
                                book_builder.setView(book_view);
                                final ImageView add_book_row = (ImageView) book_view.findViewById(R.id.add_book_view);
                                final EditText book_name = (EditText) book_view.findViewById(R.id.book_name);
                                final LinearLayout dynamic_book_lay = (LinearLayout) book_view.findViewById(R.id.container);
                                if(books != null && !books.isEmpty()){
                                    String ab = book_arry[0];
                                    book_name.setText(ab);
                                    for (int j = 1; j < book_arry.length; j++) {
                                        String bc=book_arry[j];
                                        LayoutInflater book_row = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                            final View add_bk_View = book_row.inflate(R.layout.row, null);
                                            ImageView buttonRemove = (ImageView) add_bk_View.findViewById(R.id.remove_book);
                                            EditText editText = (EditText)add_bk_View.findViewById(R.id.textout);
                                            final View.OnClickListener thisListener = new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    ((LinearLayout) add_bk_View.getParent()).removeView(add_bk_View);
                                                }
                                            };
                                            buttonRemove.setOnClickListener(thisListener);
                                            dynamic_book_lay.addView(add_bk_View);
                                            editText.setText(bc);

                                    }
                                }
                                add_book_row.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        LayoutInflater book_row = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View add_bk_View = book_row.inflate(R.layout.row, null);
                                        ImageView buttonRemove = (ImageView) add_bk_View.findViewById(R.id.remove_book);
                                        final View.OnClickListener thisListener = new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                ((LinearLayout) add_bk_View.getParent()).removeView(add_bk_View);
                                            }
                                        };
                                        buttonRemove.setOnClickListener(thisListener);
                                        dynamic_book_lay.addView(add_bk_View);
                                    }
                                });
                                book_builder
                                        .setCancelable(false)
                                        .setPositiveButton("OK",
                                                new DialogInterface.OnClickListener() {
                                                    @SuppressLint("TimberArgCount")
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        List<String> book_list = new ArrayList<String>();
                                                        if(book_name.getText().toString().length()==0){
                                                        }else{
                                                            book_list.add(0, book_name.getText().toString());
                                                        }
                                                        for (int i = 0; i < dynamic_book_lay.getChildCount(); i++) {
                                                            EditText editText = (EditText) dynamic_book_lay.getChildAt(i).findViewById(R.id.textout);
                                                            book_list.add(i+1, editText.getText().toString());
                                                        }
                                                        if(book_list.isEmpty()){
                                                            Toast.makeText(getApplicationContext(), "Books can't be empty", Toast.LENGTH_LONG).show();

                                                        } else{
                                                            JSONObject metaa = new JSONObject();
                                                            try {
                                                                metaa.put("books", book_list);
                                                                metaa.put("passion", passions);
                                                                metaa.put("strength", strengths);
                                                                metaa.put("routine",routine);
                                                            }catch (Exception e){
                                                                e.printStackTrace();
                                                            }
                                                            try {
                                                                if (profile_id.isEmpty()) {

                                                                } else {
                                                                    databaseHelper.update_Meta(SaveProfile.builder()
                                                                            .setMeta(metaa.toString())
                                                                            .build(), profile_id);
                                                                    startActivity(new Intent(AddProfileActivity.this,AddProfileActivity.class));
                                                                }
                                                            } catch (Exception e) {
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

                                AlertDialog book_alert = book_builder.create();
                                book_alert.show();
                                break;
                            case 1:
                                LayoutInflater skill = LayoutInflater.from(AddProfileActivity.this);
                                final View skill_view = skill.inflate(R.layout.skill_dialog, null);
                                AlertDialog.Builder skill_builder = new AlertDialog.Builder(AddProfileActivity.this);
                                skill_builder.setView(skill_view);
                                final AutoCompleteTextView ski_title = (AutoCompleteTextView) skill_view.findViewById(R.id.ski_title);
                                ski_title.setAdapter(new SkillSuggessionAdapter(AddProfileActivity.this,ski_title.getText().toString()));
                                final EditText ski_description = (EditText) skill_view.findViewById(R.id.ski_description);
                                final LinearLayout skill_layout = (LinearLayout) skill_view.findViewById(R.id.skill_container);
                                final ImageView add_skill_row = (ImageView) skill_view.findViewById(R.id.add_skill_view);
                                if (skills != null && !skills.isEmpty()) {
                                    skill_name_two = new ArrayList<String>();
                                    try {
                                        JSONArray jsonarray = new JSONArray(skills);
                                        for (int i = 0; i < jsonarray.length(); i++) {
                                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                                            String codee = jsonobject.getString("code");
                                            String level=jsonobject.getString("level");
                                            ski_title.setText(codee);
                                            ski_description.setText(level);
                                            i=jsonarray.length();
                                        }
                                        for (int i = 1; i < jsonarray.length(); i++) {
                                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                                            String codee = jsonobject.getString("code");
                                            String level=jsonobject.getString("level");
                                            skill_name_two.add(codee);
                                            skill_name_two.add(level);
                                            LayoutInflater skill_row = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                            final View add_skill_view = skill_row.inflate(R.layout.skill_dynamic_row, null);
                                            ImageView remove_skill = (ImageView) add_skill_view.findViewById(R.id.remove_skill);
                                            AutoCompleteTextView title = (AutoCompleteTextView) add_skill_view.findViewById(R.id.skill_name);
                                            EditText description = (EditText)add_skill_view.findViewById(R.id.skill_level);
                                            title.setAdapter(new SkillSuggessionAdapter(AddProfileActivity.this,title.getText().toString()));
                                            title.setText(codee);
                                            description.setText(level);
                                            remove_skill.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    ((LinearLayout) add_skill_view.getParent()).removeView(add_skill_view);
                                                }
                                            });
                                            skill_layout.addView(add_skill_view);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                                add_skill_row.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        LayoutInflater skill_row = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View add_skill_view = skill_row.inflate(R.layout.skill_dynamic_row, null);
                                        ImageView remove_skill = (ImageView) add_skill_view.findViewById(R.id.remove_skill);
                                        AutoCompleteTextView title = (AutoCompleteTextView) add_skill_view.findViewById(R.id.skill_name);
                                        title.setAdapter(new SkillSuggessionAdapter(AddProfileActivity.this,title.getText().toString()));
                                        remove_skill.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                ((LinearLayout) add_skill_view.getParent()).removeView(add_skill_view);
                                            }
                                        });
                                        skill_layout.addView(add_skill_view);
                                    }
                                });
                                skill_builder
                                        .setCancelable(false)
                                        .setPositiveButton("OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        JSONObject skill_bit;
                                                        JSONArray final_hash = new JSONArray();
                                                        JSONObject skill_bit_main = new JSONObject();
                                                        try {
                                                            skill_bit_main.put("code", ski_title.getText().toString());
                                                            skill_bit_main.put("level", ski_description.getText().toString());
                                                            skill_bit_main.put("type", "declared");
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                        final_hash.put(skill_bit_main);
                                                        for (int i = 0; i < skill_layout.getChildCount(); i++) {
                                                            try {
                                                                skill_bit = new JSONObject();
                                                                AutoCompleteTextView title = (AutoCompleteTextView) skill_layout.getChildAt(i).findViewById(R.id.skill_name);
                                                                EditText description = (EditText) skill_layout.getChildAt(i).findViewById(R.id.skill_level);
                                                                skill_bit.put("code", title.getText().toString());
                                                                skill_bit.put("level", description.getText().toString());
                                                                skill_bit.put("type", "declared");
                                                                final_hash.put(skill_bit);
                                                            }catch (Exception e){
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                        try {
                                                            if (profile_id.isEmpty()) {
                                                                System.out.println("do nothing");
                                                            } else {
                                                                databaseHelper.update_Skills(SaveProfile.builder()
                                                                        .setskill(final_hash.toString())
                                                                        .build(), profile_id);
                                                                startActivity(new Intent(AddProfileActivity.this,AddProfileActivity.class));
                                                            }
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                })
                                        .setNegativeButton("Cancel",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });

                                AlertDialog skill_alert = skill_builder.create();
                                skill_alert.show();
                                break;
                            case 2:
                                final LayoutInflater strength = LayoutInflater.from(AddProfileActivity.this);
                                View strength_view = strength.inflate(R.layout.strength_dialog, null);
                                AlertDialog.Builder strength_builder = new AlertDialog.Builder(AddProfileActivity.this);
                                strength_builder.setView(strength_view);
                                final LinearLayout str_layout = (LinearLayout) strength_view.findViewById(R.id.strength_container);
                                final EditText strenght_name = (EditText) strength_view.findViewById(R.id.strength);
                                final ImageView add_strength_row = (ImageView) strength_view.findViewById(R.id.add_strength_row);
                                if(strengths != null && !strengths.isEmpty()){
                                    String ab = strength_arry[0];
                                    strenght_name.setText(ab);
                                    for (int j = 1; j < strength_arry.length; j++) {
                                        String bc=strength_arry[j];
                                        LayoutInflater layoutInflater = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View add_st_View = layoutInflater.inflate(R.layout.dynamic_strength_dialog, null);
                                        ImageView buttonRemove = (ImageView) add_st_View.findViewById(R.id.remove_strength);
                                        EditText editText = (EditText)add_st_View.findViewById(R.id.strength_name);
                                        buttonRemove.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Timber.i("checking remove strenght");
                                                ((LinearLayout) add_st_View.getParent()).removeView(add_st_View);
                                            }
                                        });
                                        str_layout.addView(add_st_View);
                                        editText.setText(bc);

                                    }
                                }
                                add_strength_row.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        LayoutInflater layoutInflater = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View add_st_View = layoutInflater.inflate(R.layout.dynamic_strength_dialog, null);
                                        ImageView buttonRemove = (ImageView) add_st_View.findViewById(R.id.remove_strength);
                                        buttonRemove.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Timber.i("checking remove strenght");
                                                ((LinearLayout) add_st_View.getParent()).removeView(add_st_View);
                                            }
                                        });

                                        str_layout.addView(add_st_View);


                                    }
                                });
                                strength_builder
                                        .setCancelable(false)
                                        .setPositiveButton("OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        List<String> strength_list = new ArrayList<String>();
                                                        strength_list.add(0, strenght_name.getText().toString());
                                                        for (int i = 0; i < str_layout.getChildCount(); i++) {
                                                            EditText editText = (EditText) str_layout.getChildAt(i).findViewById(R.id.strength_name);
                                                            strength_list.add(i+1, editText.getText().toString());
                                                        }
                                                        JSONObject metaa = new JSONObject();
                                                        try {
                                                            metaa.put("books", books);
                                                            metaa.put("passion", passions);
                                                            metaa.put("strength", strength_list);
                                                            metaa.put("routine",routine);
                                                        }catch (Exception e){
                                                            e.printStackTrace();
                                                        }
                                                        try {
                                                            if (profile_id.isEmpty()) {
                                                                System.out.println("do nothing");
                                                            } else {
                                                                databaseHelper.update_Meta(SaveProfile.builder()
                                                                        .setMeta(metaa.toString())
                                                                        .build(), profile_id);
                                                                startActivity(new Intent(AddProfileActivity.this,AddProfileActivity.class));
                                                            }
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                })
                                        .setNegativeButton("Cancel",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });

                                AlertDialog strength_alert = strength_builder.create();
                                strength_alert.show();
                                break;
                            case 3:
                                LayoutInflater language = LayoutInflater.from(AddProfileActivity.this);
                                View language_view = language.inflate(R.layout.language_dialog, null);
                                AlertDialog.Builder language_builder = new AlertDialog.Builder(AddProfileActivity.this);
                                language_builder.setView(language_view);
                                final LinearLayout lan_layout = (LinearLayout) language_view.findViewById(R.id.language_container);
                                final EditText language_title = (EditText) language_view.findViewById(R.id.lan_title);
                                final MaterialBetterSpinner language_level = (MaterialBetterSpinner) language_view.findViewById(R.id.lan_level);
                                ArrayAdapter<String> lan_adapter = new ArrayAdapter<String>(AddProfileActivity.this,
                                        android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.language));
                                language_level.setAdapter(lan_adapter);
                                final ImageView add_language_row = (ImageView) language_view.findViewById(R.id.add_language_row);
                                if (languages != null && !languages.isEmpty()) {
                                    lan_two = new ArrayList<String>();
                                    try {
                                        JSONArray jsonarray = new JSONArray(languages);
                                        for (int i = 0; i < jsonarray.length(); i++) {
                                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                                            String codee = jsonobject.getString("name");
                                            String level=jsonobject.getString("level");
                                            language_title.setText(codee);
                                            if(level.trim().equals("1")){
                                                level="Beginner";
                                                if (!level.equals(null)) {
                                                    int spinnerPosition = lan_adapter.getPosition(level);
                                                    try {
                                                        if (level.trim().length() > 0) {
                                                            language_level.setText(lan_adapter.getItem(spinnerPosition).toString());
                                                        } else {
                                                            System.out.println("do nothing");
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }
                                            if(level.trim().equals("2")){
                                                level="Intermediate";
                                                if (!level.equals(null)) {
                                                    int spinnerPosition = lan_adapter.getPosition(level);
                                                    try {
                                                        if (level.trim().length() > 0) {
                                                            language_level.setText(lan_adapter.getItem(spinnerPosition).toString());
                                                        } else {
                                                            System.out.println("do nothing");
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }
                                            if(level.trim().equals("3")){
                                                level="Advanced";
                                                if (!level.equals(null)) {
                                                    int spinnerPosition = lan_adapter.getPosition(level);
                                                    try {
                                                        if (level.trim().length() > 0) {
                                                            language_level.setText(lan_adapter.getItem(spinnerPosition).toString());
                                                        } else {
                                                            System.out.println("do nothing");
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }
                                            if(level.trim().equals("4")){
                                                level="Proficient";
                                                if (!level.equals(null)) {
                                                    int spinnerPosition = lan_adapter.getPosition(level);
                                                    try {
                                                        if (level.trim().length() > 0) {
                                                            language_level.setText(lan_adapter.getItem(spinnerPosition).toString());
                                                        } else {
                                                            System.out.println("do nothing");
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }
                                            if(level.trim().equals("5")){
                                                level="Native";
                                                if (!level.equals(null)) {
                                                    int spinnerPosition = lan_adapter.getPosition(level);
                                                    try {
                                                        if (level.trim().length() > 0) {
                                                            language_level.setText(lan_adapter.getItem(spinnerPosition).toString());
                                                        } else {
                                                            System.out.println("do nothing");
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }
                                            i=jsonarray.length();
                                        }
                                        for (int i = 1; i < jsonarray.length(); i++) {
                                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                                            String codee = jsonobject.getString("name");
                                            String level=jsonobject.getString("level");
                                            LayoutInflater layoutInflater = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                            final View add_lan_View = layoutInflater.inflate(R.layout.dynamic_language_row, null);
                                            ImageView buttonRemove = (ImageView) add_lan_View.findViewById(R.id.remove_language);
                                            MaterialBetterSpinner dynamic_level = (MaterialBetterSpinner) add_lan_View.findViewById(R.id.language_level);
                                            EditText title = (EditText)add_lan_View.findViewById(R.id.language_name);
                                            dynamic_level.setAdapter(lan_adapter);
                                            if(level.trim().equals("1")){
                                                level="Beginner";
                                                if (!level.equals(null)) {
                                                    int spinnerPosition = lan_adapter.getPosition(level);
                                                    try {
                                                        if (level.trim().length() > 0) {
                                                            dynamic_level.setText(lan_adapter.getItem(spinnerPosition).toString());
                                                        } else {
                                                            System.out.println("do nothing");
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }
                                            if(level.trim().equals("2")){
                                                level="Intermediate";
                                                if (!level.equals(null)) {
                                                    int spinnerPosition = lan_adapter.getPosition(level);
                                                    try {
                                                        if (level.trim().length() > 0) {
                                                            dynamic_level.setText(lan_adapter.getItem(spinnerPosition).toString());
                                                        } else {
                                                            System.out.println("do nothing");
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }
                                            if(level.trim().equals("3")){
                                                level="Advanced";
                                                if (!level.equals(null)) {
                                                    int spinnerPosition = lan_adapter.getPosition(level);
                                                    try {
                                                        if (level.trim().length() > 0) {
                                                            dynamic_level.setText(lan_adapter.getItem(spinnerPosition).toString());
                                                        } else {
                                                            System.out.println("do nothing");
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }
                                            if(level.trim().equals("4")){
                                                level="Proficient";
                                                if (!level.equals(null)) {
                                                    int spinnerPosition = lan_adapter.getPosition(level);
                                                    try {
                                                        if (level.trim().length() > 0) {
                                                            dynamic_level.setText(lan_adapter.getItem(spinnerPosition).toString());
                                                        } else {
                                                            System.out.println("do nothing");
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }
                                            if(level.trim().equals("5")){
                                                level="Native";
                                                if (!level.equals(null)) {
                                                    int spinnerPosition = lan_adapter.getPosition(level);
                                                    try {
                                                        if (level.trim().length() > 0) {
                                                            dynamic_level.setText(lan_adapter.getItem(spinnerPosition).toString());
                                                        } else {
                                                            System.out.println("do nothing");
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }

                                            title.setText(codee);
                                            buttonRemove.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Timber.i("checking remove strenght");
                                                    ((LinearLayout) add_lan_View.getParent()).removeView(add_lan_View);
                                                }
                                            });

                                            lan_layout.addView(add_lan_View);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                                add_language_row.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        LayoutInflater layoutInflater = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View add_lan_View = layoutInflater.inflate(R.layout.dynamic_language_row, null);
                                        ImageView buttonRemove = (ImageView) add_lan_View.findViewById(R.id.remove_language);
                                        MaterialBetterSpinner description = (MaterialBetterSpinner) add_lan_View.findViewById(R.id.language_level);
                                        ArrayAdapter<String> schooladpater = new ArrayAdapter<String>(AddProfileActivity.this,
                                                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.language));
                                        description.setAdapter(schooladpater);
                                        buttonRemove.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Timber.i("checking remove strenght");
                                                ((LinearLayout) add_lan_View.getParent()).removeView(add_lan_View);
                                            }
                                        });
                                        lan_layout.addView(add_lan_View);
                                    }
                                });
                                language_builder
                                        .setCancelable(false)
                                        .setPositiveButton("OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        JSONObject language_bit;
                                                        JSONArray final_hash = new JSONArray();
                                                        JSONObject language_bit_main = new JSONObject();
                                                        try{
                                                            language_bit_main.put("name", language_title.getText().toString());
                                                            if( language_level.getText().toString().trim().equals("Beginner")){
                                                                language_bit_main.put("level", 1);
                                                            }
                                                            if( language_level.getText().toString().trim().equals("Intermediate")){
                                                                language_bit_main.put("level", 2);
                                                            }
                                                            if( language_level.getText().toString().trim().equals("Advanced")){
                                                                language_bit_main.put("level", 3);
                                                            }
                                                            if( language_level.getText().toString().trim().equals("Proficient")){
                                                                language_bit_main.put("level", 4);
                                                            }
                                                            if( language_level.getText().toString().trim().equals("Native")){
                                                                language_bit_main.put("level", 5);
                                                            }


                                                        }catch (Exception e){
                                                            e.printStackTrace();
                                                        }
                                                        System.out.println("language_bit"+language_bit_main);
                                                        final_hash.put(language_bit_main);
                                                        for (int i = 0; i < lan_layout.getChildCount(); i++) {
                                                            language_bit = new JSONObject();
                                                            EditText title = (EditText) lan_layout.getChildAt(i).findViewById(R.id.language_name);
                                                            MaterialBetterSpinner description = (MaterialBetterSpinner) lan_layout.getChildAt(i).findViewById(R.id.language_level);
                                                            try{
                                                                language_bit.put("name", title.getText().toString());
                                                                if( description.getText().toString().trim().equals("Beginner")){
                                                                    language_bit.put("level", 1);
                                                                }
                                                                if( description.getText().toString().trim().equals("Intermediate")){
                                                                    language_bit.put("level", 2);
                                                                }
                                                                if( description.getText().toString().trim().equals("Advanced")){
                                                                    language_bit.put("level", 3);
                                                                }
                                                                if( description.getText().toString().trim().equals("Proficient")){
                                                                    language_bit.put("level", 4);
                                                                }
                                                                if( description.getText().toString().trim().equals("Native")){
                                                                    language_bit.put("level", 5);
                                                                }

                                                            }catch (Exception e){
                                                                e.printStackTrace();
                                                            }
                                                            System.out.println("language_bit"+language_bit);
                                                            final_hash.put(language_bit);
                                                        }
                                                        try {
                                                            if (profile_id.isEmpty()) {
                                                                System.out.println("do nothing");
                                                            } else {
                                                                databaseHelper.update_Languages(SaveProfile.builder()
                                                                        .setLan(final_hash.toString())
                                                                        .build(), profile_id);
                                                            }
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                        startActivity(new Intent(AddProfileActivity.this,AddProfileActivity.class));
                                                    }
                                                })
                                        .setNegativeButton("Cancel",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });

                                AlertDialog language_alert = language_builder.create();
                                language_alert.show();
                                break;
                            case 4:
                                LayoutInflater passion = LayoutInflater.from(AddProfileActivity.this);
                                View promptsView4 = passion.inflate(R.layout.passion_dialog, null);
                                AlertDialog.Builder passion_builder = new AlertDialog.Builder(AddProfileActivity.this);
                                passion_builder.setView(promptsView4);
                                final LinearLayout passion_layout = (LinearLayout) promptsView4.findViewById(R.id.passion_container);
                                final EditText passion_name = (EditText) promptsView4.findViewById(R.id.passion);
                                final ImageView add_passion_row = (ImageView) promptsView4.findViewById(R.id.add_passion_row);
                                if(passions != null && !passions.isEmpty()){
                                    String ab = passion_arry[0];
                                    passion_name.setText(ab);
                                    for (int j = 1; j < passion_arry.length; j++) {
                                        String bc=passion_arry[j];
                                        LayoutInflater layoutInflater = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View add_pa_view = layoutInflater.inflate(R.layout.dynamic_passion_dialog, null);
                                        ImageView buttonRemove = (ImageView) add_pa_view.findViewById(R.id.remove_passion);
                                        EditText editText = (EditText)add_pa_view.findViewById(R.id.passion_name);
                                        buttonRemove.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Timber.i("checking remove passion");
                                                ((LinearLayout) add_pa_view.getParent()).removeView(add_pa_view);
                                            }
                                        });
                                        passion_layout.addView(add_pa_view);
                                        editText.setText(bc);
                                    }
                                }
                                add_passion_row.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        LayoutInflater layoutInflater = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View add_pa_view = layoutInflater.inflate(R.layout.dynamic_passion_dialog, null);
                                        ImageView buttonRemove = (ImageView) add_pa_view.findViewById(R.id.remove_passion);
                                        buttonRemove.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Timber.i("checking remove passion");
                                                ((LinearLayout) add_pa_view.getParent()).removeView(add_pa_view);
                                            }
                                        });

                                        passion_layout.addView(add_pa_view);


                                    }
                                });
                                passion_builder
                                        .setCancelable(false)
                                        .setPositiveButton("OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        List<String> passion_list = new ArrayList<String>(passion_layout.getChildCount());
                                                        passion_list.add(0, passion_name.getText().toString());
                                                        for (int i = 0; i < passion_layout.getChildCount(); i++) {
                                                            EditText passion_name = (EditText) passion_layout.getChildAt(i).findViewById(R.id.passion_name);
                                                            passion_list.add(i+1, passion_name.getText().toString());
                                                        }
                                                        JSONObject metaa = new JSONObject();
                                                        try {
                                                            metaa.put("books", books);
                                                            metaa.put("passion", passion_list);
                                                            metaa.put("strength", strengths);
                                                            metaa.put("routine",routine);
                                                        }catch (Exception e){
                                                            e.printStackTrace();
                                                        }
                                                        try {
                                                            if (profile_id.isEmpty()) {
                                                                System.out.println("do nothing");
                                                            } else {
                                                                databaseHelper.update_Meta(SaveProfile.builder()
                                                                        .setMeta(metaa.toString())
                                                                        .build(), profile_id);
                                                                startActivity(new Intent(AddProfileActivity.this,AddProfileActivity.class));
                                                            }
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                })
                                        .setNegativeButton("Cancel",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });

                                AlertDialog passion_alert = passion_builder.create();
                                passion_alert.show();
                                break;
                            case 5:
                                final LayoutInflater routines = LayoutInflater.from(AddProfileActivity.this);
                                View promptsView5 = routines.inflate(R.layout.routine_dialog, null);
                                AlertDialog.Builder routine_builder = new AlertDialog.Builder(AddProfileActivity.this);
                                routine_builder.setView(promptsView5);
                                final LinearLayout routine_layout = (LinearLayout) promptsView5.findViewById(R.id.routine_container);
                                final EditText routine_name = (EditText) promptsView5.findViewById(R.id.routine);
                                final MaterialBetterSpinner type = (MaterialBetterSpinner) promptsView5.findViewById(R.id.type);
                                ArrayAdapter<String> time_adapter = new ArrayAdapter<String>(AddProfileActivity.this,
                                        android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.time));
                                type.setAdapter(time_adapter);
                                final EditText time = (EditText) promptsView5.findViewById(R.id.time);
                                final ImageView add_routine_row = (ImageView) promptsView5.findViewById(R.id.add_routine_row);
                                if (routine != null && !routine.isEmpty()) {
                                    routine_data_two = new ArrayList<String>();
                                    try {
                                        JSONArray jsonarray = new JSONArray(routine);
                                        for (int i = 0; i < jsonarray.length(); i++) {
                                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                                            String activity = jsonobject.getString("activity");
                                            routine_name.setText(activity);
                                            String timee=jsonobject.getString("time");
                                            String time_arry[] = timee.split(" ");
                                            String t=time_arry[0];
                                            String ty=time_arry[1];
                                            if (!ty.equals(null)) {
                                                int spinnerPosition = time_adapter.getPosition(ty);
                                                try {
                                                    if (ty.trim().length() > 0) {
                                                        type.setText(time_adapter.getItem(spinnerPosition).toString());
                                                    } else {
                                                        System.out.println("do nothing");
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                            time.setText(t);
                                            i=jsonarray.length();
                                        }
                                        for(int i=1; i<jsonarray.length();i++){
                                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                                            String activity = jsonobject.getString("activity");
                                            routine_name.setText(activity);
                                            String timee=jsonobject.getString("time");
                                            String time_arry[] = timee.split(" ");
                                            String t=time_arry[0];
                                            String ty=time_arry[1];
                                            LayoutInflater layoutInflater = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                            final View add_routine_view = layoutInflater.inflate(R.layout.dynamic_routine_dialog, null);
                                            ImageView buttonRemove = (ImageView) add_routine_view.findViewById(R.id.remove_routine);
                                            EditText activity_in_loop = (EditText) add_routine_view.findViewById(R.id.routine_name);
                                            MaterialBetterSpinner type_in_loop = (MaterialBetterSpinner) add_routine_view.findViewById(R.id.type);
                                            EditText timee_in_loop = (EditText) add_routine_view.findViewById(R.id.time);
                                            ArrayAdapter<String> time_adapterr = new ArrayAdapter<String>(AddProfileActivity.this,
                                                    android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.time));
                                            type_in_loop.setAdapter(time_adapterr);
                                            if (!ty.equals(null)) {
                                                int spinnerPosition = time_adapterr.getPosition(ty);
                                                try {
                                                    if (ty.trim().length() > 0) {
                                                        type_in_loop.setText(time_adapterr.getItem(spinnerPosition).toString());
                                                    } else {
                                                        System.out.println("do nothing");
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            activity_in_loop.setText(activity);
                                            timee_in_loop.setText(t);
                                            buttonRemove.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Timber.i("checking remove passion");
                                                    ((LinearLayout) add_routine_view.getParent()).removeView(add_routine_view);
                                                }
                                            });
                                            routine_layout.addView(add_routine_view);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                                add_routine_row.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        LayoutInflater layoutInflater = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View add_routine_view = layoutInflater.inflate(R.layout.dynamic_routine_dialog, null);
                                        ImageView buttonRemove = (ImageView) add_routine_view.findViewById(R.id.remove_routine);
                                        final MaterialBetterSpinner type = (MaterialBetterSpinner) add_routine_view.findViewById(R.id.type);
                                        ArrayAdapter<String> time_adapter = new ArrayAdapter<String>(AddProfileActivity.this,
                                                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.time));
                                        type.setAdapter(time_adapter);
                                        buttonRemove.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Timber.i("checking remove passion");
                                                ((LinearLayout) add_routine_view.getParent()).removeView(add_routine_view);
                                            }
                                        });
                                        routine_layout.addView(add_routine_view);
                                    }
                                });
                                routine_builder
                                        .setCancelable(false)
                                        .setPositiveButton("OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        JSONObject passion_bit;
                                                        JSONArray final_routine_bit = new JSONArray();
                                                        JSONObject passion_bit_main = new JSONObject();
                                                        String hourr=time.getText().toString().concat(" ").concat(type.getText().toString());
                                                        try{
                                                            passion_bit_main.put("activity", routine_name.getText().toString());
                                                            passion_bit_main.put("time", hourr);
                                                        }catch (Exception e){
                                                            e.printStackTrace();
                                                        }
                                                        final_routine_bit.put(passion_bit_main);
                                                        for (int i = 0; i < routine_layout.getChildCount(); i++) {
                                                            passion_bit = new JSONObject();
                                                            EditText activity = (EditText) routine_layout.getChildAt(i).findViewById(R.id.routine_name);
                                                            MaterialBetterSpinner type = (MaterialBetterSpinner) routine_layout.getChildAt(i).findViewById(R.id.type);
                                                            EditText timee = (EditText) routine_layout.getChildAt(i).findViewById(R.id.time);
                                                            String time = timee.getText().toString().concat(" ").concat(type.getText().toString());

                                                            try {
                                                                passion_bit.put("activity", activity.getText().toString());
                                                                passion_bit.put("time", time);
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                            final_routine_bit.put(passion_bit);
                                                        }
                                                        JSONObject metaa = new JSONObject();
                                                        try {
                                                            metaa.put("books", books);
                                                            metaa.put("passion", passions);
                                                            metaa.put("strength", strengths);
                                                            metaa.put("routine",final_routine_bit);
                                                        }catch (Exception e){
                                                            e.printStackTrace();
                                                        }
                                                        try {
                                                                if (profile_id.isEmpty()) {
                                                                    System.out.println("do nothing");
                                                                } else {
                                                                    databaseHelper.update_Meta(SaveProfile.builder()
                                                                            .setMeta(metaa.toString().trim())
                                                                            .build(), profile_id);
                                                                    startActivity(new Intent(AddProfileActivity.this,AddProfileActivity.class));
                                                                }
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                    }
                                                })
                                        .setNegativeButton("Cancel",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });
                                AlertDialog routine_alert = routine_builder.create();
                                routine_alert.show();
                                break;
                            default:
                                break;

                        }


                    }
                });
        builderSingle.show();


    }
    public void edit_book(View view){
        final LayoutInflater book = LayoutInflater.from(AddProfileActivity.this);
        View book_view = book.inflate(R.layout.bookdialog, null);
        AlertDialog.Builder book_builder = new AlertDialog.Builder(AddProfileActivity.this);
        book_builder.setView(book_view);
        final ImageView add_book_row = (ImageView) book_view.findViewById(R.id.add_book_view);
        final EditText book_name = (EditText) book_view.findViewById(R.id.book_name);
        final LinearLayout dynamic_book_lay = (LinearLayout) book_view.findViewById(R.id.container);
        if(books != null && !books.isEmpty()){
            String ab = book_arry[0];
            book_name.setText(ab);
            for (int j = 1; j < book_arry.length; j++) {
                String bc=book_arry[j];
                LayoutInflater book_row = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View add_bk_View = book_row.inflate(R.layout.row, null);
                ImageView buttonRemove = (ImageView) add_bk_View.findViewById(R.id.remove_book);
                EditText editText = (EditText)add_bk_View.findViewById(R.id.textout);
                final View.OnClickListener thisListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((LinearLayout) add_bk_View.getParent()).removeView(add_bk_View);
                    }
                };
                buttonRemove.setOnClickListener(thisListener);
                dynamic_book_lay.addView(add_bk_View);
                editText.setText(bc);

            }
        }
        add_book_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater book_row = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View add_bk_View = book_row.inflate(R.layout.row, null);
                ImageView buttonRemove = (ImageView) add_bk_View.findViewById(R.id.remove_book);
                final View.OnClickListener thisListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((LinearLayout) add_bk_View.getParent()).removeView(add_bk_View);
                    }
                };
                buttonRemove.setOnClickListener(thisListener);
                dynamic_book_lay.addView(add_bk_View);
            }
        });
        book_builder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @SuppressLint("TimberArgCount")
                            public void onClick(DialogInterface dialog, int id) {
                                List<String> book_list = new ArrayList<String>();
                                if(book_name.getText().toString().length()==0){
                                }else{
                                    book_list.add(0, book_name.getText().toString());
                                }
                                for (int i = 0; i < dynamic_book_lay.getChildCount(); i++) {
                                    EditText editText = (EditText) dynamic_book_lay.getChildAt(i).findViewById(R.id.textout);
                                    book_list.add(i+1, editText.getText().toString());
                                }
                                if(book_list.isEmpty()){
                                    System.out.println("if");
                                    Toast.makeText(getApplicationContext(), "Books can't be empty", Toast.LENGTH_LONG).show();

                                } else{
                                    JSONObject metaa = new JSONObject();
                                    try {
                                        metaa.put("books", book_list);
                                        metaa.put("passion", passions);
                                        metaa.put("strength", strengths);
                                        metaa.put("routine",routine);
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    try {
                                        if (profile_id.isEmpty()) {

                                        } else {
                                            databaseHelper.update_Meta(SaveProfile.builder()
                                                    .setMeta(metaa.toString())
                                                    .build(), profile_id);
                                            startActivity(new Intent(AddProfileActivity.this,AddProfileActivity.class));
                                        }
                                    } catch (Exception e) {
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

        AlertDialog book_alert = book_builder.create();
        book_alert.show();
    }
    public void edit_passion(View view){
        LayoutInflater passion = LayoutInflater.from(AddProfileActivity.this);
        View promptsView4 = passion.inflate(R.layout.passion_dialog, null);
        AlertDialog.Builder passion_builder = new AlertDialog.Builder(AddProfileActivity.this);
        passion_builder.setView(promptsView4);
        final LinearLayout passion_layout = (LinearLayout) promptsView4.findViewById(R.id.passion_container);
        final EditText passion_name = (EditText) promptsView4.findViewById(R.id.passion);
        final ImageView add_passion_row = (ImageView) promptsView4.findViewById(R.id.add_passion_row);
        if(passions != null && !passions.isEmpty()){
            String ab = passion_arry[0];
            passion_name.setText(ab);
            for (int j = 1; j < passion_arry.length; j++) {
                String bc=passion_arry[j];
                LayoutInflater layoutInflater = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View add_pa_view = layoutInflater.inflate(R.layout.dynamic_passion_dialog, null);
                ImageView buttonRemove = (ImageView) add_pa_view.findViewById(R.id.remove_passion);
                EditText editText = (EditText)add_pa_view.findViewById(R.id.passion_name);
                buttonRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Timber.i("checking remove passion");
                        ((LinearLayout) add_pa_view.getParent()).removeView(add_pa_view);
                    }
                });
                passion_layout.addView(add_pa_view);
                editText.setText(bc);
            }
        }
        add_passion_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View add_pa_view = layoutInflater.inflate(R.layout.dynamic_passion_dialog, null);
                ImageView buttonRemove = (ImageView) add_pa_view.findViewById(R.id.remove_passion);
                buttonRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Timber.i("checking remove passion");
                        ((LinearLayout) add_pa_view.getParent()).removeView(add_pa_view);
                    }
                });

                passion_layout.addView(add_pa_view);


            }
        });
        passion_builder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                List<String> passion_list = new ArrayList<String>(passion_layout.getChildCount());
                                passion_list.add(0, passion_name.getText().toString());
                                for (int i = 0; i < passion_layout.getChildCount(); i++) {
                                    EditText passion_name = (EditText) passion_layout.getChildAt(i).findViewById(R.id.passion_name);
                                    passion_list.add(i+1, passion_name.getText().toString());
                                }
                                JSONObject metaa = new JSONObject();
                                try {
                                    metaa.put("books", books);
                                    metaa.put("passion", passion_list);
                                    metaa.put("strength", strengths);
                                    metaa.put("routine",routine);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                try {
                                    if (profile_id.isEmpty()) {
                                        System.out.println("do nothing");
                                    } else {
                                        databaseHelper.update_Meta(SaveProfile.builder()
                                                .setMeta(metaa.toString())
                                                .build(), profile_id);
                                        startActivity(new Intent(AddProfileActivity.this,AddProfileActivity.class));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog passion_alert = passion_builder.create();
        passion_alert.show();
    }
    public void edit_skill(View view){
        LayoutInflater skill = LayoutInflater.from(AddProfileActivity.this);
        final View skill_view = skill.inflate(R.layout.skill_dialog, null);
        AlertDialog.Builder skill_builder = new AlertDialog.Builder(AddProfileActivity.this);
        skill_builder.setView(skill_view);
        final AutoCompleteTextView ski_title = (AutoCompleteTextView) skill_view.findViewById(R.id.ski_title);
        ski_title.setAdapter(new SkillSuggessionAdapter(AddProfileActivity.this,ski_title.getText().toString()));
        final EditText ski_description = (EditText) skill_view.findViewById(R.id.ski_description);
        final LinearLayout skill_layout = (LinearLayout) skill_view.findViewById(R.id.skill_container);
        final ImageView add_skill_row = (ImageView) skill_view.findViewById(R.id.add_skill_view);
        if (skills != null && !skills.isEmpty()) {
            skill_name_two = new ArrayList<String>();
            try {
                JSONArray jsonarray = new JSONArray(skills);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    String codee = jsonobject.getString("code");
                    String level=jsonobject.getString("level");
                    ski_title.setText(codee);
                    ski_description.setText(level);
                    i=jsonarray.length();
                }
                for (int i = 1; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    String codee = jsonobject.getString("code");
                    String level=jsonobject.getString("level");
                    skill_name_two.add(codee);
                    skill_name_two.add(level);
                    LayoutInflater skill_row = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View add_skill_view = skill_row.inflate(R.layout.skill_dynamic_row, null);
                    ImageView remove_skill = (ImageView) add_skill_view.findViewById(R.id.remove_skill);
                    AutoCompleteTextView title = (AutoCompleteTextView) add_skill_view.findViewById(R.id.skill_name);
                    EditText description = (EditText)add_skill_view.findViewById(R.id.skill_level);
                    title.setAdapter(new SkillSuggessionAdapter(AddProfileActivity.this,title.getText().toString()));
                    title.setText(codee);
                    description.setText(level);
                    remove_skill.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            ((LinearLayout) add_skill_view.getParent()).removeView(add_skill_view);
                        }
                    });
                    skill_layout.addView(add_skill_view);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        add_skill_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater skill_row = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View add_skill_view = skill_row.inflate(R.layout.skill_dynamic_row, null);
                ImageView remove_skill = (ImageView) add_skill_view.findViewById(R.id.remove_skill);
                AutoCompleteTextView title = (AutoCompleteTextView) add_skill_view.findViewById(R.id.skill_name);
                title.setAdapter(new SkillSuggessionAdapter(AddProfileActivity.this,title.getText().toString()));
                remove_skill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ((LinearLayout) add_skill_view.getParent()).removeView(add_skill_view);
                    }
                });
                skill_layout.addView(add_skill_view);
            }
        });
        skill_builder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                JSONObject skill_bit;
                                JSONArray final_hash = new JSONArray();
                                JSONObject skill_bit_main = new JSONObject();
                                try {
                                    skill_bit_main.put("code", ski_title.getText().toString());
                                    skill_bit_main.put("level", ski_description.getText().toString());
                                    skill_bit_main.put("type", "declared");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                final_hash.put(skill_bit_main);
                                for (int i = 0; i < skill_layout.getChildCount(); i++) {
                                    try {
                                        skill_bit = new JSONObject();
                                        AutoCompleteTextView title = (AutoCompleteTextView) skill_layout.getChildAt(i).findViewById(R.id.skill_name);
                                        EditText description = (EditText) skill_layout.getChildAt(i).findViewById(R.id.skill_level);
                                        skill_bit.put("code", title.getText().toString());
                                        skill_bit.put("level", description.getText().toString());
                                        skill_bit.put("type", "declared");
                                        final_hash.put(skill_bit);
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                                try {
                                    if (profile_id.isEmpty()) {
                                        System.out.println("do nothing");
                                    } else {
                                        databaseHelper.update_Skills(SaveProfile.builder()
                                                .setskill(final_hash.toString())
                                                .build(), profile_id);
                                        startActivity(new Intent(AddProfileActivity.this,AddProfileActivity.class));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog skill_alert = skill_builder.create();
        skill_alert.show();
    }
    public void edit_strength(View view){
        final LayoutInflater strength = LayoutInflater.from(AddProfileActivity.this);
        View strength_view = strength.inflate(R.layout.strength_dialog, null);
        AlertDialog.Builder strength_builder = new AlertDialog.Builder(AddProfileActivity.this);
        strength_builder.setView(strength_view);
        final LinearLayout str_layout = (LinearLayout) strength_view.findViewById(R.id.strength_container);
        final EditText strenght_name = (EditText) strength_view.findViewById(R.id.strength);
        final ImageView add_strength_row = (ImageView) strength_view.findViewById(R.id.add_strength_row);
        if(strengths != null && !strengths.isEmpty()){
            String ab = strength_arry[0];
            strenght_name.setText(ab);
            for (int j = 1; j < strength_arry.length; j++) {
                String bc=strength_arry[j];
                LayoutInflater layoutInflater = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View add_st_View = layoutInflater.inflate(R.layout.dynamic_strength_dialog, null);
                ImageView buttonRemove = (ImageView) add_st_View.findViewById(R.id.remove_strength);
                EditText editText = (EditText)add_st_View.findViewById(R.id.strength_name);
                buttonRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Timber.i("checking remove strenght");
                        ((LinearLayout) add_st_View.getParent()).removeView(add_st_View);
                    }
                });
                str_layout.addView(add_st_View);
                editText.setText(bc);

            }
        }
        add_strength_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View add_st_View = layoutInflater.inflate(R.layout.dynamic_strength_dialog, null);
                ImageView buttonRemove = (ImageView) add_st_View.findViewById(R.id.remove_strength);
                buttonRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Timber.i("checking remove strenght");
                        ((LinearLayout) add_st_View.getParent()).removeView(add_st_View);
                    }
                });

                str_layout.addView(add_st_View);


            }
        });
        strength_builder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                List<String> strength_list = new ArrayList<String>();
                                strength_list.add(0, strenght_name.getText().toString());
                                for (int i = 0; i < str_layout.getChildCount(); i++) {
                                    EditText editText = (EditText) str_layout.getChildAt(i).findViewById(R.id.strength_name);
                                    strength_list.add(i+1, editText.getText().toString());
                                }
                                JSONObject metaa = new JSONObject();
                                try {
                                    metaa.put("books", books);
                                    metaa.put("passion", passions);
                                    metaa.put("strength", strength_list);
                                    metaa.put("routine",routine);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                try {
                                    if (profile_id.isEmpty()) {
                                        System.out.println("do nothing");
                                    } else {
                                        databaseHelper.update_Meta(SaveProfile.builder()
                                                .setMeta(metaa.toString())
                                                .build(), profile_id);
                                        startActivity(new Intent(AddProfileActivity.this,AddProfileActivity.class));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog strength_alert = strength_builder.create();
        strength_alert.show();
    }
    public void edit_language(View view){
        LayoutInflater language = LayoutInflater.from(AddProfileActivity.this);
        View language_view = language.inflate(R.layout.language_dialog, null);
        AlertDialog.Builder language_builder = new AlertDialog.Builder(AddProfileActivity.this);
        language_builder.setView(language_view);
        final LinearLayout lan_layout = (LinearLayout) language_view.findViewById(R.id.language_container);
        final EditText language_title = (EditText) language_view.findViewById(R.id.lan_title);
        final MaterialBetterSpinner language_level = (MaterialBetterSpinner) language_view.findViewById(R.id.lan_level);
        ArrayAdapter<String> lan_adapter = new ArrayAdapter<String>(AddProfileActivity.this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.language));
        language_level.setAdapter(lan_adapter);
        final ImageView add_language_row = (ImageView) language_view.findViewById(R.id.add_language_row);
        if (languages != null && !languages.isEmpty()) {
            lan_two = new ArrayList<String>();
            try {
                JSONArray jsonarray = new JSONArray(languages);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    String codee = jsonobject.getString("name");
                    String level=jsonobject.getString("level");
                    language_title.setText(codee);
                    if(level.trim().equals("1")){
                        level="Beginner";
                        if (!level.equals(null)) {
                            int spinnerPosition = lan_adapter.getPosition(level);
                            try {
                                if (level.trim().length() > 0) {
                                    language_level.setText(lan_adapter.getItem(spinnerPosition).toString());
                                } else {
                                    System.out.println("do nothing");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    if(level.trim().equals("2")){
                        level="Intermediate";
                        if (!level.equals(null)) {
                            int spinnerPosition = lan_adapter.getPosition(level);
                            try {
                                if (level.trim().length() > 0) {
                                    language_level.setText(lan_adapter.getItem(spinnerPosition).toString());
                                } else {
                                    System.out.println("do nothing");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    if(level.trim().equals("3")){
                        level="Advanced";
                        if (!level.equals(null)) {
                            int spinnerPosition = lan_adapter.getPosition(level);
                            try {
                                if (level.trim().length() > 0) {
                                    language_level.setText(lan_adapter.getItem(spinnerPosition).toString());
                                } else {
                                    System.out.println("do nothing");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    if(level.trim().equals("4")){
                        level="Proficient";
                        if (!level.equals(null)) {
                            int spinnerPosition = lan_adapter.getPosition(level);
                            try {
                                if (level.trim().length() > 0) {
                                    language_level.setText(lan_adapter.getItem(spinnerPosition).toString());
                                } else {
                                    System.out.println("do nothing");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    if(level.trim().equals("5")){
                        level="Native";
                        if (!level.equals(null)) {
                            int spinnerPosition = lan_adapter.getPosition(level);
                            try {
                                if (level.trim().length() > 0) {
                                    language_level.setText(lan_adapter.getItem(spinnerPosition).toString());
                                } else {
                                    System.out.println("do nothing");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    i=jsonarray.length();
                }
                for (int i = 1; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    String codee = jsonobject.getString("name");
                    String level=jsonobject.getString("level");
                    LayoutInflater layoutInflater = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View add_lan_View = layoutInflater.inflate(R.layout.dynamic_language_row, null);
                    ImageView buttonRemove = (ImageView) add_lan_View.findViewById(R.id.remove_language);
                    MaterialBetterSpinner dynamic_level = (MaterialBetterSpinner) add_lan_View.findViewById(R.id.language_level);
                    EditText title = (EditText)add_lan_View.findViewById(R.id.language_name);
                    dynamic_level.setAdapter(lan_adapter);
                    if(level.trim().equals("1")){
                        level="Beginner";
                        if (!level.equals(null)) {
                            int spinnerPosition = lan_adapter.getPosition(level);
                            try {
                                if (level.trim().length() > 0) {
                                    dynamic_level.setText(lan_adapter.getItem(spinnerPosition).toString());
                                } else {
                                    System.out.println("do nothing");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    if(level.trim().equals("2")){
                        level="Intermediate";
                        if (!level.equals(null)) {
                            int spinnerPosition = lan_adapter.getPosition(level);
                            try {
                                if (level.trim().length() > 0) {
                                    dynamic_level.setText(lan_adapter.getItem(spinnerPosition).toString());
                                } else {
                                    System.out.println("do nothing");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    if(level.trim().equals("3")){
                        level="Advanced";
                        if (!level.equals(null)) {
                            int spinnerPosition = lan_adapter.getPosition(level);
                            try {
                                if (level.trim().length() > 0) {
                                    dynamic_level.setText(lan_adapter.getItem(spinnerPosition).toString());
                                } else {
                                    System.out.println("do nothing");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    if(level.trim().equals("4")){
                        level="Proficient";
                        if (!level.equals(null)) {
                            int spinnerPosition = lan_adapter.getPosition(level);
                            try {
                                if (level.trim().length() > 0) {
                                    dynamic_level.setText(lan_adapter.getItem(spinnerPosition).toString());
                                } else {
                                    System.out.println("do nothing");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    if(level.trim().equals("5")){
                        level="Native";
                        if (!level.equals(null)) {
                            int spinnerPosition = lan_adapter.getPosition(level);
                            try {
                                if (level.trim().length() > 0) {
                                    dynamic_level.setText(lan_adapter.getItem(spinnerPosition).toString());
                                } else {
                                    System.out.println("do nothing");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    title.setText(codee);
                    buttonRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Timber.i("checking remove strenght");
                            ((LinearLayout) add_lan_View.getParent()).removeView(add_lan_View);
                        }
                    });

                    lan_layout.addView(add_lan_View);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        add_language_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View add_lan_View = layoutInflater.inflate(R.layout.dynamic_language_row, null);
                ImageView buttonRemove = (ImageView) add_lan_View.findViewById(R.id.remove_language);
                MaterialBetterSpinner description = (MaterialBetterSpinner) add_lan_View.findViewById(R.id.language_level);
                ArrayAdapter<String> schooladpater = new ArrayAdapter<String>(AddProfileActivity.this,
                        android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.language));
                description.setAdapter(schooladpater);
                buttonRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Timber.i("checking remove strenght");
                        ((LinearLayout) add_lan_View.getParent()).removeView(add_lan_View);
                    }
                });
                lan_layout.addView(add_lan_View);
            }
        });
        language_builder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                JSONObject language_bit;
                                JSONArray final_hash = new JSONArray();
                                JSONObject language_bit_main = new JSONObject();
                                try{
                                    language_bit_main.put("name", language_title.getText().toString());
                                    if( language_level.getText().toString().trim().equals("Beginner")){
                                        language_bit_main.put("level", 1);
                                    }
                                    if( language_level.getText().toString().trim().equals("Intermediate")){
                                        language_bit_main.put("level", 2);
                                    }
                                    if( language_level.getText().toString().trim().equals("Advanced")){
                                        language_bit_main.put("level", 3);
                                    }
                                    if( language_level.getText().toString().trim().equals("Proficient")){
                                        language_bit_main.put("level", 4);
                                    }
                                    if( language_level.getText().toString().trim().equals("Native")){
                                        language_bit_main.put("level", 5);
                                    }


                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                System.out.println("language_bit"+language_bit_main);
                                final_hash.put(language_bit_main);
                                for (int i = 0; i < lan_layout.getChildCount(); i++) {
                                    language_bit = new JSONObject();
                                    EditText title = (EditText) lan_layout.getChildAt(i).findViewById(R.id.language_name);
                                    MaterialBetterSpinner description = (MaterialBetterSpinner) lan_layout.getChildAt(i).findViewById(R.id.language_level);
                                    try{
                                        language_bit.put("name", title.getText().toString());
                                        if( description.getText().toString().trim().equals("Beginner")){
                                            language_bit.put("level", 1);
                                        }
                                        if( description.getText().toString().trim().equals("Intermediate")){
                                            language_bit.put("level", 2);
                                        }
                                        if( description.getText().toString().trim().equals("Advanced")){
                                            language_bit.put("level", 3);
                                        }
                                        if( description.getText().toString().trim().equals("Proficient")){
                                            language_bit.put("level", 4);
                                        }
                                        if( description.getText().toString().trim().equals("Native")){
                                            language_bit.put("level", 5);
                                        }

                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    System.out.println("language_bit"+language_bit);
                                    final_hash.put(language_bit);
                                }
                                try {
                                    if (profile_id.isEmpty()) {
                                        System.out.println("do nothing");
                                    } else {
                                        databaseHelper.update_Languages(SaveProfile.builder()
                                                .setLan(final_hash.toString())
                                                .build(), profile_id);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                startActivity(new Intent(AddProfileActivity.this,AddProfileActivity.class));
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog language_alert = language_builder.create();
        language_alert.show();
    }
public void edit_routine(View view){
    final LayoutInflater routines = LayoutInflater.from(AddProfileActivity.this);
    View promptsView5 = routines.inflate(R.layout.routine_dialog, null);
    AlertDialog.Builder routine_builder = new AlertDialog.Builder(AddProfileActivity.this);
    routine_builder.setView(promptsView5);
    final LinearLayout routine_layout = (LinearLayout) promptsView5.findViewById(R.id.routine_container);
    final EditText routine_name = (EditText) promptsView5.findViewById(R.id.routine);
    final MaterialBetterSpinner type = (MaterialBetterSpinner) promptsView5.findViewById(R.id.type);
    ArrayAdapter<String> time_adapter = new ArrayAdapter<String>(AddProfileActivity.this,
            android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.time));
    type.setAdapter(time_adapter);
    final EditText time = (EditText) promptsView5.findViewById(R.id.time);
    final ImageView add_routine_row = (ImageView) promptsView5.findViewById(R.id.add_routine_row);
    if (routine != null && !routine.isEmpty()) {
        routine_data_two = new ArrayList<String>();
        try {
            JSONArray jsonarray = new JSONArray(routine);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String activity = jsonobject.getString("activity");
                routine_name.setText(activity);
                String timee=jsonobject.getString("time");
                String time_arry[] = timee.split(" ");
                String t=time_arry[0];
                String ty=time_arry[1];
                if (!ty.equals(null)) {
                    int spinnerPosition = time_adapter.getPosition(ty);
                    try {
                        if (ty.trim().length() > 0) {
                            type.setText(time_adapter.getItem(spinnerPosition).toString());
                        } else {
                            System.out.println("do nothing");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                time.setText(t);
                i=jsonarray.length();
            }
            for(int i=1; i<jsonarray.length();i++){
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String activity = jsonobject.getString("activity");
                routine_name.setText(activity);
                String timee=jsonobject.getString("time");
                String time_arry[] = timee.split(" ");
                String t=time_arry[0];
                String ty=time_arry[1];
                LayoutInflater layoutInflater = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View add_routine_view = layoutInflater.inflate(R.layout.dynamic_routine_dialog, null);
                ImageView buttonRemove = (ImageView) add_routine_view.findViewById(R.id.remove_routine);
                EditText activity_in_loop = (EditText) add_routine_view.findViewById(R.id.routine_name);
                MaterialBetterSpinner type_in_loop = (MaterialBetterSpinner) add_routine_view.findViewById(R.id.type);
                EditText timee_in_loop = (EditText) add_routine_view.findViewById(R.id.time);
                ArrayAdapter<String> time_adapterr = new ArrayAdapter<String>(AddProfileActivity.this,
                        android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.time));
                type_in_loop.setAdapter(time_adapterr);
                if (!ty.equals(null)) {
                    int spinnerPosition = time_adapterr.getPosition(ty);
                    try {
                        if (ty.trim().length() > 0) {
                            type_in_loop.setText(time_adapterr.getItem(spinnerPosition).toString());
                        } else {
                            System.out.println("do nothing");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                activity_in_loop.setText(activity);
                timee_in_loop.setText(t);
                buttonRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Timber.i("checking remove passion");
                        ((LinearLayout) add_routine_view.getParent()).removeView(add_routine_view);
                    }
                });
                routine_layout.addView(add_routine_view);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    add_routine_row.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            LayoutInflater layoutInflater = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View add_routine_view = layoutInflater.inflate(R.layout.dynamic_routine_dialog, null);
            ImageView buttonRemove = (ImageView) add_routine_view.findViewById(R.id.remove_routine);
            final MaterialBetterSpinner type = (MaterialBetterSpinner) add_routine_view.findViewById(R.id.type);
            ArrayAdapter<String> time_adapter = new ArrayAdapter<String>(AddProfileActivity.this,
                    android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.time));
            type.setAdapter(time_adapter);
            buttonRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Timber.i("checking remove passion");
                    ((LinearLayout) add_routine_view.getParent()).removeView(add_routine_view);
                }
            });
            routine_layout.addView(add_routine_view);
        }
    });
    routine_builder
            .setCancelable(false)
            .setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            JSONObject passion_bit;
                            JSONArray final_routine_bit = new JSONArray();
                            JSONObject passion_bit_main = new JSONObject();
                            String hourr=time.getText().toString().concat(" ").concat(type.getText().toString());
                            try{
                                passion_bit_main.put("activity", routine_name.getText().toString());
                                passion_bit_main.put("time", hourr);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            final_routine_bit.put(passion_bit_main);
                            for (int i = 0; i < routine_layout.getChildCount(); i++) {
                                passion_bit = new JSONObject();
                                EditText activity = (EditText) routine_layout.getChildAt(i).findViewById(R.id.routine_name);
                                MaterialBetterSpinner type = (MaterialBetterSpinner) routine_layout.getChildAt(i).findViewById(R.id.type);
                                EditText timee = (EditText) routine_layout.getChildAt(i).findViewById(R.id.time);
                                String time = timee.getText().toString().concat(" ").concat(type.getText().toString());

                                try {
                                    passion_bit.put("activity", activity.getText().toString());
                                    passion_bit.put("time", time);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                final_routine_bit.put(passion_bit);
                            }
                            JSONObject metaa = new JSONObject();
                            try {
                                metaa.put("books", books);
                                metaa.put("passion", passions);
                                metaa.put("strength", strengths);
                                metaa.put("routine",final_routine_bit);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            try {
                                if (profile_id.isEmpty()) {
                                    System.out.println("do nothing");
                                } else {
                                    databaseHelper.update_Meta(SaveProfile.builder()
                                            .setMeta(metaa.toString().trim())
                                            .build(), profile_id);
                                    startActivity(new Intent(AddProfileActivity.this,AddProfileActivity.class));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    })
            .setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
    AlertDialog routine_alert = routine_builder.create();
    routine_alert.show();
}
}
