package sprytechies.skillregister.data;



import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;
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
import sprytechies.skillregister.data.remote.RibotsService;


@Singleton
public class DataManager {

    private final RibotsService mRibotsService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;
    String id;

    @Inject
    public DataManager(RibotsService ribotsService,  PreferencesHelper preferencesHelper,
                       DatabaseHelper databaseHelper) {
        mRibotsService = ribotsService;
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }



/*public Observable<AwardInsert> syncAward() {
                return mRibotsService.getAwards()
                .concatMap(new Func1<List<AwardInsert>, Observable<AwardInsert>>() {
                    @Override
                    public Observable<AwardInsert> call(List<AwardInsert> ribots) {
                        return mDatabaseHelper.setRibots(ribots);
                    }
                });

    }*/

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
    /*public Observable<List<SkillInsert>> getSkill() {
        return mDatabaseHelper.getSkill().distinct();
    }*/
    public Observable<List<volunteerInsert>> getVolunteer() {
        return mDatabaseHelper.getVolunteer().distinct();
    }
    public Observable<List<PublicationInsert>> getPublication() {
        return mDatabaseHelper.getPublication().distinct();
    }
}
