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
public abstract class ContactInsert implements Comparable<ContactInsert>, Parcelable {

    public abstract Contact contact();

    public static ContactInsert create(Contact contact) {
        return new AutoValue_ContactInsert(contact);
    }

    public static TypeAdapter<ContactInsert> typeAdapter(Gson gson) {
        return new AutoValue_ContactInsert.GsonTypeAdapter(gson);
    }

    @Override
    public int compareTo(@NonNull ContactInsert another) {
        return contact().contact().compareToIgnoreCase(another.contact().contact());
    }
}

