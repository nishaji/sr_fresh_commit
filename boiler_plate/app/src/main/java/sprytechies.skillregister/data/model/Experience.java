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
public abstract class Experience implements Parcelable {
    @Nullable public abstract String company();
    @Nullable public abstract String title();
    @Nullable public abstract String job();
    @Nullable public abstract Location location();
    @Nullable  public abstract String from();
    @Nullable public abstract String upto();
    @Nullable public abstract String type();
    @Nullable public abstract String status();
    @Nullable public abstract String id();
    @Nullable public abstract String createflag();
    @Nullable public abstract String updateflag();
    @Nullable public abstract String postflag();
    @Nullable public abstract String putflag();
    @Nullable public abstract String mongoid();
    @Nullable public abstract String date();


    public static Experience.Builder builder() {
        return new AutoValue_Experience.Builder();
    }

    public static TypeAdapter<Experience> typeAdapter(Gson gson) {
        return new AutoValue_Experience.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Experience.Builder setCompany(String company);
        public abstract Experience.Builder setTitle(String title);
        public abstract Experience.Builder setJob(String job);
        public abstract Experience.Builder setLocation(Location location);
        public abstract Experience.Builder setFrom(String from);
        public abstract Experience.Builder setUpto(String upto);
        public abstract Experience.Builder setType(String type);
        public abstract Experience.Builder setStatus(String status);
        public abstract Experience.Builder setId(String id);
        public abstract Experience.Builder setCreateflag(String createflag);
        public abstract Experience.Builder setUpdateflag(String updateflag);
        public abstract Experience.Builder setPostflag(String postflag);
        public abstract Experience.Builder setPutflag(String putflag);
        public abstract Experience.Builder setMongoid(String mongoid);
        public abstract Experience.Builder setDate(String date);
        public abstract Experience build();

    }
}
