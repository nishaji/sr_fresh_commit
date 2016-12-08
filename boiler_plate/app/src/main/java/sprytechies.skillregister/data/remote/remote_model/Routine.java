package sprytechies.skillregister.data.remote.remote_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sprydev5 on 3/12/16.
 */

public class Routine implements Serializable {
    @SerializedName("activity")
    private String activity;
    @SerializedName("time")
    private String time;
    public Routine(String activity,String time){
        this.activity=activity;
        this.time=time;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
