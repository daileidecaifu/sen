package sen.wedding.com.weddingsen.sales.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lorin on 17/6/24.
 */

public class PayLogResModel {

    @SerializedName("sign_list")
    List<PayRecordLogModel> signList;

    public List<PayRecordLogModel> getSignList() {
        return signList;
    }

    public void setSignList(List<PayRecordLogModel> signList) {
        this.signList = signList;
    }
}
