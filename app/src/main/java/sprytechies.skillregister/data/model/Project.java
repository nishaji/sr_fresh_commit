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
public abstract class Project implements Parcelable {
    @Nullable public abstract String project();
    @Nullable public abstract String role();
    @Nullable public abstract String meta();
    @Nullable public abstract String from();
    @Nullable public abstract String  upto();
    @Nullable public abstract String id();
    @Nullable public abstract String createflag();
    @Nullable public abstract String updateflag();
    @Nullable public abstract String postflag();
    @Nullable public abstract String putflag();
    @Nullable public abstract String mongoid();
    @Nullable public abstract String date();

    public static Project.Builder builder() {
        return new AutoValue_Project.Builder();
    }


    public static TypeAdapter<Project> typeAdapter(Gson gson) {
        return new AutoValue_Project.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Project.Builder setProject(String project);
        public abstract Project.Builder setRole(String role);
        public abstract Project.Builder setMeta(String meta);
        public abstract Project.Builder setFrom(String from);
        public abstract Project.Builder setUpto(String upto);
        public abstract Project.Builder setId(String id);
        public abstract Project.Builder setCreateflag(String createflag);
        public abstract Project.Builder setUpdateflag(String updateflag);
        public abstract Project.Builder setPostflag(String postflag);
        public abstract Project.Builder setPutflag(String putflag);
        public abstract Project.Builder setMongoid(String mongoid);
        public abstract Project.Builder setDate(String date);
        public abstract Project build();

    }
}
