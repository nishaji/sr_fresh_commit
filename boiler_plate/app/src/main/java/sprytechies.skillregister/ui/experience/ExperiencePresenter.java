package sprytechies.skillregister.ui.experience;

import java.util.List;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sprytechies.skillregister.data.DataManager;
import sprytechies.skillregister.data.model.ExperienceInsert;
import sprytechies.skillregister.ui.base.BasePresenter;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;

public class ExperiencePresenter extends BasePresenter<ExperienceMvp> {
    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject public ExperiencePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }
    @Override
    public void attachView(ExperienceMvp mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadExperience() {
        checkViewAttached();RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getExperience().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Subscriber<List<ExperienceInsert>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the ribots.");
                        getMvpView().showError();
                    }
                    @Override
                    public void onNext(List<ExperienceInsert> awards) {
                        if (awards.isEmpty()) {
                            getMvpView().showExperienceEmpty();
                        } else {
                            getMvpView().showExperiences(awards);
                        }
                    }
                });
    }

}


