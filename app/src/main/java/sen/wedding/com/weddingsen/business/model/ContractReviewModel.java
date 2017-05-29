package sen.wedding.com.weddingsen.business.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lorin on 17/5/29.
 */

public class ContractReviewModel {

    @SerializedName("order_money")
    private String orderMoney;

    @SerializedName("order_other_money")
    private String orderOtherMoney;

    @SerializedName("sign_using_time")
    private String signUsingTime;

    @SerializedName("sign_pic")
    private String signPic;

    @SerializedName("user_kezi_order_id")
    private String userKeziOrderId;

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getOrderOtherMoney() {
        return orderOtherMoney;
    }

    public void setOrderOtherMoney(String orderOtherMoney) {
        this.orderOtherMoney = orderOtherMoney;
    }

    public String getSignUsingTime() {
        return signUsingTime;
    }

    public void setSignUsingTime(String signUsingTime) {
        this.signUsingTime = signUsingTime;
    }

    public String getSignPic() {
        return signPic;
    }

    public void setSignPic(String signPic) {
        this.signPic = signPic;
    }

    public String getUserKeziOrderId() {
        return userKeziOrderId;
    }

    public void setUserKeziOrderId(String userKeziOrderId) {
        this.userKeziOrderId = userKeziOrderId;
    }
}
