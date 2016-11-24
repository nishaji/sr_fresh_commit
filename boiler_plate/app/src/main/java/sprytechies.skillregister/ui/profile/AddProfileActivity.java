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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import sprytechies.skillregister.ui.base.BaseActivity;
import sprytechies.skillregister.ui.launcher.activity.ViewActivity;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;


public class AddProfileActivity extends BaseActivity {

    @Inject
    DatabaseHelper databaseHelper;
    private Subscription mSubscription;
    @BindView(R.id.profile_fab) FloatingActionButton fab;
    @BindView(R.id.add_profile_tool) Toolbar toolbar;
    @BindView(R.id.book_card) CardView book_card;
    @BindView(R.id.skills_card) CardView skills_card;
    @BindView(R.id.passion_card) CardView passion_card;
    @BindView(R.id.strength_card) CardView strength_card;
    @BindView(R.id.language_card) CardView language_card;
    String books, profile_id , strengths, passions, skills, languages,meta;
    ArrayList<String>code,lan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.personaldata);
        ButterKnife.bind(this);
        setuptoolbar();
        set_up_card();
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
                            String book = books.substring(indexOfOpenBracket + 1, indexOfLastBracket);
                            String strArray[] = book.split(",");
                            for (int i = 0; i < 3; i++) {
                                LinearLayout row = new LinearLayout(AddProfileActivity.this);
                                row.setOrientation(LinearLayout.VERTICAL);
                                row.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                TextView book_header = new TextView(AddProfileActivity.this);
                                book_header.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                book_header.setText(" " + "Books");
                                book_header.setTextColor(BLUE);
                                row.addView(book_header);
                                for (int j = 0; j < strArray.length; j++) {
                                    String ab = strArray[j];
                                    TextView book_text = new TextView(AddProfileActivity.this);
                                    book_text.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                    book_text.setText("  " + (j + 1) + "." + " " + ab.trim() + " ");
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
                            String strength_array[] = strength.split(",");
                            for (int i = 0; i < 3; i++) {
                                LinearLayout row = new LinearLayout(AddProfileActivity.this);
                                row.setOrientation(LinearLayout.VERTICAL);
                                row.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                TextView strength_header = new TextView(AddProfileActivity.this);
                                strength_header.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                strength_header.setText(" " + "Strength");
                                strength_header.setTextColor(BLUE);
                                row.addView(strength_header);
                                for (int j = 0; j < strength_array.length; j++) {
                                    String ab = strength_array[j];
                                    TextView book_text = new TextView(AddProfileActivity.this);
                                    book_text.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                    book_text.setText("  " + (j + 1) + "." + " " + ab.trim() + " ");
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
                            String passion_array[] = passion.split(",");
                            for (int i = 0; i < 3; i++) {
                                LinearLayout row = new LinearLayout(AddProfileActivity.this);
                                row.setOrientation(LinearLayout.VERTICAL);
                                row.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                TextView passion_header = new TextView(AddProfileActivity.this);
                                passion_header.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                passion_header.setText(" " + "Passion");
                                passion_header.setTextColor(BLUE);
                                row.addView(passion_header);
                                for (int j = 0; j < passion_array.length; j++) {
                                    String ab = passion_array[j];
                                    TextView book_text = new TextView(AddProfileActivity.this);
                                    book_text.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                    book_text.setText("  " + (j + 1) + "." + " " + ab.trim() + " ");
                                    book_text.setTextColor(BLACK);
                                    row.addView(book_text);
                                }
                                passion_card.addView(row);
                            }
                        }
                        if (skills != null && !skills.isEmpty()) {
                            skills_card.setVisibility(View.VISIBLE);
                              code = new ArrayList<String>();
                        try {
                            JSONArray jsonarray = new JSONArray(skills);
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                String codee = jsonobject.getString("code");
                                code.add(codee);
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
                                skills_header.setTextColor(BLUE);
                                row.addView(skills_header);
                                for (int j = 0; j < code.size(); j++) {
                                    String ab1 = code.get(j);
                                    TextView book_text = new TextView(AddProfileActivity.this);
                                    book_text.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                                    book_text.setText("  " + (j + 1) + "." + " " + ab1.trim() + " ");
                                    book_text.setTextColor(BLACK);
                                    row.addView(book_text);
                                }
                                skills_card.addView(row);
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
                                    book_text.setText("  " + (j + 1) + "." + " " + ab1.trim() + " ");
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
        toolbar.setTitle(" Add Profile");
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
        builderSingle.setIcon(R.drawable.common_google_signin_btn_icon_dark);
        builderSingle.setTitle("Select Your Choice:-");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                AddProfileActivity.this,
                android.R.layout.simple_expandable_list_item_1);
        arrayAdapter.add("Book");
        arrayAdapter.add("Skills");
        arrayAdapter.add("Strength");
        arrayAdapter.add("Languages");
        arrayAdapter.add("Passion");
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
                                                        for (int i = 0; i < dynamic_book_lay.getChildCount(); i++) {
                                                            EditText editText = (EditText) dynamic_book_lay.getChildAt(i).findViewById(R.id.textout);
                                                            book_list.add(i, editText.getText().toString());
                                                        }
                                                        book_list.add(book_list.size(), book_name.getText().toString());
                                                        JSONObject metaa = new JSONObject();
                                                        try {
                                                            metaa.put("books", book_list);
                                                            metaa.put("passion", passions);
                                                            metaa.put("strength", strengths);
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

                                AlertDialog book_alert = book_builder.create();
                                book_alert.show();
                                break;
                            case 1:
                                LayoutInflater skill = LayoutInflater.from(AddProfileActivity.this);
                                final View skill_view = skill.inflate(R.layout.skill_dialog, null);
                                AlertDialog.Builder skill_builder = new AlertDialog.Builder(AddProfileActivity.this);
                                skill_builder.setView(skill_view);
                                final EditText ski_title = (EditText) skill_view.findViewById(R.id.ski_title);
                                final EditText ski_description = (EditText) skill_view.findViewById(R.id.ski_description);
                                final LinearLayout skill_layout = (LinearLayout) skill_view.findViewById(R.id.skill_container);
                                final ImageView add_skill_row = (ImageView) skill_view.findViewById(R.id.add_skill_view);
                                add_skill_row.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        LayoutInflater skill_row = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View add_skill_view = skill_row.inflate(R.layout.skill_dynamic_row, null);
                                        ImageView remove_skill = (ImageView) add_skill_view.findViewById(R.id.remove_skill);

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
                                                        for (int i = 0; i < skill_layout.getChildCount(); i++) {
                                                            try {
                                                                skill_bit = new JSONObject();
                                                                EditText title = (EditText) skill_layout.getChildAt(i).findViewById(R.id.skill_name);
                                                                EditText description = (EditText) skill_layout.getChildAt(i).findViewById(R.id.skill_level);
                                                                skill_bit.put("code", title.getText().toString());
                                                                skill_bit.put("level", description.getText().toString());
                                                                skill_bit.put("type", "declared");
                                                                final_hash.put(skill_bit);
                                                            }catch (Exception e){
                                                                e.printStackTrace();
                                                            }

                                                        }
                                                        JSONObject skill_bit_main = new JSONObject();
                                                        try {
                                                            skill_bit_main.put("code", ski_title.getText().toString());
                                                            skill_bit_main.put("level", ski_description.getText().toString());
                                                            skill_bit_main.put("type", "declared");
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                        final_hash.put(skill_bit_main);
                                                        System.out.println(final_hash + "final hash");
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

                                                        for (int i = 0; i < str_layout.getChildCount(); i++) {
                                                            EditText editText = (EditText) str_layout.getChildAt(i).findViewById(R.id.strength_name);
                                                            strength_list.add(i, editText.getText().toString());
                                                        }
                                                        strength_list.add(strength_list.size(), strenght_name.getText().toString());
                                                        JSONObject metaa = new JSONObject();
                                                        try {
                                                            metaa.put("books", books);
                                                            metaa.put("passion", passions);
                                                            metaa.put("strength", strength_list);
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
                                final EditText language_level = (EditText) language_view.findViewById(R.id.lan_level);
                                final ImageView add_language_row = (ImageView) language_view.findViewById(R.id.add_language_row);
                                add_language_row.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        LayoutInflater layoutInflater = (LayoutInflater) AddProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View add_lan_View = layoutInflater.inflate(R.layout.dynamic_language_row, null);
                                        ImageView buttonRemove = (ImageView) add_lan_View.findViewById(R.id.remove_language);
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
                                                        for (int i = 0; i < lan_layout.getChildCount(); i++) {
                                                            language_bit = new JSONObject();
                                                            EditText title = (EditText) lan_layout.getChildAt(i).findViewById(R.id.language_name);
                                                            EditText description = (EditText) lan_layout.getChildAt(i).findViewById(R.id.language_level);
                                                            try{
                                                                language_bit.put("name", title.getText().toString());
                                                                language_bit.put("level", description.getText().toString());
                                                            }catch (Exception e){
                                                                e.printStackTrace();
                                                            }

                                                            final_hash.put(language_bit);

                                                        }
                                                        JSONObject language_bit_main = new JSONObject();
                                                        try{
                                                            language_bit_main.put("name", language_title.getText().toString());
                                                            language_bit_main.put("level", language_level.getText().toString());
                                                        }catch (Exception e){
                                                            e.printStackTrace();
                                                        }
                                                        final_hash.put(language_bit_main);
                                                        System.out.println(final_hash + "final hash");
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
                                                        for (int i = 0; i < passion_layout.getChildCount(); i++) {
                                                            EditText passion_name = (EditText) passion_layout.getChildAt(i).findViewById(R.id.passion_name);
                                                            passion_list.add(i, passion_name.getText().toString());
                                                        }
                                                        passion_list.add(passion_list.size(), passion_name.getText().toString());
                                                        JSONObject metaa = new JSONObject();
                                                        try {
                                                            metaa.put("books", books);
                                                            metaa.put("passion", passion_list);
                                                            metaa.put("strength", strengths);
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
                            default:
                                break;

                        }


                    }
                });
        builderSingle.show();


    }
}
