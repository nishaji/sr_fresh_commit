package sprytechies.skillregister.ui.education;


import java.util.List;
import sprytechies.skillregister.data.model.EducationInsert;
import sprytechies.skillregister.ui.base.MvpView;

public interface EducationMvpView extends MvpView {
    void showEducations(List<EducationInsert> education);
    void showEducationEmpty();
    void showError();
}
