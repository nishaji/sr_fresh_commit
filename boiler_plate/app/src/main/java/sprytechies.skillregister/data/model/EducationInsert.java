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
public abstract class EducationInsert implements Comparable<EducationInsert>, Parcelable {

    public abstract Education education();

    public static EducationInsert create(Education education) {
        return new AutoValue_EducationInsert(education);
    }

    public static TypeAdapter<EducationInsert> typeAdapter(Gson gson) {
        return new AutoValue_EducationInsert.GsonTypeAdapter(gson);
    }

    @Override
    public int compareTo(@NonNull EducationInsert another) {
        return education().school().compareToIgnoreCase(another.education().school());
    }
}
