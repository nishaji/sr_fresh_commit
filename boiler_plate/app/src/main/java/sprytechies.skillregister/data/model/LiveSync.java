package sprytechies.skillregister.data.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by sprydev5 on 5/12/16.
 */
@AutoValue
public abstract class LiveSync implements Parcelable {
    @Nullable public abstract String id();
    @Nullable public abstract String bit();
    @Nullable public abstract String post();
    @Nullable public abstract String put();
    public static LiveSync.Builder builder() {
        return new AutoValue_LiveSync.Builder();
    }

    public static TypeAdapter<LiveSync> typeAdapter(Gson gson) {
        return new AutoValue_LiveSync.GsonTypeAdapter(gson);
    }
    @AutoValue.Builder
    public abstract static class Builder {
        public abstract LiveSync.Builder setId(String id);
        public abstract LiveSync.Builder setBit(String bit);
        public abstract LiveSync.Builder setPost(String post);
        public abstract LiveSync.Builder setPut(String put);
        public abstract LiveSync build();


    }

}
