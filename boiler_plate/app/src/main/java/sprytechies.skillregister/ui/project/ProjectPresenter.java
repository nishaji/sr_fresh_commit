package sprytechies.skillregister.ui.project;

import java.util.List;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sprytechies.skillregister.data.DataManager;
import sprytechies.skillregister.data.model.ProjectInsert;
import sprytechies.skillregister.injection.ConfigPersistent;
import sprytechies.skillregister.ui.base.BasePresenter;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;

@ConfigPersistent
public class ProjectPresenter extends BasePresenter<ProjectMvpView> {
    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public ProjectPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }
    @Override
    public void attachView(ProjectMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadProject() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);mSubscription = mDataManager.getProject().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Subscriber<List<ProjectInsert>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the project.");
                        getMvpView().showError();
                    }
                    @Override
                    public void onNext(List<ProjectInsert> projectInserts) {
                        if (projectInserts.isEmpty()) {
                            getMvpView().showProjectEmpty();
                        } else {
                            getMvpView().showProjectss(projectInserts);
                        }
                    }
                });
    }

}




