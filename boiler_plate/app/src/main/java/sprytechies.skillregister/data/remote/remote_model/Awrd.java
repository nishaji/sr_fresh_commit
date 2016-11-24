package sprytechies.skillregister.data.remote.remote_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import sprytechies.skillregister.data.model.AwardInsert;


/**
 * Created by sprydev5 on 4/11/16.
 */

public class Awrd implements Serializable {
    @SerializedName("title")
    private String title;
    @SerializedName("organization")
    private String org;
    @SerializedName("date")
    private Date date;
    @SerializedName("desc")
    private String desc;
    @SerializedName("id")
    private String id;

    public Awrd(AwardInsert award){
        Date date=new Date(award.award().duration());
        this.title=award.award().title();
        this.org=award.award().organisation();
        this.date=date;
        this.desc=award.award().description();

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
