package sen.wedding.com.weddingsen.main.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lorin on 17/6/10.
 */

public class HotelDetailModel {

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

    @SerializedName("hotel_images")
    private List<String> hotelImages;

    @SerializedName("hotel_address")
    private String hotelAddress;

    @SerializedName("room_list")
    private List<BallroomModel> roomList;

    @SerializedName("menu_list")
    private List<BanquetMenuModel> menuList;

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

    public List<String> getHotelImages() {
        return hotelImages;
    }

    public void setHotelImages(List<String> hotelImages) {
        this.hotelImages = hotelImages;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public List<BallroomModel> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<BallroomModel> roomList) {
        this.roomList = roomList;
    }

    public List<BanquetMenuModel> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<BanquetMenuModel> menuList) {
        this.menuList = menuList;
    }
}
