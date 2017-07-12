package sen.wedding.com.weddingsen.business.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lorin on 17/4/10.
 */

public class DetailResModel {

    @SerializedName("order_item")
    private OrderItemModel orderItem;

    @SerializedName("order_follow")
    private LogInfoModel orderFollow;

    @SerializedName("handle_note")
    private String handleNote;

    @SerializedName("handle_time")
    private String handleTime;

    @SerializedName("finish_middle")
    private int finishMiddle;

    public OrderItemModel getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItemModel orderItem) {
        this.orderItem = orderItem;
    }

    public LogInfoModel getOrderFollow() {
        return orderFollow;
    }

    public void setOrderFollow(LogInfoModel orderFollow) {
        this.orderFollow = orderFollow;
    }

    public String getHandleNote() {
        return handleNote;
    }

    public void setHandleNote(String handleNote) {
        this.handleNote = handleNote;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public int getFinishMiddle() {
        return finishMiddle;
    }

    public void setFinishMiddle(int finishMiddle) {
        this.finishMiddle = finishMiddle;
    }
}
