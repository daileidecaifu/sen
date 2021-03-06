package sen.wedding.com.weddingsen.main.model;

import com.google.gson.annotations.SerializedName;


/**
 * Created by lorin on 17/5/26.
 */

public class HotelShowModel {

    @SerializedName("hotel_id")
    private String hotelId;

    @SerializedName("hotel_name")
    private String hotelName;

    @SerializedName("hotel_low")
    private String hotelLow;

    @SerializedName("hotel_high")
    private String hotelHigh;

    @SerializedName("hotel_max_desk")
    private String hotelMaxDesk;

    @SerializedName("area_sh_name")
    private String areaShName;

    @SerializedName("hotel_type")
    private String hotelType;

    @SerializedName("hotel_phone")
    private String hotelPhone;

    @SerializedName("hotel_image")
    private String hotelImage;

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelLow() {
        return hotelLow;
    }

    public void setHotelLow(String hotelLow) {
        this.hotelLow = hotelLow;
    }

    public String getHotelHigh() {
        return hotelHigh;
    }

    public void setHotelHigh(String hotelHigh) {
        this.hotelHigh = hotelHigh;
    }

    public String getHotelMaxDesk() {
        return hotelMaxDesk;
    }

    public void setHotelMaxDesk(String hotelMaxDesk) {
        this.hotelMaxDesk = hotelMaxDesk;
    }

    public String getAreaShName() {
        return areaShName;
    }

    public void setAreaShName(String areaShName) {
        this.areaShName = areaShName;
    }

    public String getHotelType() {
        return hotelType;
    }

    public void setHotelType(String hotelType) {
        this.hotelType = hotelType;
    }

    public String getHotelPhone() {
        return hotelPhone;
    }

    public void setHotelPhone(String hotelPhone) {
        this.hotelPhone = hotelPhone;
    }

    public String getHotelImage() {
        return hotelImage;
    }

    public void setHotelImage(String hotelImage) {
        this.hotelImage = hotelImage;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }
}
