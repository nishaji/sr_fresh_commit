package sprytechies.skillregister.ui.project;

import java.util.List;
import sprytechies.skillregister.data.model.ProjectInsert;
import sprytechies.skillregister.ui.base.MvpView;


/**
 * Created by sprydev5 on 4/10/16.
 */

public interface ProjectMvpView extends MvpView {
    void showProjectss(List<ProjectInsert> ribots);

    void showProjectEmpty();

    void showError();
}
