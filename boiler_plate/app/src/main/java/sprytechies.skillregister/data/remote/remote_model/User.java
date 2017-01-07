package sprytechies.skillregister.data.remote.remote_model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

import sprytechies.skillregister.data.model.SignUp;


/**
 * Created by sprydev5 on 13/10/16.
 */

public class User implements Serializable{

    @SerializedName("fname")
    private String fname;

    @SerializedName("lname")
    private String lname;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public User(SignUp signUp) {
        this.fname = signUp.first_name();
        this.lname = signUp.last_name();
        this.email=signUp.email();
        this.password=signUp.password();
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
