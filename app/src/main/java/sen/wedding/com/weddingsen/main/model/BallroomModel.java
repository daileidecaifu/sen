package sen.wedding.com.weddingsen.main.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lorin on 17/5/26.
 */

public class BallroomModel {

    @SerializedName("room_name")
    private String roomName;

    @SerializedName("room_max_desk")
    private String roomMaxDesk;

    @SerializedName("room_high")
    private String roomHigh;

    @SerializedName("room_lz")
    private String roomLz;

    @SerializedName("room_min_desk")
    private String roomMinDesk;

    @SerializedName("room_best_desk")
    private String roomBestDesk;

    @SerializedName("room_m")
    private String roomM;

    @SerializedName("room_image")
    private List<String> roomImage;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomMaxDesk() {
        return roomMaxDesk;
    }

    public void setRoomMaxDesk(String roomMaxDesk) {
        this.roomMaxDesk = roomMaxDesk;
    }

    public String getRoomHigh() {
        return roomHigh;
    }

    public void setRoomHigh(String roomHigh) {
        this.roomHigh = roomHigh;
    }

    public String getRoomLz() {
        return roomLz;
    }

    public void setRoomLz(String roomLz) {
        this.roomLz = roomLz;
    }

    public String getRoomMinDesk() {
        return roomMinDesk;
    }

    public void setRoomMinDesk(String roomMinDesk) {
        this.roomMinDesk = roomMinDesk;
    }

    public String getRoomBestDesk() {
        return roomBestDesk;
    }

    public void setRoomBestDesk(String roomBestDesk) {
        this.roomBestDesk = roomBestDesk;
    }

    public String getRoomM() {
        return roomM;
    }

    public void setRoomM(String roomM) {
        this.roomM = roomM;
    }

    public List<String> getRoomImage() {
        return roomImage;
    }

    public void setRoomImage(List<String> roomImage) {
        this.roomImage = roomImage;
    }
}
