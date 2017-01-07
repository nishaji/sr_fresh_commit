package sprytechies.skillregister.injection.component;

import android.app.Application;
import android.content.Context;
import javax.inject.Singleton;
import dagger.Component;




import sprytechies.skillregister.data.DataManager;
import sprytechies.skillregister.data.PullService;
import sprytechies.skillregister.data.SyncService;
import sprytechies.skillregister.data.UpdateService;
import sprytechies.skillregister.data.local.DatabaseHelper;
import sprytechies.skillregister.data.local.PreferencesHelper;
import sprytechies.skillregister.data.remote.RibotsService;
import sprytechies.skillregister.data.remote.postservice.AwardPost;
import sprytechies.skillregister.data.remote.postservice.CertificatePost;
import sprytechies.skillregister.data.remote.postservice.ContactPost;
import sprytechies.skillregister.data.remote.postservice.EducationPost;
import sprytechies.skillregister.data.remote.postservice.ExperiencePost;
import sprytechies.skillregister.data.remote.postservice.PostProfile;
import sprytechies.skillregister.data.remote.postservice.ProjectPost;
import sprytechies.skillregister.data.remote.postservice.PublicationPost;
import sprytechies.skillregister.data.remote.postservice.VolunteerPost;
import sprytechies.skillregister.data.remote.putservice.AwardPut;
import sprytechies.skillregister.data.remote.putservice.CertificatePut;
import sprytechies.skillregister.data.remote.putservice.EducationPut;
import sprytechies.skillregister.data.remote.putservice.ExperiencePut;
import sprytechies.skillregister.data.remote.putservice.ProjectPut;
import sprytechies.skillregister.data.remote.putservice.PublicationPut;
import sprytechies.skillregister.data.remote.putservice.VolunteerPut;
import sprytechies.skillregister.data.remote.putservice.contactPut;
import sprytechies.skillregister.injection.ApplicationContext;
import sprytechies.skillregister.injection.module.ApplicationModule;
import sprytechies.skillregister.ui.launcher.Home.HomeFragment;
import sprytechies.skillregister.util.RxEventBus;


@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {


    void inject(SyncService syncService);void inject(UpdateService updateService);void inject(HomeFragment homeFragment);
    void inject(PullService pullService);void inject(AwardPost awardPost);void inject(CertificatePost certificatePost);
    void inject(ContactPost contactPost);void inject(EducationPost educationPost);void inject(ExperiencePost experiencePost);
    void inject(ProjectPost projectPost);void inject(PublicationPost publicationPost);void inject(VolunteerPost volunteerPost);
    void inject(AwardPut awardPut);void inject(contactPut contactPut);void inject(CertificatePut certificatePut);
    void inject(EducationPut educationPut);void inject(ExperiencePut experiencePut);void inject(ProjectPut projectPut);
    void inject(PublicationPut publicationPut);void inject(VolunteerPut volunteerPut);void inject(PostProfile postProfile);

    @ApplicationContext
    Context context();
    Application application();
    RibotsService ribotsService();
    PreferencesHelper preferencesHelper();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
    RxEventBus eventBus();


}
