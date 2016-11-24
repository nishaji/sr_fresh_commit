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
public abstract class Education implements Parcelable {
    @Nullable public abstract String school();
    @Nullable public abstract String from();
    @Nullable public abstract String upto();
    @Nullable public abstract String course();
    @Nullable public abstract String schooltype();
    @Nullable public abstract String title();
    @Nullable public abstract String edutype();
    @Nullable public abstract String cgpi();
    @Nullable public abstract String cgpitype();
    @Nullable public abstract Location location();
    @Nullable public abstract String id();
    @Nullable public abstract String createflag();
    @Nullable public abstract String updateflag();
    @Nullable public abstract String postflag();
    @Nullable public abstract String putflag();
    @Nullable public abstract String mongoid();
    @Nullable public abstract String date();



    public static Education.Builder builder() {
        return new AutoValue_Education.Builder();
    }

    public static TypeAdapter<Education> typeAdapter(Gson gson) {
        return new AutoValue_Education.GsonTypeAdapter(gson);
    }
    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Education.Builder setSchool(String school);
        public abstract Education.Builder setCourse(String course);
        public abstract Education.Builder setLocation(Location location);
        public abstract Education.Builder setFrom(String from);
        public abstract Education.Builder setUpto(String upto);
        public abstract Education.Builder setSchooltype(String schooltype);
        public abstract Education.Builder setTitle(String title);
        public abstract Education.Builder setEdutype(String edutype);
        public abstract Education.Builder setCgpi(String cgpi);
        public abstract Education.Builder setCgpitype(String cgpitype);
        public abstract Education.Builder setId(String id);
        public abstract Education.Builder setCreateflag(String createflag);
        public abstract Education.Builder setUpdateflag(String updateflag);
        public abstract Education.Builder setPostflag(String postflag);
        public abstract Education.Builder setPutflag(String putflag);
        public abstract Education.Builder setMongoid(String mongoid);
        public abstract Education.Builder setDate(String date);
        public abstract Education build();

    }
}
