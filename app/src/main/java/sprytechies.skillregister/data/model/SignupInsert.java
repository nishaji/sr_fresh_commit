package sprytechies.skillregister.data.model;


import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by sprydev5 on 12/10/16.
 */
@AutoValue
public abstract class SignupInsert implements Comparable<SignupInsert>, Parcelable {

    public abstract SignUp signUp();

    public static SignupInsert create(SignUp signUp) {
        return new AutoValue_SignupInsert(signUp);
    }

    public static TypeAdapter<SignupInsert> typeAdapter(Gson gson) {
        return new AutoValue_SignupInsert.GsonTypeAdapter(gson);
    }

    @Override
    public int compareTo(@NonNull SignupInsert another) {
        return signUp().first_name().compareToIgnoreCase(another.signUp().last_name());
    }
}