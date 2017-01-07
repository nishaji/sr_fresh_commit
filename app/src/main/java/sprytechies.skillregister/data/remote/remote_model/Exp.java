package sprytechies.skillregister.data.remote.remote_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import sprytechies.skillregister.data.model.ExperienceInsert;
import sprytechies.skillregister.data.model.Location;


/**
 * Created by sprydev5 on 27/10/16.
 */

public class Exp implements Serializable {
    @SerializedName("from") private Date from;
    @SerializedName("upto") private Date upto;
    @SerializedName("status") private String status;
    @SerializedName("type") private String type;
    @SerializedName("title") private String title;
    @SerializedName("company") private String company;
    @SerializedName("job") private String job;
    @SerializedName("location") private Location location;
    @SerializedName("id") private String id;

    public Exp(ExperienceInsert experience){
        Date date=new Date(experience.experience().from());
        Date date1=new Date(experience.experience().upto());
        this.from=date;
        this.upto=date1;
        this.status=experience.experience().status();
        this.type=experience.experience().type();
        this.title=experience.experience().title();
        this.company=experience.experience().company();
        this.job=experience.experience().job();
        this.location=experience.experience().location();
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
