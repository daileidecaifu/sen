package sen.wedding.com.weddingsen.business.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lorin on 17/4/11.
 */

public class AreaOrHotelResModel {

    @SerializedName("area_list")
    private ArrayList<AreaModel> areaList;

    @SerializedName("hotel_list")
    private ArrayList<HotelModel> hotelList;

    public ArrayList<AreaModel> getAreaList() {
        return areaList;
    }

    public void setAreaList(ArrayList<AreaModel> areaList) {
        this.areaList = areaList;
    }

    public ArrayList<HotelModel> getHotelList() {
        return hotelList;
    }

    public void setHotelList(ArrayList<HotelModel> hotelList) {
        this.hotelList = hotelList;
    }
}
