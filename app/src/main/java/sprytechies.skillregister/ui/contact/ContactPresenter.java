package sprytechies.skillregister.ui.contact;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sprytechies.skillregister.data.DataManager;
import sprytechies.skillregister.data.model.ContactInsert;
import sprytechies.skillregister.injection.ConfigPersistent;
import sprytechies.skillregister.ui.base.BasePresenter;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;

@ConfigPersistent
public class ContactPresenter extends BasePresenter<ContactMvpView> {
    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public ContactPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }
    @Override
    public void attachView(ContactMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadContact() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getContact().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Subscriber<List<ContactInsert>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the ribots.");
                        getMvpView().showError();
                    }
                    @Override
                    public void onNext(List<ContactInsert> contactInserts) {
                        if (contactInserts.isEmpty()) {
                            getMvpView().showConatctEmpty();
                        } else {
                            getMvpView().showConatcts(contactInserts);
                        }
                    }
                });
    }

}


