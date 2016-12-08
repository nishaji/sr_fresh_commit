package sprytechies.skillregister.ui.launcher.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import sprytechies.skillregister.BoilerplateApplication;
import sprytechies.skillregister.R;
import sprytechies.skillregister.data.model.AwardInsert;
import sprytechies.skillregister.data.model.CertificateInsert;
import sprytechies.skillregister.data.model.ContactInsert;
import sprytechies.skillregister.data.model.EducationInsert;
import sprytechies.skillregister.data.model.ExperienceInsert;
import sprytechies.skillregister.data.model.ProjectInsert;
import sprytechies.skillregister.data.model.PublicationInsert;
import sprytechies.skillregister.data.model.volunteerInsert;
import sprytechies.skillregister.ui.launcher.AwardPreview.AwardPreviewAdapter;
import sprytechies.skillregister.ui.launcher.CertificatePreview.CertificatePreviewAdapter;
import sprytechies.skillregister.ui.launcher.ContactPreview.ContactPreviewAdapter;
import sprytechies.skillregister.ui.launcher.EducationPresenter.EducationPreviewAdapter;
import sprytechies.skillregister.ui.launcher.ExperiencePreview.ExperiencePreviewAdapter;

public class HomeFragment extends Fragment implements HomeMvp {

    RecyclerView education,experience,award,contact,certificate;
    @Inject EducationPreviewAdapter educationAdapter;
    @Inject ExperiencePreviewAdapter experineceAdapter;
    @Inject AwardPreviewAdapter awardAdapter;
    @Inject ContactPreviewAdapter contactAdapter;
    @Inject CertificatePreviewAdapter certificateAdpter;
    @Inject HomePresenter eduPresenter, expPresenter,awardPresenter,contactPresenter,certPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x = inflater.inflate(R.layout.home_tab_layout, null);
        BoilerplateApplication.get(HomeFragment.this.getContext()).getComponent().inject(this);
        education=(RecyclerView)x.findViewById(R.id.education_recycler);
        experience=(RecyclerView)x.findViewById(R.id.experience_recycler);
        award=(RecyclerView)x.findViewById(R.id.award_recycler);
        contact=(RecyclerView)x.findViewById(R.id.contact_recycler);
        certificate=(RecyclerView)x.findViewById(R.id.certificate_recycler);
        setEducationrecycler();setExperienceRecycler();setAwardrecycler();
        setContactrecycler();setCertificaterecycler();return x;
    }

    private void setAwardrecycler() {
        award.setLayoutManager(new LinearLayoutManager(HomeFragment.this.getActivity()));
        awardAdapter= new AwardPreviewAdapter();
        award.setAdapter(awardAdapter);awardPresenter.attachView(this);awardPresenter.loadAward();
    }
    private void setContactrecycler() {
        contact.setLayoutManager(new LinearLayoutManager(HomeFragment.this.getActivity()));
        contactAdapter= new ContactPreviewAdapter();
        contact.setAdapter(contactAdapter);contactPresenter.attachView(this);contactPresenter.loadContact();
    }
    private void setCertificaterecycler() {
        certificate.setLayoutManager(new LinearLayoutManager(HomeFragment.this.getActivity()));
        certificateAdpter= new CertificatePreviewAdapter();
        certificate.setAdapter(certificateAdpter);certPresenter.attachView(this);certPresenter.loadCertificate();
    }
    public void setEducationrecycler(){
        education.setLayoutManager(new LinearLayoutManager(HomeFragment.this.getActivity()));
        educationAdapter= new EducationPreviewAdapter();education.setAdapter(educationAdapter);
        eduPresenter.attachView(this);eduPresenter.loadEducation();
    }
    public void setExperienceRecycler(){
        experience.setLayoutManager(new LinearLayoutManager(HomeFragment.this.getActivity()));
        experineceAdapter=new ExperiencePreviewAdapter();experience.setAdapter(experineceAdapter);
        expPresenter.attachView(this);expPresenter.loadExperience();
    }

    @Override
    public void showEducations(List<EducationInsert> education) {
        educationAdapter.setEducation(education);educationAdapter.notifyDataSetChanged();
    }
    @Override
    public void showExperience(List<ExperienceInsert> experience) {
        experineceAdapter.setExperience(experience);experineceAdapter.notifyDataSetChanged();
    }
    @Override
    public void showAward(List<AwardInsert> award) {
        awardAdapter.setAwards(award);awardAdapter.notifyDataSetChanged();
    }
    @Override
    public void showContact(List<ContactInsert> contact) {
        contactAdapter.setContacts(contact);contactAdapter.notifyDataSetChanged();

    }
    @Override
    public void showCertificate(List<CertificateInsert> certificate) {
        certificateAdpter.setCertificates(certificate);certificateAdpter.notifyDataSetChanged();
    }
    @Override
    public void showProject(List<ProjectInsert> project) {

    }
    @Override
    public void showPublication(List<PublicationInsert> publication) {

    }
    @Override
    public void showVolunteer(List<volunteerInsert> volunteer) {

    }
    @Override
    public void showEducationError() {
        educationAdapter.setEducation(Collections.<EducationInsert>emptyList());
        educationAdapter.notifyDataSetChanged();
        Toast.makeText(HomeFragment.this.getContext(), R.string.empty_awards, Toast.LENGTH_LONG).show();
    }
    @Override
    public void showExperienceError() {

    }


}