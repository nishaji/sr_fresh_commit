package com.example.sprydev5.skillsregister.model;

/**
 * Created by sprydev5 on 29/7/16.
 */
public class Skill {

    private String title,description,id;

    public Skill(){

    }
    public Skill(String title,String description,String id){
        this.title=title;
        this.description=description;
        this.id=id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
