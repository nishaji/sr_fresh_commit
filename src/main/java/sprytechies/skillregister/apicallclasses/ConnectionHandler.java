package sprytechies.skillregister.apicallclasses;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pradeep on 3/11/15.
 */
public class ConnectionHandler {

    public static String response = null;
    public String urlstr;
    public String method;
    public JSONObject params;

    public ConnectionHandler(String urlstr, String method, JSONObject params) {
        this.urlstr=urlstr;
        this.method=method;
        this.params=params;
    }

    public String makePostJsonCall() {
        try{
            //Connect
            URL url = new URL(urlstr);
            HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setDoOutput(true);urlCon.setChunkedStreamingMode(0);
            urlCon.setRequestProperty("Content-Type", "application/json");
            urlCon.setRequestProperty("Accept", "application/json");
            urlCon.setRequestMethod(method);
            urlCon.connect();


            //Write
            OutputStream os = urlCon.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(params.toString());
            writer.close();
            os.close();

            //Read
            BufferedReader br = new BufferedReader(new InputStreamReader(urlCon.getInputStream(),"UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            response = sb.toString();
            int code = urlCon.getResponseCode();
            System.out.println("Response code of the object is "+code);
            if (code==200)
            {
                System.out.println("OK");
            } else {
                System.out.println(urlCon.getErrorStream()+"error"+code);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }

}
