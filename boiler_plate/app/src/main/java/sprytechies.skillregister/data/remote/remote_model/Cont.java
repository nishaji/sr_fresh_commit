package sprytechies.skillregister.data.remote.remote_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import sprytechies.skillregister.data.model.ContactInsert;


/**
 * Created by sprydev5 on 4/11/16.
 */

public class Cont implements Serializable {
    @SerializedName("contact")
    private String contact;
    @SerializedName("category")
    private String category;
    @SerializedName("status")
    private String status;
    @SerializedName("type")
    private String type;
    @SerializedName("id")
    private String id;

    public Cont(ContactInsert contact){
        this.contact=contact.contact().contact();
        this.category=contact.contact().category();
        this.status=contact.contact().status();
        this.type=contact.contact().type();

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
