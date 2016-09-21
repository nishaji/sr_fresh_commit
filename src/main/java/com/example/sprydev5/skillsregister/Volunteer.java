package com.example.sprydev5.skillsregister;

/**
 * Created by sprydev5 on 15/9/16.
 */
public class Volunteer {
    private String role,organisation,description,id;

    public  Volunteer(){

    }
    public Volunteer(String role,String organisation,String description,String id){
        this.role=role;
        this.organisation=organisation;
        this.description=description;
        this.id=id;

    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
