package sprytechies.skillregister.data;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
import sprytechies.skillregister.data.local.DatabaseHelper;
import sprytechies.skillregister.data.local.PreferencesHelper;
import sprytechies.skillregister.data.model.AwardInsert;
import sprytechies.skillregister.data.model.CertificateInsert;
import sprytechies.skillregister.data.model.ContactInsert;
import sprytechies.skillregister.data.model.EducationInsert;
import sprytechies.skillregister.data.model.ExperienceInsert;
import sprytechies.skillregister.data.model.ProjectInsert;
import sprytechies.skillregister.data.model.PublicationInsert;
import sprytechies.skillregister.data.model.volunteerInsert;



@Singleton
public class DataManager {
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;


    @Inject
    public DataManager(PreferencesHelper preferencesHelper, DatabaseHelper databaseHelper) {
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<List<AwardInsert>> getAwards() {
        return mDatabaseHelper.getAwards().distinct();
    }
    public Observable<List<EducationInsert>> getEducation() {
        return mDatabaseHelper.getEducation().distinct();
    }

    public Observable<List<ExperienceInsert>> getExperience() {
        return mDatabaseHelper.getExperience().distinct();
    }
    public Observable<List<ProjectInsert>> getProject() {
        return mDatabaseHelper.getProject().distinct();
    }
    public Observable<List<CertificateInsert>> getCertificate() {
        return mDatabaseHelper.getCertificate().distinct();
    }
    public Observable<List<ContactInsert>> getContact() {
        return mDatabaseHelper.getContact().distinct();
    }
    public Observable<List<volunteerInsert>> getVolunteer() {
        return mDatabaseHelper.getVolunteer().distinct();
    }
    public Observable<List<PublicationInsert>> getPublication() {
        return mDatabaseHelper.getPublication().distinct();
    }
}
