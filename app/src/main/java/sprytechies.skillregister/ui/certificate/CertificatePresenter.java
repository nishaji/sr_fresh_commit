package sprytechies.skillregister.ui.certificate;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sprytechies.skillregister.data.DataManager;
import sprytechies.skillregister.data.model.CertificateInsert;
import sprytechies.skillregister.injection.ConfigPersistent;
import sprytechies.skillregister.ui.base.BasePresenter;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;

@ConfigPersistent
public class CertificatePresenter extends BasePresenter<CertificateMvpView> {
    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public CertificatePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(CertificateMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadCertificate() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getCertificate().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Subscriber<List<CertificateInsert>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the ribots.");
                        getMvpView().showError();
                    }
                    @Override
                    public void onNext(List<CertificateInsert> certificateInserts) {
                        if (certificateInserts.isEmpty()) {
                            getMvpView().showCertificateEmpty();
                        } else {
                            getMvpView().showCertificates(certificateInserts);
                        }
                    }
                });
    }

}
