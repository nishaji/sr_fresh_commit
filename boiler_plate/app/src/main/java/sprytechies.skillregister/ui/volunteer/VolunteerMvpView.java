package sprytechies.skillregister.ui.volunteer;


import java.util.List;
import sprytechies.skillregister.data.model.volunteerInsert;
import sprytechies.skillregister.ui.base.MvpView;

/**
 * Created by sprydev5 on 4/10/16.
 */

public interface VolunteerMvpView extends MvpView {
    void showVolunteers(List<volunteerInsert> ribots);

    void showVolunteerEmpty();

    void showError();
}
