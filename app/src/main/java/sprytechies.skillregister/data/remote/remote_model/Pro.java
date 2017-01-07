package sprytechies.skillregister.data.remote.remote_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import sprytechies.skillregister.data.model.ProjectInsert;


/**
 * Created by sprydev5 on 4/11/16.
 */

public class Pro implements Serializable {
    @SerializedName("project")
    private String project;
    @SerializedName("from")
    private Date from;
    @SerializedName("upto")
    private Date upto;
    @SerializedName("role")
    private String role;
    @SerializedName("id")
    private String id;

    public Pro(ProjectInsert project){
        Date date=new Date(project.project().from());
        Date date1=new Date(project.project().upto());
        this.project=project.project().project();
        this.from=date;
        this.upto=date1;
        this.role=project.project().role();

   }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getUpto() {
        return upto;
    }

    public void setUpto(Date upto) {
        this.upto = upto;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
