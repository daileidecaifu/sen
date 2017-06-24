package sen.wedding.com.weddingsen.sales.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lorin on 17/5/7.
 */

public class PayRecordLogModel {

    @SerializedName("sign_type")
    private String signType;

    @SerializedName("order_money")
    private String orderMoney;

    @SerializedName("order_time")
    private String orderTime;

    @SerializedName("first_order_money")
    private String firstOrderMoney;

    @SerializedName("first_order_using_time")
    private String firstOrderUsingTime;

    @SerializedName("other_item_weikuan_old_time")
    private String otherItemWeikuanOldTime;

    @SerializedName("other_item_weikuan_new_time")
    private String otherItemWeikuanNewTime;

    @SerializedName("order_sign_pic")
    private List<String> orderSignPic;

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getFirstOrderMoney() {
        return firstOrderMoney;
    }

    public void setFirstOrderMoney(String firstOrderMoney) {
        this.firstOrderMoney = firstOrderMoney;
    }

    public String getFirstOrderUsingTime() {
        return firstOrderUsingTime;
    }

    public void setFirstOrderUsingTime(String firstOrderUsingTime) {
        this.firstOrderUsingTime = firstOrderUsingTime;
    }

    public String getOtherItemWeikuanOldTime() {
        return otherItemWeikuanOldTime;
    }

    public void setOtherItemWeikuanOldTime(String otherItemWeikuanOldTime) {
        this.otherItemWeikuanOldTime = otherItemWeikuanOldTime;
    }

    public String getOtherItemWeikuanNewTime() {
        return otherItemWeikuanNewTime;
    }

    public void setOtherItemWeikuanNewTime(String otherItemWeikuanNewTime) {
        this.otherItemWeikuanNewTime = otherItemWeikuanNewTime;
    }

    public List<String> getOrderSignPic() {
        return orderSignPic;
    }

    public void setOrderSignPic(List<String> orderSignPic) {
        this.orderSignPic = orderSignPic;
    }
}
