package sprytechies.skillregister.data.remote.remote_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sprydev5 on 15/11/16.
 */

public class Acc implements Serializable {
    @SerializedName("accessId")
    private String accessId;
    @SerializedName("accessCode")
    private String accessCode;
    @SerializedName("privacy")
    private String privacy;
    @SerializedName("syncCanReplace")
    private String syncCanReplace;
    @SerializedName("fb")
    private String fb;
    @SerializedName("gplus")
    private String gplus;
    @SerializedName("in")
    private String in;

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getSyncCanReplace() {
        return syncCanReplace;
    }

    public void setSyncCanReplace(String syncCanReplace) {
        this.syncCanReplace = syncCanReplace;
    }

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb = fb;
    }

    public String getGplus() {
        return gplus;
    }

    public void setGplus(String gplus) {
        this.gplus = gplus;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }
}
