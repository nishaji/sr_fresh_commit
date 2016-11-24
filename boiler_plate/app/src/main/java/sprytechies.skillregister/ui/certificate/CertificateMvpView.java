package sprytechies.skillregister.ui.certificate;

import java.util.List;

import sprytechies.skillregister.data.model.CertificateInsert;
import sprytechies.skillregister.ui.base.MvpView;


/**
 * Created by sprydev5 on 4/10/16.
 */

public interface CertificateMvpView extends MvpView {
    void showCertificates(List<CertificateInsert> certificateInserts);

    void showCertificateEmpty();

    void showError();
}
