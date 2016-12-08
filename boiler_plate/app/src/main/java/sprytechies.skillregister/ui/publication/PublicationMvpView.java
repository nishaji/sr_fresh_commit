package sprytechies.skillregister.ui.publication;


import java.util.List;
import sprytechies.skillregister.data.model.PublicationInsert;
import sprytechies.skillregister.ui.base.MvpView;

public interface PublicationMvpView extends MvpView {
    void showPublications(List<PublicationInsert> ribots);
    void showPublicationEmpty();
    void showError();
}
