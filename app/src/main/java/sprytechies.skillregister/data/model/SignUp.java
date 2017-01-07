package sprytechies.skillregister.data.model;


import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by sprydev5 on 12/10/16.
 */
@AutoValue
public abstract class SignUp implements Parcelable {
  @Nullable  public abstract String first_name();
   @Nullable public abstract String last_name();
    public abstract String email();
    public abstract String password();

    public static SignUp.Builder builder() {
        return new AutoValue_SignUp.Builder();
    }

    public static TypeAdapter<SignUp> typeAdapter(Gson gson) {
        return new AutoValue_SignUp.GsonTypeAdapter(gson);
    }
    @AutoValue.Builder
    public abstract static class Builder {
        public abstract SignUp.Builder setFirst_name(String firstname);
        public abstract SignUp.Builder setLast_name(String lastname);
        public abstract SignUp.Builder setEmail(String email);
        public abstract SignUp.Builder setPassword(String password);
        public abstract SignUp build();    }
}
