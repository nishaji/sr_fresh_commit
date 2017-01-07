package sprytechies.skillregister.ui.launcher.Home;


import java.util.List;

import sprytechies.skillregister.data.model.AwardInsert;
import sprytechies.skillregister.data.model.CertificateInsert;
import sprytechies.skillregister.data.model.ContactInsert;
import sprytechies.skillregister.data.model.EducationInsert;
import sprytechies.skillregister.data.model.ExperienceInsert;
import sprytechies.skillregister.data.model.ProjectInsert;
import sprytechies.skillregister.data.model.PublicationInsert;
import sprytechies.skillregister.data.model.volunteerInsert;
import sprytechies.skillregister.ui.base.MvpView;

/**
 * Created by sprydev5 on 17/11/16.
 */

public interface HomeMvp extends MvpView {
    void showEducations(List<EducationInsert> education);
    void showExperience(List<ExperienceInsert> experience);
    void showAward(List<AwardInsert>award);
    void showContact(List<ContactInsert>contact);
    void showCertificate(List<CertificateInsert>certificate);
    void showProject(List<ProjectInsert>project);
    void showPublication(List<PublicationInsert>publication);
    void showVolunteer(List<volunteerInsert> volunteer);
    void showEducationError();
    void showExperienceError();
}
