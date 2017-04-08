package sen.wedding.com.weddingsen.utils.model;

import sen.wedding.com.weddingsen.base.BaseActivity;

/**
 * Created by lorin on 17/3/26.
 */

public class BaseTypeModel {

    private String key;
    private String content;
    private int type;

    public BaseTypeModel(String oKey,String oContent)
    {
        this.key = oKey;
        this.content = oContent;
    }

    public BaseTypeModel(String oKey,String oContent,int type)
    {
        this.key = oKey;
        this.content = oContent;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return content;
    }

    public void setValue(String value) {
        this.content = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
