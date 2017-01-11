package sprytechies.skillregister.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sprydev5 on 11/1/17.
 */

public class Meta implements Serializable {
    @SerializedName("responsibility")
    private String responsibility;
    @SerializedName("achievements")
    private String achievements;
    @SerializedName("desc")
    private String desc;

    public Meta(String responsibility,String achievements,String desc){
        this.responsibility=responsibility;
        this.achievements=achievements;
        this.desc=desc;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
