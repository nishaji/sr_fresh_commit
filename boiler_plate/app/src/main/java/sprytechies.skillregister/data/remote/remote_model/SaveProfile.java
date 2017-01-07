package sprytechies.skillregister.data.remote.remote_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sprydev5 on 22/11/16.
 */

public class SaveProfile implements Serializable {
    @SerializedName("type")
    private String type;
    @SerializedName("meta")
    private ArrayList<String> meta;
    @SerializedName("skills")
    private String skill;
    @SerializedName("language")
    private String language;
    public SaveProfile(String type,ArrayList<String> meta,String skill,String lan){
        this.type=type;
        this.meta=meta;
        this.skill=skill;
        this.language=lan;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public ArrayList<String> getMeta() {
        return meta;
    }

    public void setMeta(ArrayList<String> meta) {
        this.meta = meta;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}


