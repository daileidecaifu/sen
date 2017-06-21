package sen.wedding.com.weddingsen.sales.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import sen.wedding.com.weddingsen.main.model.OrderInfoModel;

/**
 * Created by lorin on 17/4/9.
 */

public class SecondSaleInfosResModel {

    @SerializedName("order_list")
    private ArrayList<SecondSaleInfoModel> orderList;

    private int count;

    public ArrayList<SecondSaleInfoModel> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<SecondSaleInfoModel> orderList) {
        this.orderList = orderList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
