package sen.wedding.com.weddingsen.utils.model;

/**
 * Created by lorin on 17/6/14.
 */

public class EventIntent {

    private int actionId;

    private String data;

    public EventIntent(int actionId , String data)
    {
        this.actionId = actionId;
        this.data = data;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
