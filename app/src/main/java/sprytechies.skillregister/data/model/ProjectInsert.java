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
public abstract class ProjectInsert implements Comparable<ProjectInsert>, Parcelable {

    public abstract Project project();

    public static ProjectInsert create(Project project) {
        return new AutoValue_ProjectInsert(project);
    }

    public static TypeAdapter<ProjectInsert> typeAdapter(Gson gson) {
        return new AutoValue_ProjectInsert.GsonTypeAdapter(gson);
    }

    @Override
    public int compareTo(@NonNull ProjectInsert another) {
        return project().project().compareToIgnoreCase(another.project().project());
    }
}

