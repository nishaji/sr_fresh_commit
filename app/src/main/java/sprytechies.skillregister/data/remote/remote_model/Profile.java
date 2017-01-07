package sprytechies.skillregister.data.remote.remote_model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sprydev5 on 27/10/16.
 */

public class Profile implements Serializable{
    @SerializedName("fname")
   private String fname;
    @SerializedName("lname")
    private String lname;
    @SerializedName("id")
    private String id;
    @SerializedName("userId")
    private String userId;
    @SerializedName("pic")
    private String pic;
    @SerializedName("account")
    private Acc account;
    @SerializedName("permissions")
    private List<JSONObject> permissions;
    public Profile(Acc jsonObject, List<JSONObject> js){
        this.account=jsonObject;
        this.permissions=js;
    }
    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }



    public List<JSONObject> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<JSONObject> permissions) {
        this.permissions = permissions;
    }

    public Acc getAccount() {
        return account;
    }

    public void setAccount(Acc account) {
        this.account = account;
    }
}
