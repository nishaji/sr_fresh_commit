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
public abstract class ExperienceInsert implements Comparable<ExperienceInsert>, Parcelable {

    public abstract Experience experience();

    public static ExperienceInsert create(Experience experience) {
        return new AutoValue_ExperienceInsert(experience);
    }

    public static TypeAdapter<ExperienceInsert> typeAdapter(Gson gson) {
        return new AutoValue_ExperienceInsert.GsonTypeAdapter(gson);
    }

    @Override
    public int compareTo(@NonNull ExperienceInsert another) {
        return experience().company().compareToIgnoreCase(another.experience().company());
    }
}
