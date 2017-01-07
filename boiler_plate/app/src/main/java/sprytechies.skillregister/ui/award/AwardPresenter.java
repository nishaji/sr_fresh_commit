package sprytechies.skillregister.ui.award;

import java.util.List;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sprytechies.skillregister.data.DataManager;
import sprytechies.skillregister.data.model.AwardInsert;
import sprytechies.skillregister.injection.ConfigPersistent;
import sprytechies.skillregister.ui.base.BasePresenter;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;

@ConfigPersistent
public class AwardPresenter extends BasePresenter<AwardMvpView> {
    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public AwardPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(AwardMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadAwards() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getAwards().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<AwardInsert>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the awards.");
                        getMvpView().showError();
                    }
                    @Override
                    public void onNext(List<AwardInsert> awards) {
                        if (awards.isEmpty()) {
                            getMvpView().showAwardEmpty();
                        } else {
                            getMvpView().showAwards(awards);
                        }
                    }
                });
    }

}
