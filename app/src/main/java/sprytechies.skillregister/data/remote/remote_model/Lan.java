package sprytechies.skillregister.data.remote.remote_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sprydev5 on 21/11/16.
 */

public class Lan implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("level")
    private String level;
    public Lan(String name,String level){
        this.name=name;
        this.level=level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
