package sprytechies.skillregister.ui.volunteer;

import java.util.List;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sprytechies.skillregister.data.DataManager;
import sprytechies.skillregister.data.model.volunteerInsert;
import sprytechies.skillregister.ui.base.BasePresenter;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;


/**
 * Created by sprydev5 on 4/10/16.
 */

public class VolunteerPresenter extends BasePresenter<VolunteerMvpView> {

   private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public VolunteerPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(VolunteerMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadvolunteer() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getVolunteer()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<volunteerInsert>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the ribots.");
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<volunteerInsert> awards) {
                        if (awards.isEmpty()) {
                            getMvpView().showVolunteerEmpty();
                        } else {
                            getMvpView().showVolunteers(awards);
                        }
                    }
                });
    }

}
