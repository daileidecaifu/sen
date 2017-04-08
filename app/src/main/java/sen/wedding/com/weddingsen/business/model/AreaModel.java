package sen.wedding.com.weddingsen.business.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lorin on 17/3/25.
 */

public class AreaModel {

    @SerializedName("area_id")
    private String areaId;

    @SerializedName("area_name")
    private String areaName;

    private boolean isSelect;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}