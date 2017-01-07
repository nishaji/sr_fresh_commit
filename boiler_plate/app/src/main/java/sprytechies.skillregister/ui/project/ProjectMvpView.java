package sprytechies.skillregister.ui.project;

import java.util.List;
import sprytechies.skillregister.data.model.ProjectInsert;
import sprytechies.skillregister.ui.base.MvpView;

public interface ProjectMvpView extends MvpView {
    void showProjectss(List<ProjectInsert> ribots);
    void showProjectEmpty();
    void showError();
}
