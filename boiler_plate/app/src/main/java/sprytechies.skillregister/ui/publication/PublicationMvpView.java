package sprytechies.skillregister.ui.publication;


import java.util.List;
import sprytechies.skillregister.data.model.PublicationInsert;
import sprytechies.skillregister.ui.base.MvpView;


/**
 * Created by sprydev5 on 4/10/16.
 */

public interface PublicationMvpView extends MvpView {
    void showPublications(List<PublicationInsert> ribots);

    void showPublicationEmpty();

    void showError();
}
