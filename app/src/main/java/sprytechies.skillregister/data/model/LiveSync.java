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
    @Nullable public abstract String bitbefore();
    @Nullable public abstract String bitafter();
    @Nullable public abstract String syncstatus();
    @Nullable public abstract String bitid();
    @Nullable public abstract String bitmongoid();
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
        public abstract LiveSync.Builder setBitbefore(String bitbefore);
        public abstract LiveSync.Builder setBitafter(String bitafter);
        public abstract LiveSync.Builder setSyncstatus(String syncstatus);
        public abstract LiveSync.Builder setBitid(String bitid);
        public abstract LiveSync.Builder setBitmongoid(String bitmongoid);

        public abstract LiveSync build();


    }

}
