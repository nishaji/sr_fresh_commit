package sprytechies.skillregister.data.remote.remote_model;

/**
 * Created by sprydev5 on 29/11/16.
 */

public class SkillSugession {
    String name;
    public SkillSugession(String name){
        this.setItem(name);

    }

    public String getItem() {
        return name;
    }

    public void setItem(String id) {
        this.name = id;
    }
}
