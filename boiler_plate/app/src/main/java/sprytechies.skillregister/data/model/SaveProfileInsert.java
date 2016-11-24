package sprytechies.skillregister.data.model;


import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by sprydev5 on 22/11/16.
 */
@AutoValue
public abstract class SaveProfileInsert implements Comparable<SaveProfileInsert>, Parcelable {

    public abstract SaveProfile saveProfile();

    public static SaveProfileInsert create(SaveProfile project) {
        return new AutoValue_SaveProfileInsert(project);
    }

    public static TypeAdapter<SaveProfileInsert> typeAdapter(Gson gson) {
        return new AutoValue_SaveProfileInsert.GsonTypeAdapter(gson);
    }

    @Override
    public int compareTo(@NonNull SaveProfileInsert another) {
        return saveProfile().type().compareToIgnoreCase(another.saveProfile().type());
    }
}
