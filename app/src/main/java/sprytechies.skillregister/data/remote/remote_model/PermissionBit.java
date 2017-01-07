package sprytechies.skillregister.data.remote.remote_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sprydev5 on 19/11/16.
 */

public class PermissionBit implements Serializable {
    @SerializedName("permissions")
    private ArrayList<Permi> permission;
public PermissionBit(ArrayList<Permi>permission){
    this.permission=permission;
}
    public ArrayList<Permi> getPermission() {
        return permission;
    }

    public void setPermission(ArrayList<Permi> permission) {
        this.permission = permission;
    }
}
