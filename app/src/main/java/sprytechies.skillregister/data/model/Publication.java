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
public abstract class Publication implements Parcelable {
    @Nullable public abstract String title();
    @Nullable public abstract String publishers();
    @Nullable public abstract String authors();
    @Nullable public abstract String url();
    @Nullable public abstract String description();
    @Nullable public abstract String date();
    @Nullable public abstract String id();
    @Nullable public abstract String createflag();
    @Nullable public abstract String updateflag();
    @Nullable public abstract String postflag();
    @Nullable public abstract String putflag();
    @Nullable public abstract String mongoid();
    @Nullable public abstract String datetime();

    public static Publication.Builder builder() {
        return new AutoValue_Publication.Builder();
    }


    public static TypeAdapter<Publication> typeAdapter(Gson gson) {
        return new AutoValue_Publication.GsonTypeAdapter(gson);
    }
    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Publication.Builder setTitle(String title);
        public abstract Publication.Builder setPublishers(String publishers);
        public abstract Publication.Builder setAuthors(String authors);
        public abstract Publication.Builder setDescription(String description);
        public abstract Publication.Builder setUrl(String url);
        public abstract Publication.Builder setDate(String date);
        public abstract Publication.Builder setId(String id);
        public abstract Publication.Builder setCreateflag(String createflag);
        public abstract Publication.Builder setUpdateflag(String updateflag);
        public abstract Publication.Builder setPostflag(String postflag);
        public abstract Publication.Builder setPutflag(String putflag);
        public abstract Publication.Builder setMongoid(String mongoid);
        public abstract Publication.Builder setDatetime(String datetime);
        public abstract Publication build();

    }
}
