package sprytechies.skillregister.ui.contact;

import java.util.List;

import sprytechies.skillregister.data.model.ContactInsert;
import sprytechies.skillregister.ui.base.MvpView;

public interface ContactMvpView extends MvpView {
    void showConatcts(List<ContactInsert> ribots);
    void showConatctEmpty();
    void showError();
}
