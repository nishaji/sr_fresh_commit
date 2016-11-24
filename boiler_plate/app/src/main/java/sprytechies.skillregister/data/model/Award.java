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
public abstract class Award implements Parcelable{
    @Nullable public abstract String title();
    @Nullable public abstract String organisation();
    @Nullable public abstract String description();
    @Nullable  public abstract String duration();
    @Nullable public abstract String id();
    @Nullable public abstract String createflag();
    @Nullable public abstract String updateflag();
    @Nullable public abstract String postflag();
    @Nullable public abstract String putflag();
    @Nullable public abstract String mongoid();
    @Nullable public abstract String date();

    public static Award.Builder builder() {
        return new AutoValue_Award.Builder();
    }

    public static TypeAdapter<Award> typeAdapter(Gson gson) {
        return new AutoValue_Award.GsonTypeAdapter(gson);
    }
    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Award.Builder setTitle(String title);
        public abstract Award.Builder setOrganisation(String organisation);
        public abstract Award.Builder setDescription(String description);
        public abstract Award.Builder setDuration(String duration);
        public abstract Award.Builder setId(String id);
        public abstract Award.Builder setCreateflag(String createflag);
        public abstract Award.Builder setUpdateflag(String updateflag);
        public abstract Award.Builder setPostflag(String postflag);
        public abstract Award.Builder setPutflag(String putflag);
        public abstract Award.Builder setMongoid(String mongoid);
        public abstract Award.Builder setDate(String date);
        public abstract Award build();

    }
}
