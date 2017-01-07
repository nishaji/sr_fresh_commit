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
public abstract class Contact implements Parcelable {

    @Nullable public abstract String contact();
    @Nullable public abstract String category();
    @Nullable public abstract String type();
    @Nullable public abstract String status();
    @Nullable public abstract String id();
    @Nullable public abstract String createflag();
    @Nullable public abstract String updateflag();
    @Nullable public abstract String postflag();
    @Nullable public abstract String putflag();
    @Nullable public abstract String mongoid();
    @Nullable public abstract String date();


    public static Contact.Builder builder() {
        return new AutoValue_Contact.Builder();
    }

    public static TypeAdapter<Contact> typeAdapter(Gson gson) {
        return new AutoValue_Contact.GsonTypeAdapter(gson);
    }
    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Contact.Builder setContact(String contact);
        public abstract Contact.Builder setCategory(String category);
        public abstract Contact.Builder setType(String type);
        public abstract Contact.Builder setStatus(String status);
        public abstract Contact.Builder setId(String id);
        public abstract Contact.Builder setCreateflag(String createflag);
        public abstract Contact.Builder setUpdateflag(String updateflag);
        public abstract Contact.Builder setPostflag(String postflag);
        public abstract Contact.Builder setPutflag(String putflag);
        public abstract Contact.Builder setMongoid(String mongoid);
        public abstract Contact.Builder setDate(String date);
        public abstract Contact build();

    }

}
