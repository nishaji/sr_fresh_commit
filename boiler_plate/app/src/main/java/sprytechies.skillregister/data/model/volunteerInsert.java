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
public abstract class volunteerInsert implements Comparable<volunteerInsert>, Parcelable {

    public abstract Volunteer volunteer();

    public static volunteerInsert create(Volunteer volunteer) {
        return new AutoValue_volunteerInsert(volunteer);
    }

    public static TypeAdapter<volunteerInsert> typeAdapter(Gson gson) {
        return new AutoValue_volunteerInsert.GsonTypeAdapter(gson);
    }

    @Override
    public int compareTo(@NonNull volunteerInsert another) {
        return volunteer().role().compareToIgnoreCase(another.volunteer().role());
    }
}

