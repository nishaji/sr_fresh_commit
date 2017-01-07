package sprytechies.skillregister.data.remote.remote_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import sprytechies.skillregister.data.model.volunteerInsert;


/**
 * Created by sprydev5 on 9/11/16.
 */

public class Volun implements Serializable {
    @SerializedName("role")
    private String role;
    @SerializedName("type")
    private String type;
    @SerializedName("organization")
    private String organization;
    @SerializedName("from")
    private Date from;
    @SerializedName("upto")
    private Date upto;
    @SerializedName("desc")
    private String desc;
    @SerializedName("id")
    private String id;

    public Volun(volunteerInsert volunteer){
        Date from=new Date(volunteer.volunteer().from());
        Date upto=new Date(volunteer.volunteer().upto());
        this.role=volunteer.volunteer().role();
        this.type=volunteer.volunteer().type();
        this.organization=volunteer.volunteer().organisation();
        this.from=from;
        this.upto=upto;
        this.desc=volunteer.volunteer().description();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getUpto() {
        return upto;
    }

    public void setUpto(Date upto) {
        this.upto = upto;
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
