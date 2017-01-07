package sprytechies.skillregister.ui.education;

import java.util.List;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sprytechies.skillregister.data.DataManager;
import sprytechies.skillregister.data.model.EducationInsert;
import sprytechies.skillregister.injection.ConfigPersistent;
import sprytechies.skillregister.ui.base.BasePresenter;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;

@ConfigPersistent
public class EducationPresenter extends BasePresenter<EducationMvpView> {
    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public EducationPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }
    @Override
    public void attachView(EducationMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadEducation() {
        checkViewAttached();RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getEducation().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Subscriber<List<EducationInsert>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the education.");
                        getMvpView().showError();
                    }
                    @Override
                    public void onNext(List<EducationInsert> education) {
                        if (education.isEmpty()) {
                            getMvpView().showEducationEmpty();
                        } else {
                            getMvpView().showEducations(education);
                        }
                    }
                });
    }

}
