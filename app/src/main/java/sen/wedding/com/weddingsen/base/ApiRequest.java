package sen.wedding.com.weddingsen.base;

import java.util.Iterator;
import java.util.Map;

import sen.wedding.com.weddingsen.http.base.CacheStrategy;
import sen.wedding.com.weddingsen.http.request.BasicHttpRequest;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.http.request.RequestBody;

/**
 * Created by lorin on 17/4/2.
 */

public class ApiRequest extends BasicHttpRequest {

    Map<String, Object> params;
    Object data;

    public ApiRequest(String url) {
        this(url, HttpMethod.POST, DEFAULT_TIMEOUT);
    }

    public ApiRequest(String url, String method) {
        this(url, method, DEFAULT_TIMEOUT);
    }


    public ApiRequest(String url, String method, int timeout) {
        super(url, method, timeout);
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void formatRequestParams() {

        addHeader("Accept", "application/json");
        if (params != null) {
            setBody(new RequestBody.JsonBody(params));
        } else {
            setBody(new RequestBody.JsonBody(data));
        }
    }

}
