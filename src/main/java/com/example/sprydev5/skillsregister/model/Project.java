package com.example.sprydev5.skillsregister.model;

/**
 * Created by sprydev5 on 29/7/16.
 */
public class Project {
   private String projectname,role,responsibility,achievement,duration,id;

    public Project() {
    }

    public Project(String projectname,String role, String responsibility,String id) {
        this.projectname = projectname;
        this.role=role;
        this.responsibility = responsibility;
        this.id=id;

    }


    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
