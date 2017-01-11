package sprytechies.skillregister.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sprydev5 on 11/1/17.
 */

public class EduMeta implements Serializable {
    @SerializedName("desc")
    private String desc;
    public EduMeta(String desc){
        this.desc=desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
