package sprytechies.skillregister.ui.publication;


import java.util.List;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sprytechies.skillregister.data.DataManager;
import sprytechies.skillregister.data.model.PublicationInsert;
import sprytechies.skillregister.injection.ConfigPersistent;
import sprytechies.skillregister.ui.base.BasePresenter;
import sprytechies.skillregister.util.RxUtil;


/**
 * Created by sprydev5 on 4/10/16.
 */
@ConfigPersistent
public class PublicationPresenter extends BasePresenter<PublicationMvpView> {
    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject public PublicationPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override public void attachView(PublicationMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadpublication() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getPublication()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<PublicationInsert>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<PublicationInsert> awards) {
                        if (awards.isEmpty()) {
                            getMvpView().showPublicationEmpty();
                        } else {
                            getMvpView().showPublications(awards);
                        }
                    }
                });
    }


}
