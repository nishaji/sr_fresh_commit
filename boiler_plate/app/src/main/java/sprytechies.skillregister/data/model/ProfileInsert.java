package sprytechies.skillregister.data.model;


import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by sprydev5 on 25/10/16.
 */
@AutoValue
public abstract class ProfileInsert implements Comparable<ProfileInsert>, Parcelable {

    public abstract ProfileBit profile();

    public static ProfileInsert create(ProfileBit profile) {
        return new AutoValue_ProfileInsert(profile);
    }

    public static TypeAdapter<ProfileInsert> typeAdapter(Gson gson) {
        return new AutoValue_ProfileInsert.GsonTypeAdapter(gson);
    }

    @Override
    public int compareTo(@NonNull ProfileInsert another) {
        return profile().passion().compareToIgnoreCase(another.profile().passion());
    }
}