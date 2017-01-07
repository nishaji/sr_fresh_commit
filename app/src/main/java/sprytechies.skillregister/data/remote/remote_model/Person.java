package sprytechies.skillregister.data.remote.remote_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sprydev5 on 15/11/16.
 */

public class Person implements Serializable {
    @SerializedName("account")
    private Acc acc;
    @SerializedName("permissions")
    private ArrayList<Permi> permission;

    public Person(Acc acc,ArrayList<Permi>permission)
    {
        this.acc=acc;
        this.permission=permission;
    }

    public Acc getAcc() {
        return acc;
    }

    public void setAcc(Acc acc) {
        this.acc = acc;
    }

    public ArrayList<Permi> getPermission() {
        return permission;
    }

    public void setPermission(ArrayList<Permi> permission) {
        this.permission = permission;
    }
}
