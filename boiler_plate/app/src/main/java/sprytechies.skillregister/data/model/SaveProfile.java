package sprytechies.skillregister.data.model;


import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;



/**
 * Created by sprydev5 on 21/11/16.
 */
@AutoValue
public abstract class SaveProfile implements Parcelable {
    @Nullable public abstract String type();
    @Nullable public abstract String meta();
    @Nullable public abstract String skill();
    @Nullable  public abstract String lan();
    @Nullable  public abstract String id();

    public static SaveProfile.Builder builder() {
        return new AutoValue_SaveProfile.Builder();
    }


    public static TypeAdapter<SaveProfile> typeAdapter(Gson gson) {
        return new AutoValue_SaveProfile.GsonTypeAdapter(gson);
    }
    @AutoValue.Builder
    public abstract static class Builder {
        public abstract SaveProfile.Builder setType(String type);
        public abstract SaveProfile.Builder setMeta(String meta);
        public abstract SaveProfile.Builder setskill(String skill);
        public abstract SaveProfile.Builder setLan(String lan);
        public abstract SaveProfile.Builder setId(String id);
        public abstract SaveProfile build();

    }
}
