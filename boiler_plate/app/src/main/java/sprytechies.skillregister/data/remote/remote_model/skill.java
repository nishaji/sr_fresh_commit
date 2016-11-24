package sprytechies.skillregister.data.remote.remote_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sprydev5 on 21/11/16.
 */

public class skill implements Serializable {
    @SerializedName("code")
    private String code;
    @SerializedName("type")
    private String type;
    @SerializedName("level")
    private String level;
    public skill(String code,String type,String level){
        this.code=code;
        this.type=type;
        this.level=level;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
