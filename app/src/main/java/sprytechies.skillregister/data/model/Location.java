package sprytechies.skillregister.data.model;



import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sprydev5 on 14/11/16.
 */
public  class Location implements Serializable {
   @SerializedName("name")
    public String name;
    @SerializedName("type")
    public String type;
    public Location(String name,String type){
        this.name=name;
        this.type=type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
