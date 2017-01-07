package sprytechies.skillregister.data.remote.remote_model;

/**
 * Created by sprydev5 on 6/11/16.
 */

public class CompanySugession {
    String item;
    public CompanySugession(String item){
        this.setItem(item);

    }

    public String getItem() {
        return item;
    }

    public void setItem(String id) {
        this.item = id;
    }
}


