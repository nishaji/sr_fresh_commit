package sprytechies.skillregister.data.remote.remote_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sprydev5 on 15/11/16.
 */

public class Permi implements Serializable {

    @SerializedName("privacy")
    private String privacy;
    @SerializedName("code")
    private String code;

    public Permi(String privacy,String code){
        this.privacy=privacy;
        this.code=code;
    }
    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
