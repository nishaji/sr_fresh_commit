package sprytechies.skillregister.apicallclasses;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import sprytechies.skillregister.model.BussinessSugession;
import sprytechies.skillregister.model.JobSugession;
import sprytechies.skillregister.model.SchoolSugession;
import sprytechies.skillregister.model.SkillSugession;

/**
 * Created by sprydev5 on 22/9/16.
 */

public class JsonParse {
    public JsonParse(){

    }

    public List<BussinessSugession> getParseJsonBuss(String sName)
    {
        List<BussinessSugession> ListData = new ArrayList<BussinessSugession>();
        try {
            String temp=sName.replace(" ", "%20");
            URL js = new URL("http://192.168.1.107:3001/api/businesses/search?name="+temp);
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
                                ListData.add(new BussinessSugession(jsonResponse.get(i).toString()));
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
    public List<SkillSugession> getParseJsonSkii(String sName)
    {
        List<SkillSugession> ListData = new ArrayList<SkillSugession>();
        try {
            String temp=sName.replace(" ", "%20");
            URL js = new URL("http://192.168.1.107:3001/api/skills/search?name="+temp);
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
    public List<SchoolSugession> getParseJsonSchool(String sName)
    {
        List<SchoolSugession> ListData = new ArrayList<SchoolSugession>();
        try {
            String temp=sName.replace(" ", "%20");
            URL js = new URL("http://192.168.1.107:3001/api/schools/search?name="+temp);
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
                                ListData.add(new SchoolSugession(jsonResponse.get(i).toString()));
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
    public List<JobSugession> getParseJsonJob(String sName)
    {
        List<JobSugession> ListData = new ArrayList<JobSugession>();
        try {
            String temp=sName.replace(" ", "%20");
            URL js = new URL("http://192.168.1.107:3001/api/jobs/search?name="+temp);
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
}
