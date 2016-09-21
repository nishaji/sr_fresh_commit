package com.example.sprydev5.skillsregister.model;

/**
 * Created by sprydev5 on 26/8/16.
 */
public class Contact {
    private String contact,category,type,id;

  public  Contact(){

    }
public Contact(String contact,String category,String type,String id){
    this.contact=contact;
    this.category=category;
    this.type=type;
    this.id=id;

}
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
