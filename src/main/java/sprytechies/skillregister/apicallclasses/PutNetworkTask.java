package sprytechies.skillregister.apicallclasses;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by sprydev5 on 2/9/16.
 */
public class PutNetworkTask extends AsyncTask<Void, Integer, String> {

    private final String path;
    private final JSONObject params;
    private final String apiserver = "http://sr.api.sprytechies.net:3003/api";

    public AsyncResponse delegate = null;

    private String TAG = "NetworkTask";

    public PutNetworkTask(String path, JSONObject params) {
        this.path = path;
        this.params = params;
    }

    @Override
    protected String doInBackground(Void... input) {
        ConnectionHandler conn = new ConnectionHandler(apiserver + path, "PUT", params);
        return conn.makePostJsonCall();
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.ProcessResponse(path, result);
        Log.d(TAG, " update returned: " + result);
    }

}

