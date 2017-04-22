package sen.wedding.com.weddingsen.main.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import sen.wedding.com.weddingsen.main.model.OrderInfoModel;

/**
 * Created by lorin on 17/4/9.
 */

public class GuestInfosResModel {

    @SerializedName("order_list")
    private ArrayList<OrderInfoModel> orderList;

    private int count;

    public ArrayList<OrderInfoModel> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<OrderInfoModel> orderList) {
        this.orderList = orderList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
