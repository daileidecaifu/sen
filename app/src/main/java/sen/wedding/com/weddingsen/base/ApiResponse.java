package sen.wedding.com.weddingsen.base;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.util.Iterator;
import java.util.Map;

import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.BasicHttpRequest;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.http.request.RequestBody;
import sen.wedding.com.weddingsen.http.request.RequestBody.JsonBody;
import sen.wedding.com.weddingsen.http.response.BasicHttpResponse;
import sen.wedding.com.weddingsen.utils.GsonConverter;

/**
 * Created by lorin on 17/4/2.
 */

public class ApiResponse extends BasicHttpResponse {


    private byte[] decryptResult;

    public ApiResponse(int statusCode, Map<String, String> headers, byte[] result,
                            byte[] error) {
        this(statusCode, headers, result, error, false);
    }

    public ApiResponse(int statusCode, Map<String, String> headers, byte[] result,
                            byte[] error, boolean isFromCache) {
        super(statusCode, headers, result, error);
        decryptResult = decryptResultData();
    }

    private byte[] decryptResultData() {
        if (getResult() == null || getResult().length == 0) {
            return null;
        }

        return getResult();
    }



    public JsonObject getJsonResult() {
        if (decryptResult == null || decryptResult.length == 0) {
            return null;
        }

        try {
            return (JsonObject) new JsonParser().parse(new String(decryptResult));
        } catch (JsonParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultModel getResultModel() {
        try {
            String strResult = new String(decryptResult);
            ResultModel decode = GsonConverter.decode(strResult, ResultModel.class);
            if (decode == null) {
                throw new Exception("model decode error");
            }

            return decode;
        } catch (Exception ex) {
            ex.printStackTrace();
            return  createEmptyResultModel();
        }
    }

    private ResultModel createEmptyResultModel() {
        ResultModel result = new ResultModel();
        result.status = -1024;
        result.message = "model decode error";
        return result;
    }

}