package sprytechies.skillregister.ui.launcher;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import sprytechies.skillregister.R;
import sprytechies.skillregister.ui.award.AwardActivity;
import sprytechies.skillregister.ui.certificate.CertificateActivity;
import sprytechies.skillregister.ui.contact.ConatctActivity;
import sprytechies.skillregister.ui.education.EducationActivity;
import sprytechies.skillregister.ui.experience.ExperienceActivity;
import sprytechies.skillregister.ui.profile.AddProfileActivity;
import sprytechies.skillregister.ui.project.ProjectActivity;
import sprytechies.skillregister.ui.publication.ActivityPublication;
import sprytechies.skillregister.ui.volunteer.VolunteerActivity;


/**
 * Created by sprydev5 on 21/10/16.
 */

public class MenuFragment extends Fragment {
    GridView androidGridView;
    String[] gridViewString = {
           "Profile", "Award", "Certificate", "Contact", "Education",
            "Experience", "Project", "Publication", "Volunteer",

    } ;
    int[] gridViewImageId = {
            R.mipmap.proff, R.mipmap.award, R.mipmap.cert, R.mipmap.contact, R.mipmap.edu,
             R.mipmap.exp, R.mipmap.pro, R.mipmap.pub, R.mipmap.volun,

    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("menu Fragment is calling");
        /**
         *Inflate tab_layout and setup Views.
         */
        View x =inflater.inflate(R.layout.menu_fragment,container, false);
        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(MenuFragment.this.getActivity(), gridViewString, gridViewImageId);
        androidGridView=(GridView)x.findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
            switch (position) {
                case 0:
                    Bundle bndlanimation9 = ActivityOptions.makeCustomAnimation(MenuFragment.this.getActivity(), R.anim.animation, R.anim.animation2).toBundle();
                    startActivity(new Intent(MenuFragment.this.getActivity(), AddProfileActivity.class),bndlanimation9);
                    break;
                case 1:
                    Bundle bndlanimation1 = ActivityOptions.makeCustomAnimation(MenuFragment.this.getActivity(), R.anim.animation, R.anim.animation2).toBundle();
                    startActivity(new Intent(MenuFragment.this.getActivity(), AwardActivity.class),bndlanimation1);
                    break;
                case 2:
                    Bundle bndlanimation2 = ActivityOptions.makeCustomAnimation(MenuFragment.this.getActivity(), R.anim.animation, R.anim.animation2).toBundle();
                    startActivity(new Intent(MenuFragment.this.getActivity(), CertificateActivity.class),bndlanimation2);
                    break;
                case 3:
                    Bundle bndlanimation3 = ActivityOptions.makeCustomAnimation(MenuFragment.this.getActivity(), R.anim.animation, R.anim.animation2).toBundle();
                    startActivity(new Intent(MenuFragment.this.getActivity(), ConatctActivity.class),bndlanimation3);
                    break;
                case 4:
                    Bundle bndlanimation4 = ActivityOptions.makeCustomAnimation(MenuFragment.this.getActivity(), R.anim.animation, R.anim.animation2).toBundle();
                    startActivity(new Intent(MenuFragment.this.getActivity(), EducationActivity.class),bndlanimation4);
                    break;
                case 5:
                    Bundle bndlanimation5 = ActivityOptions.makeCustomAnimation(MenuFragment.this.getActivity(), R.anim.animation, R.anim.animation2).toBundle();
                    startActivity(new Intent(MenuFragment.this.getActivity(), ExperienceActivity.class),bndlanimation5);
                    break;
                case 6:
                    Bundle bndlanimation6 = ActivityOptions.makeCustomAnimation(MenuFragment.this.getActivity(), R.anim.animation, R.anim.animation2).toBundle();
                    startActivity(new Intent(MenuFragment.this.getActivity(), ProjectActivity.class),bndlanimation6);
                    break;
                case 7:
                    Bundle bndlanimation7 = ActivityOptions.makeCustomAnimation(MenuFragment.this.getActivity(), R.anim.animation, R.anim.animation2).toBundle();
                    startActivity(new Intent(MenuFragment.this.getActivity(), ActivityPublication.class),bndlanimation7);
                    break;
                case 8:
                    Bundle bndlanimation8 = ActivityOptions.makeCustomAnimation(MenuFragment.this.getActivity(), R.anim.animation, R.anim.animation2).toBundle();
                    startActivity(new Intent(MenuFragment.this.getActivity(), VolunteerActivity.class),bndlanimation8);
                    break;

            }
        }


    });
        return x;
    }
}
