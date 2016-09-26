package sprytechies.skillregister.model;

/**
 * Created by sprydev5 on 22/9/16.
 */

public class JobSugession {
    String item;
    public JobSugession(String item){
        this.setItem(item);

    }

    public String getItem() {
        return item;
    }

    public void setItem(String id) {
        this.item = id;
    }
}
