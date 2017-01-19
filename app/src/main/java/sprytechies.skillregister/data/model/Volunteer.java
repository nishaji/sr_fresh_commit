package sprytechies.skillregister.data.model;


import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by sprydev5 on 4/10/16.
 */
@AutoValue
public abstract class Volunteer implements Parcelable {
    @Nullable public abstract String role();
    @Nullable public abstract String type();
    @Nullable public abstract String organisation();
    @Nullable public abstract String description();
    @Nullable public abstract String from();
    @Nullable public abstract String upto();
    @Nullable  public abstract String id();
    @Nullable public abstract String mongoid();

    public static Volunteer.Builder builder() {
        return new AutoValue_Volunteer.Builder();
    }


    public static TypeAdapter<Volunteer> typeAdapter(Gson gson) {
        return new AutoValue_Volunteer.GsonTypeAdapter(gson);
    }
    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Volunteer.Builder setRole(String role);
        public abstract Volunteer.Builder setType(String type);
        public abstract Volunteer.Builder setOrganisation(String organisation);
        public abstract Volunteer.Builder setDescription(String description);
        public abstract Volunteer.Builder setFrom(String from);
        public abstract Volunteer.Builder setUpto(String upto);
        public abstract Volunteer.Builder setId(String id);
        public abstract Volunteer.Builder setMongoid(String mongoid);
        public abstract Volunteer build();

    }
}

