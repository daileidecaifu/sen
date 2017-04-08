package sen.wedding.com.weddingsen.business.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lorin on 17/4/9.
 */

public class HotelModel {

    @SerializedName("hotel_id")
    private String hotelId;

    @SerializedName("hotel_name")
    private String hotelName;

    private boolean isSelect;

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
