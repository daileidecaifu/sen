package sen.wedding.com.weddingsen.base.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lorin on 17/8/14.
 */

public class CheckVersionResModel {

    @SerializedName("update_status")
    private UpdateStatusModel updateStatus;

    public UpdateStatusModel getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(UpdateStatusModel updateStatus) {
        this.updateStatus = updateStatus;
    }
}
