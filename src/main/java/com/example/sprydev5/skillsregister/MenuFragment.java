package com.example.sprydev5.skillsregister;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


import com.example.sprydev5.skillsregister.reside.ResideMenu;

import java.util.ArrayList;

/**
 * Created by sprydev5 on 22/8/16.
 */
public class MenuFragment extends Fragment {

    private View parentView;
    private ResideMenu resideMenu;
    private GridviewAdapter mAdapter;
    private ArrayList<String> listCountry;
    private ArrayList<Integer> listFlag;

    private GridView gridView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.home, container, false);
        setUpViews();
        prepareList();

        // prepared arraylist and passed it to the Adapter class
        mAdapter = new GridviewAdapter(MenuFragment.this.getActivity(),listCountry, listFlag);

        // Set custom adapter to gridview
        gridView = (GridView) parentView.findViewById(R.id.gridView1);
        gridView.setAdapter(mAdapter);

        // Implement On Item click listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {

                Toast.makeText(MenuFragment.this.getActivity(), mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        Intent intent = new Intent(MenuFragment.this.getActivity(), PersonalData.class);
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(MenuFragment.this.getActivity(), R.anim.animation, R.anim.animation2).toBundle();
                        startActivity(intent, bndlanimation);
                        break;
                    case 1:
                        Intent intent1 = new Intent(MenuFragment.this.getActivity(), ViewandAddeducation.class);
                        Bundle bndlanimation1 = ActivityOptions.makeCustomAnimation(MenuFragment.this.getActivity(), R.anim.animation, R.anim.animation2).toBundle();
                        startActivity(intent1, bndlanimation1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(MenuFragment.this.getActivity(), WorkExperience.class);
                        Bundle bndlanimation2 = ActivityOptions.makeCustomAnimation(MenuFragment.this.getActivity(), R.anim.animation, R.anim.animation2).toBundle();
                        startActivity(intent2, bndlanimation2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(MenuFragment.this.getActivity(), Projects.class);
                        Bundle bndlanimation3 = ActivityOptions.makeCustomAnimation(MenuFragment.this.getActivity(), R.anim.animation, R.anim.animation2).toBundle();
                        startActivity(intent3, bndlanimation3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(MenuFragment.this.getActivity(), ViewandEditCertificate.class);
                        Bundle bndlanimation4 = ActivityOptions.makeCustomAnimation(MenuFragment.this.getActivity(), R.anim.animation, R.anim.animation2).toBundle();
                        startActivity(intent4, bndlanimation4);
                        break;
                    case 5:
                        Intent intent5 = new Intent(MenuFragment.this.getActivity(), ViewandAddContact.class);
                        Bundle bndlanimation5 = ActivityOptions.makeCustomAnimation(MenuFragment.this.getActivity(), R.anim.animation, R.anim.animation2).toBundle();
                        startActivity(intent5, bndlanimation5);
                        break;
                    case 6:
                        Intent intent6 = new Intent(MenuFragment.this.getActivity(), Skills.class);
                        Bundle bndlanimation6 = ActivityOptions.makeCustomAnimation(MenuFragment.this.getActivity(), R.anim.animation, R.anim.animation2).toBundle();
                        startActivity(intent6, bndlanimation6);
                        break;
                    case 7:
                        Intent intent7 = new Intent(MenuFragment.this.getActivity(), Objective.class);
                        Bundle bndlanimation7 = ActivityOptions.makeCustomAnimation(MenuFragment.this.getActivity(), R.anim.animation, R.anim.animation2).toBundle();
                        startActivity(intent7, bndlanimation7);
                        break;
                    case 8:
                        Intent intent8 = new Intent(MenuFragment.this.getActivity(), ViewAndAddVolunteers.class);
                        Bundle bndlanimation8 = ActivityOptions.makeCustomAnimation(MenuFragment.this.getActivity(), R.anim.animation, R.anim.animation2).toBundle();
                        startActivity(intent8, bndlanimation8);
                        break;
                    case 9:
                        Intent intent9 = new Intent(MenuFragment.this.getActivity(), ViewAndAddAwards.class);
                        Bundle bndlanimation9 = ActivityOptions.makeCustomAnimation(MenuFragment.this.getActivity(), R.anim.animation, R.anim.animation2).toBundle();
                        startActivity(intent9, bndlanimation9);
                        break;
                    case 10:
                        Intent intent10 = new Intent(MenuFragment.this.getActivity(), ViewAndAddPublication.class);
                        Bundle bndlanimation10 = ActivityOptions.makeCustomAnimation(MenuFragment.this.getActivity(), R.anim.animation, R.anim.animation2).toBundle();
                        startActivity(intent10, bndlanimation10);
                        break;
                }
            }


        });
        return parentView;
    }
    public void prepareList()
    {
        listCountry = new ArrayList<String>();

        listCountry.add("Personal Info");
        listCountry.add("Education");
        listCountry.add("Work Experience");
        listCountry.add("Projects");
        listCountry.add("Certificates");
        listCountry.add("Contact");
        listCountry.add("Skills");
        listCountry.add("Objective");
        listCountry.add("Volunteers");
        listCountry.add("Awards");
        listCountry.add("Publication");
        listFlag = new ArrayList<Integer>();
        listFlag.add(R.drawable.ic_personal_details);
        listFlag.add(R.drawable.ic_education);
        listFlag.add(R.drawable.ic_work_experience);
        listFlag.add(R.drawable.ic_project);
        listFlag.add(R.drawable.ic_languages);
        listFlag.add(R.drawable.ic_references);
        listFlag.add(R.drawable.ic_skills);
        listFlag.add(R.drawable.ic_objective);
        listFlag.add(R.drawable.ic_declaration);
        listFlag.add(R.drawable.award);
        listFlag.add(R.drawable.publication);
    }

    private void setUpViews() {
        LanucherActivity parentActivity = (LanucherActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();

      /*  parentView.findViewById(R.id.btn_open_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });*/

        // add gesture operation's ignored views
        // FrameLayout ignored_view = (FrameLayout) parentView.findViewById(R.id.ignored_view);
        //  resideMenu.addIgnoredView(ignored_view);
    }

}

