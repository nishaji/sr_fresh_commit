package sprytechies.skillregister.data.remote.remote_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import sprytechies.skillregister.data.model.CertificateInsert;


/**
 * Created by sprydev5 on 4/11/16.
 */

public class Cert implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private String type;
    @SerializedName("certdate")
    private Date certdate;
    @SerializedName("authority")
    private String authority;
    @SerializedName("rank")
    private String rank;
    @SerializedName("id")
    private String id;

    public Cert(CertificateInsert cert){
        Date date=new Date(cert.certificate().certdate());
        this.name=cert.certificate().name();
        this.type=cert.certificate().type();
        this.certdate=date;
        this.authority=cert.certificate().authority();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCertdate() {
        return certdate;
    }

    public void setCertdate(Date certdate) {
        this.certdate = certdate;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
