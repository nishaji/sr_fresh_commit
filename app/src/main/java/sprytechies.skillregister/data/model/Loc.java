package sprytechies.skillregister.data.model;


import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by sprydev5 on 8/11/16.
 */
@AutoValue
public abstract class Loc implements Parcelable{
    public abstract String name();
    public abstract String type();

    public static Loc create(String name, String type) {
        return new AutoValue_Loc(name, type);
    }

    public static TypeAdapter<Loc> typeAdapter(Gson gson) {
        return new AutoValue_Loc.GsonTypeAdapter(gson);
    }
}
