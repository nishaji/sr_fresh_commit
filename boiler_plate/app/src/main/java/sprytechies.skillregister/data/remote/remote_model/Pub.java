package sprytechies.skillregister.data.remote.remote_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import sprytechies.skillregister.data.model.PublicationInsert;


/**
 * Created by sprydev5 on 4/11/16.
 */

public class Pub implements Serializable {
    @SerializedName("title")
    private String title;
    @SerializedName("publisher")
    private String publisher;
    @SerializedName("date")
    private Date date;
    @SerializedName("authors")
    private String authors;
    @SerializedName("url")
    private String url;
    @SerializedName("desc")
    private String desc;
    @SerializedName("id")
    private String id;

    public Pub(PublicationInsert pub){
        Date date=new Date(pub.publication().date());
        this.title=pub.publication().title();
        this.publisher=pub.publication().publishers();
        this.date=date;
        this.authors=pub.publication().authors();
        this.url=pub.publication().url();
        this.desc=pub.publication().description();

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
