package sprytechies.skillregister.data.model;


import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by sprydev5 on 22/10/16.
 */
@AutoValue
public abstract class PublicationInsert implements Comparable<PublicationInsert>, Parcelable {
    public abstract Publication publication();

    public static PublicationInsert create(Publication publication) {
        return new AutoValue_PublicationInsert(publication);
    }

    public static TypeAdapter<PublicationInsert> typeAdapter(Gson gson) {
        return new AutoValue_PublicationInsert.GsonTypeAdapter(gson);
    }

    @Override
    public int compareTo(@NonNull PublicationInsert another) {
        return publication().title().compareToIgnoreCase(another.publication().title());
    }
}
