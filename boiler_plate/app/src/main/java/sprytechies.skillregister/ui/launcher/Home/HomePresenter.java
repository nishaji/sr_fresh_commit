package sprytechies.skillregister.ui.launcher.Home;

import java.util.List;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sprytechies.skillregister.data.DataManager;
import sprytechies.skillregister.data.model.AwardInsert;
import sprytechies.skillregister.data.model.CertificateInsert;
import sprytechies.skillregister.data.model.ContactInsert;
import sprytechies.skillregister.data.model.EducationInsert;
import sprytechies.skillregister.data.model.ExperienceInsert;
import sprytechies.skillregister.ui.base.BasePresenter;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;

public class HomePresenter extends BasePresenter<HomeMvp> {
    @Inject DataManager mDataManager;
    private Subscription mSubscription;
    @Inject
    public HomePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }
    @Override
    public void attachView(HomeMvp mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    void loadEducation() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getEducation().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Subscriber<List<EducationInsert>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the education.");
                        getMvpView().showEducationError();
                    }
                    @Override
                    public void onNext(List<EducationInsert> education) {
                        if (education.isEmpty()) {
                        } else {
                            getMvpView().showEducations(education);
                        }
                    }
                });

    }
     void loadExperience() {
         checkViewAttached();RxUtil.unsubscribe(mSubscription);mSubscription = mDataManager.getExperience()
            .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe(new Subscriber<List<ExperienceInsert>>() {
        @Override
        public void onCompleted() {
        }@Override
        public void onError(Throwable e) {
            Timber.e(e, "There was an error loading the education.");
            getMvpView().showExperienceError();
        }@Override
        public void onNext(List<ExperienceInsert> experience) {
            if (experience.isEmpty()) {

            } else {
                getMvpView().showExperience(experience);
            }
        }
    });
}
    void loadAward() {
        checkViewAttached();RxUtil.unsubscribe(mSubscription);mSubscription = mDataManager.getAwards()
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<AwardInsert>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the award.");
                        getMvpView().showExperienceError();
                    }
                    @Override
                    public void onNext(List<AwardInsert> experience) {
                        if (experience.isEmpty()) {

                        } else {
                            getMvpView().showAward(experience);
                        }
                    }
                });
    }
    void loadContact() {
        checkViewAttached();RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getContact()
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<ContactInsert>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the contact.");
                        getMvpView().showExperienceError();
                    }
                    @Override
                    public void onNext(List<ContactInsert> experience) {
                        if (experience.isEmpty()) {

                        } else {
                            getMvpView().showContact(experience);
                        }
                    }
                });
    }
    void loadCertificate() {
        checkViewAttached();RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getCertificate().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Subscriber<List<CertificateInsert>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the certificate.");
                        getMvpView().showExperienceError();
                    }
                    @Override
                    public void onNext(List<CertificateInsert> experience) {
                        if (experience.isEmpty()) {

                        } else {
                            getMvpView().showCertificate(experience);
                        }
                    }
                });
    }

}


