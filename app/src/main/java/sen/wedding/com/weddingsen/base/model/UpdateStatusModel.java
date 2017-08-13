package sen.wedding.com.weddingsen.base.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lorin on 17/8/14.
 */

public class UpdateStatusModel {

    private String version;

    @SerializedName("update_now")
    private String updateNow;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpdateNow() {
        return updateNow;
    }

    public void setUpdateNow(String updateNow) {
        this.updateNow = updateNow;
    }
}
