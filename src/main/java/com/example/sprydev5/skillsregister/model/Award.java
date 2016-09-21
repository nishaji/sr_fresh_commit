package com.example.sprydev5.skillsregister.model;

/**
 * Created by sprydev5 on 16/9/16.
 */
public class Award {
    private String title,organisation,description,id;

    public  Award(){

    }
    public Award(String title,String organisation,String description,String id){
        this.title=title;
        this.organisation=organisation;
        this.description=description;
        this.id=id;

    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
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
