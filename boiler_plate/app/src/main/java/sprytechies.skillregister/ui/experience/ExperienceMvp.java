package sprytechies.skillregister.ui.experience;


import java.util.List;
import sprytechies.skillregister.data.model.ExperienceInsert;
import sprytechies.skillregister.ui.base.MvpView;

public interface ExperienceMvp extends MvpView {
    void showExperiences(List<ExperienceInsert> ribots);
    void showExperienceEmpty();
    void showError();
}
