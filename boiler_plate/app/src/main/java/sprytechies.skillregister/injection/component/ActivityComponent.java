package sprytechies.skillregister.injection.component;

import dagger.Subcomponent;
import sprytechies.skillregister.injection.PerActivity;
import sprytechies.skillregister.injection.module.ActivityModule;
import sprytechies.skillregister.ui.award.AddAwardActivity;
import sprytechies.skillregister.ui.award.AwardActivity;
import sprytechies.skillregister.ui.certificate.AddCertificate;
import sprytechies.skillregister.ui.certificate.CertificateActivity;
import sprytechies.skillregister.ui.contact.AddContact;
import sprytechies.skillregister.ui.contact.ConatctActivity;
import sprytechies.skillregister.ui.education.AddEducation;
import sprytechies.skillregister.ui.education.EducationActivity;
import sprytechies.skillregister.ui.experience.AddExperience;
import sprytechies.skillregister.ui.experience.ExperienceActivity;
import sprytechies.skillregister.ui.home.HomeActivity;
import sprytechies.skillregister.ui.launcher.Exportpdf.ExportPdf;
import sprytechies.skillregister.ui.launcher.Permission.SetPermission;
import sprytechies.skillregister.ui.launcher.activity.ViewActivity;
import sprytechies.skillregister.ui.profile.AddProfileActivity;
import sprytechies.skillregister.ui.project.AddProject;
import sprytechies.skillregister.ui.project.ProjectActivity;
import sprytechies.skillregister.ui.publication.ActivityPublication;
import sprytechies.skillregister.ui.publication.AddPublication;
import sprytechies.skillregister.ui.volunteer.AddVolunteer;
import sprytechies.skillregister.ui.volunteer.VolunteerActivity;


/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(AwardActivity awardActivity);void inject(CertificateActivity certificateActivity);
    void inject(ConatctActivity conatctActivity);void inject(EducationActivity educationActivity);void inject(ExperienceActivity experienceActivity);
    void inject(ProjectActivity projectActivity);void inject(ActivityPublication activityPublication);void inject(AddAwardActivity addAwardActivity);
    void inject(AddEducation addEducation);void inject(AddExperience addExperience);void inject(AddProject addProject);
    void inject(AddPublication addPublication);void inject(AddContact addContact);void inject(AddCertificate addCertificate);
    void inject(ViewActivity viewActivity);void inject(HomeActivity homeActivity);
    void inject(AddProfileActivity addProfileActivity);void inject(ExportPdf exportPdf);
    void inject(AddVolunteer addVolunteer);void inject(VolunteerActivity volunteerActivity);void inject(SetPermission setPermission);



}
