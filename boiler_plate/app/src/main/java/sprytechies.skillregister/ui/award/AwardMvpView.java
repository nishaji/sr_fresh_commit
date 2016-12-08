package sprytechies.skillregister.ui.award;

import java.util.List;
import sprytechies.skillregister.data.model.AwardInsert;
import sprytechies.skillregister.ui.base.MvpView;

public interface AwardMvpView extends MvpView {

    void showAwards(List<AwardInsert> awardInserts);
    void showAwardEmpty();
    void showError();
}
