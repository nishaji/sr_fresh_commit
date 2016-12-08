package sprytechies.skillregister.data.remote;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import sprytechies.skillregister.data.remote.remote_model.CompanySugession;
import sprytechies.skillregister.data.remote.remote_model.CourseSugeeion;
import sprytechies.skillregister.data.remote.remote_model.JobSugession;
import sprytechies.skillregister.data.remote.remote_model.SchoolSugession;
import sprytechies.skillregister.data.remote.remote_model.SkillSugession;


/**
 * Created by sprydev5 on 6/11/16.
 */

public class JsonParse {
    public JsonParse() {

    }

    public List<CompanySugession> getParseJsonBuss(String sName) {
        List<CompanySugession> ListData = new ArrayList<CompanySugession>();
        try {
            String temp = sName.replace(" ", "%20");
            URL js = new URL("http://sr.api.sprytechies.net:3003/api/businesses/search?name=" + temp);
            URLConnection jc = js.openConnection();
            StringBuilder sb = new StringBuilder();
            InputStream is = jc.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            if (sb != null) {
                if (!sb.toString().contains("failure") || !sb.toString().equalsIgnoreCase("")) {
                    JSONArray jsonResponse;
                    jsonResponse = new JSONArray(sb.toString());
                    if (jsonResponse != null) {
                        if (jsonResponse.length() > 0) {
                            int i;
                            for (i = 0; i < jsonResponse.length(); i++) {
                                JSONObject jsonObject = new JSONObject(jsonResponse.get(i).toString());
                                ListData.add(new CompanySugession(jsonObject.getString("title")));
                            }
                        }
                    }
                }
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return ListData;

    }
    public List<SchoolSugession> getParseJsonSchool(String sName) {
        List<SchoolSugession> ListData = new ArrayList<SchoolSugession>();
        try {
            String temp = sName.replace(" ", "%20");
            URL js = new URL("http://sr.api.sprytechies.net:3003/api/schools/search?title=" + temp);
            URLConnection jc = js.openConnection();
            StringBuilder sb = new StringBuilder();
            InputStream is = jc.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            if (sb != null) {
                if (!sb.toString().contains("failure") || !sb.toString().equalsIgnoreCase("")) {
                    JSONArray jsonResponse;
                    jsonResponse = new JSONArray(sb.toString());
                    if (jsonResponse != null) {
                        if (jsonResponse.length() > 0) {
                            int i;
                            for (i = 0; i < jsonResponse.length(); i++) {
                                JSONObject jsonObject = new JSONObject(jsonResponse.get(i).toString());
                                ListData.add(new SchoolSugession(jsonObject.get("title").toString()));
                            }
                        }
                    }
                }
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return ListData;


    }

    public List<JobSugession> getParseJsonJob(String sName) {
        List<JobSugession> ListData = new ArrayList<JobSugession>();
        try {
            String temp = sName.replace(" ", "%20");
            URL js = new URL("http://sr.api.sprytechies.net:3003/api/jobs/search?name=" + temp);
            URLConnection jc = js.openConnection();
            StringBuilder sb = new StringBuilder();
            InputStream is = jc.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            if (sb != null) {
                if (!sb.toString().contains("failure") || !sb.toString().equalsIgnoreCase("")) {
                    JSONArray jsonResponse;
                    jsonResponse = new JSONArray(sb.toString());
                    if (jsonResponse != null) {
                        if (jsonResponse.length() > 0) {
                            int i;
                            for (i = 0; i < jsonResponse.length(); i++) {

                                ListData.add(new JobSugession(jsonResponse.get(i).toString()));
                            }
                        }
                    }
                }
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return ListData;

    }

    public List<CourseSugeeion> getParseJsonCourse(String sName) {
        List<CourseSugeeion> ListData = new ArrayList<CourseSugeeion>();
        try {
            String temp = sName.replace(" ", "%20");
            URL js = new URL("http://sr.api.sprytechies.net:3003/api/courses/search?name=" + temp);
            URLConnection jc = js.openConnection();
            StringBuilder sb = new StringBuilder();
            InputStream is = jc.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            if (sb != null) {
                if (!sb.toString().contains("failure") || !sb.toString().equalsIgnoreCase("")) {
                    JSONArray jsonResponse;
                    jsonResponse = new JSONArray(sb.toString());
                    if (jsonResponse != null) {
                        if (jsonResponse.length() > 0) {
                            int i;
                            for (i = 0; i < jsonResponse.length(); i++) {
                                ListData.add(new CourseSugeeion(jsonResponse.get(i).toString()));
                            }
                        }
                    }
                }
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return ListData;

    }
    public List<SkillSugession> getSkill(String sName) {
        List<SkillSugession> ListData = new ArrayList<SkillSugession>();
        try {
            String temp = sName.replace(" ", "%20");
            URL js = new URL("http://sr.api.sprytechies.net:3003/api/skills/search?name=" + temp);
            URLConnection jc = js.openConnection();
            StringBuilder sb = new StringBuilder();
            InputStream is = jc.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            if (sb != null) {
                if (!sb.toString().contains("failure") || !sb.toString().equalsIgnoreCase("")) {
                    JSONArray jsonResponse;
                    jsonResponse = new JSONArray(sb.toString());
                    if (jsonResponse != null) {
                        if (jsonResponse.length() > 0) {
                            int i;
                            for (i = 0; i < jsonResponse.length(); i++) {
                                ListData.add(new SkillSugession(jsonResponse.get(i).toString()));
                            }
                        }
                    }
                }
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return ListData;

    }
}


