package sprytechies.skillregister.data.remote.remote_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sprydev5 on 21/11/16.
 */

public class GetProfile implements Serializable {
    @SerializedName("type") private String type;
    @SerializedName("meta") private Meta meta;
    @SerializedName("skills") private ArrayList<skill> skill;
    @SerializedName("language") private ArrayList<Lan> language;

    public GetProfile(String type,Meta meta,ArrayList<skill> skill,ArrayList<Lan> lan){
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

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public ArrayList<skill> getSkill() {
        return skill;
    }

    public void setSkill(ArrayList<skill> skill) {
        this.skill = skill;
    }

    public ArrayList<Lan> getLanguage() {
        return language;
    }

    public void setLanguage(ArrayList<Lan> language) {
        this.language = language;
    }

}
