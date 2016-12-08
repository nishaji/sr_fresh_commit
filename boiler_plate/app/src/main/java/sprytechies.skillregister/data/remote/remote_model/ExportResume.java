package sprytechies.skillregister.data.remote.remote_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sprydev5 on 29/11/16.
 */

public class ExportResume  {
    @SerializedName("HTML")
    @Expose
    private String html;
    @SerializedName("path")
    @Expose
    private String path;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
