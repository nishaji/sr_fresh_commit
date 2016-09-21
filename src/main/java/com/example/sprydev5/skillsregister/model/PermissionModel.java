package com.example.sprydev5.skillsregister.model;

/**
 * Created by sprydev5 on 14/9/16.
 */
public class PermissionModel {
    public String name;

    public static final String[] data = {"Education", "Work Experience", "Projects",
            "Certificates", "Contacts", "Profile"};

    PermissionModel(String name){
        this.name=name;
    }
}
