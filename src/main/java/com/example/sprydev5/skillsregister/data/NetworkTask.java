package com.example.sprydev5.skillsregister.data;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by pradeep on 4/11/15.
 */

public class NetworkTask extends AsyncTask<Void, Integer, String> {

    private final String path;
    private final JSONObject params;
    private final String apiserver="http://sr.api.sprytechies.net:3003/api";

    public AsyncResponse delegate = null;

    private String TAG="NetworkTask";

    public NetworkTask(String path, JSONObject params){
        this.path=path;
        this.params=params;
    }

    @Override
    protected String doInBackground(Void... input) {
        ConnectionHandler conn = new ConnectionHandler(apiserver+path,"POST",params);
        return conn.makePostJsonCall();
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.ProcessResponse(path, result);
      //  Log.d(TAG, " returned: " + result);
    }

}
