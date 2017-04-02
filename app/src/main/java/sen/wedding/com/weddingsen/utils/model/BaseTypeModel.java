package sen.wedding.com.weddingsen.utils.model;

import sen.wedding.com.weddingsen.base.BaseActivity;

/**
 * Created by lorin on 17/3/26.
 */

public class BaseTypeModel {

    private String key;
    private String value;

    public BaseTypeModel(String oKey,String oValue)
    {
        this.key = oKey;
        this.value = oValue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
