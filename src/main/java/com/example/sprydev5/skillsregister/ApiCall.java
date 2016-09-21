package com.example.sprydev5.skillsregister;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sprydev5 on 16/8/16.
 */
public class ApiCall implements Runnable  {

    public api_call caller;

    public interface api_call
    {
        public void get_data(String data);
    }

    ApiCall(api_call caller) {
        this.caller = caller;
    }

    private String link;
    public void caliing_api(String link)
    {
        this.link = link;
        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        //	caller.get_data(download(this.link));
        threadMsg(download(this.link));

    }

    private void threadMsg(String msg) {

        if (!msg.equals(null) && !msg.equals("")) {
            Message msgObj = handler.obtainMessage();
            Bundle b = new Bundle();
            b.putString("message", msg);
            msgObj.setData(b);
            handler.sendMessage(msgObj);
        }
    }


    private final Handler handler = new Handler() {

        public void handleMessage(Message msg) {

            String Response = msg.getData().getString("message");

            caller.get_data(Response);

        }
    };

    public static String download(String url) {
        URL website;
        StringBuilder response = null;
        try {
            website = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
            connection.setRequestProperty("charset", "utf-8");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));

            response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                response.append(inputLine);

            in.close();

        } catch (Exception  e) {
            return "";
        }


        return response.toString();
    }


}




