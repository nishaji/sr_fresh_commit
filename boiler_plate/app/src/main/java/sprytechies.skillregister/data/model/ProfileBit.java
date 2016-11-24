package sprytechies.skillregister.data.model;


import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by sprydev5 on 25/10/16.
 */
@AutoValue
public abstract class ProfileBit implements Parcelable {
    @Nullable public abstract String type();
    @Nullable public abstract String books();
    @Nullable  public abstract String skills();
    @Nullable public abstract String languages();
    @Nullable public abstract String routine();
    @Nullable public abstract String strength();
    @Nullable public abstract String passion();
    @Nullable public abstract String id();

    public static ProfileBit.Builder builder() {
        return new AutoValue_ProfileBit.Builder();
    }

    public static TypeAdapter<ProfileBit> typeAdapter(Gson gson) {
        return new AutoValue_ProfileBit.GsonTypeAdapter(gson);
    }
    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setType(String type);
        public abstract Builder setBooks(String books);
        public abstract Builder setSkills(String skills);
        public abstract Builder setLanguages(String languages);
        public abstract Builder setRoutine(String routine);
        public abstract Builder setStrength(String strength);
        public abstract Builder setPassion(String passion);
        public abstract Builder setId(String id);
        public abstract ProfileBit build();



    }
}
