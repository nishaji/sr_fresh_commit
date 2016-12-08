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
public abstract class Certificate implements Parcelable {
    @Nullable public abstract String name();
    @Nullable public abstract String authority();
    @Nullable public abstract String certdate();
    @Nullable public abstract String type();
    @Nullable public abstract String rank();
    @Nullable public abstract String id();
    @Nullable public abstract String createflag();
    @Nullable public abstract String updateflag();
    @Nullable public abstract String postflag();
    @Nullable public abstract String putflag();
    @Nullable public abstract String mongoid();
    @Nullable public abstract String date();

    public static Certificate.Builder builder() {
        return new AutoValue_Certificate.Builder();
    }

    public static TypeAdapter<Certificate> typeAdapter(Gson gson) {
        return new AutoValue_Certificate.GsonTypeAdapter(gson);
    }
    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Certificate.Builder setName(String name);
        public abstract Certificate.Builder setAuthority(String authority);
        public abstract Certificate.Builder setCertdate(String certdate);
        public abstract Certificate.Builder setType(String type);
        public abstract Certificate.Builder setRank(String rank);
        public abstract Certificate.Builder setId(String id);
        public abstract Certificate.Builder setCreateflag(String createflag);
        public abstract Certificate.Builder setUpdateflag(String updateflag);
        public abstract Certificate.Builder setPostflag(String postflag);
        public abstract Certificate.Builder setPutflag(String putflag);
        public abstract Certificate.Builder setMongoid(String mongoid);
        public abstract Certificate.Builder setDate(String date);
        public abstract Certificate build();

    }
}

