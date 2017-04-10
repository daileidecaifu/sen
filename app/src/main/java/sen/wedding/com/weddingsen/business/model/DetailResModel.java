package sen.wedding.com.weddingsen.business.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lorin on 17/4/10.
 */

public class DetailResModel {

    @SerializedName("order_item")
    private OrderItemModel orderItem;

    public OrderItemModel getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItemModel orderItem) {
        this.orderItem = orderItem;
    }
}
