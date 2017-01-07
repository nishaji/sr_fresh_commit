package sprytechies.skillregister.data.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by sprydev5 on 5/12/16.
 */
@AutoValue
public abstract class LiveSyncinsert implements Comparable<LiveSyncinsert>, Parcelable {
    public abstract LiveSync liveSync();

    public static LiveSyncinsert create(LiveSync liveSync) {
        return new AutoValue_LiveSyncinsert(liveSync);
    }

    public static TypeAdapter<LiveSyncinsert> typeAdapter(Gson gson) {
        return new AutoValue_LiveSyncinsert.GsonTypeAdapter(gson);
    }

    @Override
    public int compareTo(@NonNull LiveSyncinsert another) {
        return liveSync().bit().compareToIgnoreCase(another.liveSync().bit());
    }
}
