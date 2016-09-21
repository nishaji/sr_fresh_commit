package com.example.sprydev5.skillsregister.model;

/**
 * Created by sprydev5 on 26/8/16.
 */
public class Certificate {

    private String certname, certstatus, certtype,id;

    public Certificate() {

    }

    public Certificate(String certname,String certstatus,String certtype ,String id){
        this.certname=certname;
        this.certstatus=certstatus;
        this.certtype=certtype;
        this.id=id;

    }
    public String getCertname() {
        return certname;
    }

    public void setCertname(String certname) {
        this.certname = certname;
    }

    public String getCertstatus() {
        return certstatus;
    }

    public void setCertstatus(String certstatus) {
        this.certstatus = certstatus;
    }

    public String getCerttype() {
        return certtype;
    }

    public void setCerttype(String certtype) {
        this.certtype = certtype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
