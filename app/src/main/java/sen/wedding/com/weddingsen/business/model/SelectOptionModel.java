package sen.wedding.com.weddingsen.business.model;

/**
 * Created by lorin on 17/3/25.
 */

public class SelectOptionModel {

    private String content;

    private int position;

    private String type;

    private boolean isSelect;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}