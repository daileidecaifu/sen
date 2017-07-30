package sen.wedding.com.weddingsen.main.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by lorin on 17/7/30.
 */

public class HotelOptionResModel {

    @SerializedName("sh_area")
    private Map<String,String> shArea;

    @SerializedName("hotel_level")
    private Map<String,String> hotelLevel;

    public Map<String, String> getShArea() {
        return shArea;
    }

    public void setShArea(Map<String, String> shArea) {
        this.shArea = shArea;
    }

    public Map<String, String> getHotelLevel() {
        return hotelLevel;
    }

    public void setHotelLevel(Map<String, String> hotelLevel) {
        this.hotelLevel = hotelLevel;
    }
}
