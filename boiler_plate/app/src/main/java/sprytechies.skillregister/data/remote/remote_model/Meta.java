package sprytechies.skillregister.data.remote.remote_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sprydev5 on 21/11/16.
 */

public class Meta implements Serializable {
    @SerializedName("books")
    private ArrayList<String> books;
    @SerializedName("passion")
    private ArrayList<String> passion;
    @SerializedName("strength")
    private ArrayList<String> strength;
    public Meta(ArrayList<String> books,ArrayList<String> passion,ArrayList<String> strength){
        this.books=books;
        this.passion=passion;
        this.strength=strength;
    }

    public ArrayList<String> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<String> books) {
        this.books = books;
    }

    public ArrayList<String> getPassion() {
        return passion;
    }

    public void setPassion(ArrayList<String> passion) {
        this.passion = passion;
    }

    public ArrayList<String> getStrength() {
        return strength;
    }

    public void setStrength(ArrayList<String> strength) {
        this.strength = strength;
    }
}
