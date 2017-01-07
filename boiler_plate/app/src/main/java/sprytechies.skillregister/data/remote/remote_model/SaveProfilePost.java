package sprytechies.skillregister.data.remote.remote_model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;

import sprytechies.skillregister.data.model.SaveProfileInsert;

/**
 * Created by sprydev5 on 2/12/16.
 */

public class SaveProfilePost implements Serializable {
    @SerializedName("type")
    private String type;
    @SerializedName("meta")
    private Meta meta;
    @SerializedName("skills")
    private ArrayList<skill> skill;
    @SerializedName("language")
    private ArrayList<Lan> language;
    public SaveProfilePost(SaveProfileInsert saveProfileInsert){
        String books="";
        String passion="";
        String strength="";
        String routine="";
        try {
            JSONObject jsonObject=new JSONObject(saveProfileInsert.saveProfile().meta());
            books = jsonObject.getString("books");
            passion = jsonObject.getString("passion");
            strength = jsonObject.getString("strength");
            routine = jsonObject.getString("routine");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int indexOfOpenBracket = books.indexOf("[");
        int indexOfLastBracket = books.lastIndexOf("]");
        String book = books.substring(indexOfOpenBracket + 1, indexOfLastBracket);
        String  book_arry[] = book.split(",");
        ArrayList<String> book_list = new ArrayList<String>();
        for (int j = 0; j < book_arry.length; j++) {
            String ab = book_arry[j];
            book_list.add(ab.trim());
        }
        int indexOfOpenBracket1 = strength.indexOf("[");
        int indexOfLastBracket1 = strength.lastIndexOf("]");
        String st = strength.substring(indexOfOpenBracket1 + 1, indexOfLastBracket1);
        String  strength_arry[] = st.split(",");
        ArrayList<String> strength_list = new ArrayList<String>();
        for (int j = 0; j < strength_arry.length; j++) {
            String ab = strength_arry[j];
            strength_list.add(ab.trim());
        }
        int indexOfOpenBracket2 = passion.indexOf("[");
        int indexOfLastBracket2 = passion.lastIndexOf("]");
        String pa = passion.substring(indexOfOpenBracket2 + 1, indexOfLastBracket2);
        String  passion_array[] = pa.split(",");
        ArrayList<String> passion_list = new ArrayList<String>();
        for (int j = 0; j < passion_array.length; j++) {
            String ab = passion_array[j];
            System.out.println("passion"+ab);
            passion_list.add(ab.trim());
        }
        ArrayList<Routine>routines_list=new ArrayList<>();
        try {
            JSONArray jsonarray = new JSONArray(routine);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String activity = jsonobject.getString("activity");
                String time = jsonobject.getString("time");
                routines_list.add(new Routine(activity,time));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
       ArrayList<skill> skills=new ArrayList<>();
        try {
            JSONArray jsonarray = new JSONArray(saveProfileInsert.saveProfile().skill());
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String codee = jsonobject.getString("code");
                String level = jsonobject.getString("level");
                String type = jsonobject.getString("type");
                System.out.println("skill"+codee+level+type);
                skills.add(new skill(codee,type,level));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<Lan> languages=new ArrayList<>();
        try {
            JSONArray jsonarray = new JSONArray(saveProfileInsert.saveProfile().lan());
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String codee = jsonobject.getString("name");
                String level = jsonobject.getString("level");
                languages.add(new Lan(codee,level));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.type=saveProfileInsert.saveProfile().type();
        this.meta=new Meta(book_list,strength_list,passion_list,routines_list);
        this.skill=skills;
        this.language=languages;

    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public ArrayList<skill> getSkill() {
        return skill;
    }

    public void setSkill(ArrayList<skill> skill) {
        this.skill = skill;
    }

    public ArrayList<Lan> getLanguage() {
        return language;
    }

    public void setLanguage(ArrayList<Lan> language) {
        this.language = language;
    }
}



