package sen.wedding.com.weddingsen.sales.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import sen.wedding.com.weddingsen.component.refresh.CursorModel;

/**
 * Created by lorin on 17/3/30.
 */

public class SecondSaleContractResModel{

    private String title;

    @SerializedName("first_input_note")
    private String fristInputNote;

    @SerializedName("first_input_content")
    private String firstInputContent;

    @SerializedName("second_input_note")
    private String secondInputNote;

    @SerializedName("second_input_content")
    private String secondInputContent;

    @SerializedName("third_input_note")
    private String thirdInputNote;

    @SerializedName("third_input_content")
    private List<String> thirdInputContent;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFristInputNote() {
        return fristInputNote;
    }

    public void setFristInputNote(String fristInputNote) {
        this.fristInputNote = fristInputNote;
    }

    public String getFirstInputContent() {
        return firstInputContent;
    }

    public void setFirstInputContent(String firstInputContent) {
        this.firstInputContent = firstInputContent;
    }

    public String getSecondInputNote() {
        return secondInputNote;
    }

    public void setSecondInputNote(String secondInputNote) {
        this.secondInputNote = secondInputNote;
    }

    public String getSecondInputContent() {
        return secondInputContent;
    }

    public void setSecondInputContent(String secondInputContent) {
        this.secondInputContent = secondInputContent;
    }

    public String getThirdInputNote() {
        return thirdInputNote;
    }

    public void setThirdInputNote(String thirdInputNote) {
        this.thirdInputNote = thirdInputNote;
    }

    public List<String> getThirdInputContent() {
        return thirdInputContent;
    }

    public void setThirdInputContent(List<String> thirdInputContent) {
        this.thirdInputContent = thirdInputContent;
    }
}
