package com.example.sprydev5.skillsregister.model;

/**
 * Created by sprydev5 on 6/8/16.
 */
public class Edu {
    private String schoolname,id,course,location,mongoid;

    public Edu() {
    }

    public Edu(String schoolname,String course,String location,String id,String mongoid) {
        this.schoolname = schoolname;
        this.id = id;
        this.course=course;
        this.location=location;
        this.mongoid=mongoid;

    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }





    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }



    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getMongoid() {
        return mongoid;
    }

    public void setMongoid(String mongoid) {
        this.mongoid = mongoid;
    }
}
