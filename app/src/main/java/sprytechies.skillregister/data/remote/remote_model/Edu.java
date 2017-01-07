package sprytechies.skillregister.data.remote.remote_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import sprytechies.skillregister.data.model.EducationInsert;
import sprytechies.skillregister.data.model.Location;


/**
 * Created by sprydev5 on 27/10/16.
 */

public class Edu implements Serializable {

    @SerializedName("school")
    private String school;
    @SerializedName("from")
    private Date from;
    @SerializedName("upto")
    private Date upto;
    @SerializedName("course")
    private String course;
    @SerializedName("type")
    private String type;
    @SerializedName("edutype")
    private String edutype;
    @SerializedName("cgpi")
    private String cgpi;
    @SerializedName("cgpitype")
    private String cgpitype;
    @SerializedName("location")
    private Location location;
    @SerializedName("title")
    private String title;
    @SerializedName("id")
    private String id;

    public Edu(EducationInsert education) {
        Date date=new Date(education.education().from());
        Date date1=new Date(education.education().upto());
        this.school = education.education().school();
        this.from = date;
        this.upto=date1;
        this.course=education.education().course();
        this.type=education.education().schooltype();
        this.edutype=education.education().edutype();
        this.cgpi=education.education().cgpi();
        this.edutype=education.education().edutype();
        this.location=education.education().location();
        this.title=education.education().title();

    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEdutype() {
        return edutype;
    }

    public void setEdutype(String edutype) {
        this.edutype = edutype;
    }

    public String getCgpi() {
        return cgpi;
    }

    public void setCgpi(String cgpi) {
        this.cgpi = cgpi;
    }

    public String getCgpitype() {
        return cgpitype;
    }

    public void setCgpitype(String cgpitype) {
        this.cgpitype = cgpitype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

