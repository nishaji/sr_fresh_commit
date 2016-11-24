package sprytechies.skillregister.data.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by sprydev5 on 5/10/16.
 */
@AutoValue
public abstract class CertificateInsert implements Comparable<CertificateInsert>, Parcelable {

    public abstract Certificate certificate();

    public static CertificateInsert create(Certificate certificate) {
        return new AutoValue_CertificateInsert(certificate);
    }

    public static TypeAdapter<CertificateInsert> typeAdapter(Gson gson) {
        return new AutoValue_CertificateInsert.GsonTypeAdapter(gson);
    }

    @Override
    public int compareTo(@NonNull CertificateInsert another) {
        return certificate().name().compareToIgnoreCase(another.certificate().name());
    }
}