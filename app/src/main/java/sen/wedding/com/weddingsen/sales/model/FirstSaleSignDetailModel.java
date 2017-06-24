package sen.wedding.com.weddingsen.sales.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lorin on 17/6/22.
 */

public class FirstSaleSignDetailModel {

    @SerializedName("order_money")
    private String orderMoney;

    @SerializedName("sign_using_time")
    private String signUsingTime;

    @SerializedName("first_order_money")
    private String firstOrderMoney;

    @SerializedName("sign_pic")
    private List<String> signPic;

    @SerializedName("first_order_using_time")
    private String firstOrderUsingTime;

    @SerializedName("next_pay_time")
    private String nextPayTime;

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getSignUsingTime() {
        return signUsingTime;
    }

    public void setSignUsingTime(String signUsingTime) {
        this.signUsingTime = signUsingTime;
    }

    public String getFirstOrderMoney() {
        return firstOrderMoney;
    }

    public void setFirstOrderMoney(String firstOrderMoney) {
        this.firstOrderMoney = firstOrderMoney;
    }

    public List<String> getSignPic() {
        return signPic;
    }

    public void setSignPic(List<String> signPic) {
        this.signPic = signPic;
    }

    public String getFirstOrderUsingTime() {
        return firstOrderUsingTime;
    }

    public void setFirstOrderUsingTime(String firstOrderUsingTime) {
        this.firstOrderUsingTime = firstOrderUsingTime;
    }

    public String getNextPayTime() {
        return nextPayTime;
    }

    public void setNextPayTime(String nextPayTime) {
        this.nextPayTime = nextPayTime;
    }
}
