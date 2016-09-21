package com.example.sprydev5.skillsregister.model;

/**
 * Created by sprydev5 on 28/7/16.
 */
public class Workexperience {

    private String companyname,companytitle, location,id;

    public Workexperience() {
    }

    public Workexperience(String companyname, String companytitle,String location,String id) {
        this.companyname = companyname;
        this.companytitle=companytitle;
        this.location = location;
        this.id=id;

    }


    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCompanytitle() {
        return companytitle;
    }

    public void setCompanytitle(String companytitle) {
        this.companytitle = companytitle;
    }





    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getLocation() {
        return location;
    }


}
