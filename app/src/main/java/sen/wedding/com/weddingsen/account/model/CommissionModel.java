package sen.wedding.com.weddingsen.account.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lorin on 17/6/12.
 */

public class CommissionModel {

    private String unpay;

    private String pay;

    private String all;

    public String getUnpay() {
        return unpay;
    }

    public void setUnpay(String unpay) {
        this.unpay = unpay;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }
}
