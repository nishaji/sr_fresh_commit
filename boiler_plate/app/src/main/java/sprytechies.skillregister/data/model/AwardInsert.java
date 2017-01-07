package sprytechies.skillregister.data.model;


import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by sprydev5 on 5/10/16.
 */
@AutoValue
public abstract class AwardInsert implements Comparable<AwardInsert>, Parcelable {

    public abstract Award award();

    public static AwardInsert create(Award award) {
        return new AutoValue_AwardInsert(award);
    }

    public static TypeAdapter<AwardInsert> typeAdapter(Gson gson) {
        return new AutoValue_AwardInsert.GsonTypeAdapter(gson);
    }

    @Override
    public int compareTo(@NonNull AwardInsert another) {
        return award().title().compareToIgnoreCase(another.award().title());
    }
}
