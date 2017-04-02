package sen.wedding.com.weddingsen.http.service;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import sen.wedding.com.weddingsen.http.request.HttpRequest;
import sen.wedding.com.weddingsen.http.request.RequestBody;
import sen.wedding.com.weddingsen.http.response.BasicHttpResponse;
import sen.wedding.com.weddingsen.http.response.HttpResponse;
import sen.wedding.com.weddingsen.utils.AppLog;
import sen.wedding.com.weddingsen.utils.NetworkUtils;

/**
 * Created by sunyun on 16/4/29.
 */
public final class HttpEngine {

    private Context context;
    private HttpRequest request;

    private HttpURLConnection connection;

    public HttpEngine(Context context, HttpRequest request) {
        this.context = context;
        this.request = request;
    }

    public HttpResponse getResponse(IProgress progress) {
        if (NetworkUtils.getNetworkClass(context) == NetworkUtils.NETWORK_TYPE_UNAVAILABLE) {
            return new BasicHttpResponse(1025, request.getHeaders(), null, "网络已关闭".getBytes());
        }

        long startTime = System.currentTimeMillis();

        HttpResponse response;
        try {
            sendRequest();
            response = readResponse(progress);
        } catch (Exception e) {
            e.printStackTrace();
            response = new BasicHttpResponse(0, null, null, null);
        }

        long elapse = System.currentTimeMillis() - startTime;
        StringBuilder sb = new StringBuilder();
        if (response.getResult() != null) {
            sb.append("finish (");
        } else {
            sb.append("fail (");
        }
        sb.append(request.getMethod() + ",");
        sb.append(response.getStatusCode()).append(',');
        sb.append(elapse).append("ms");
        sb.append(") ").append(request.getUrl());
        AppLog.i(sb.toString());

        return response;

    }

    private void sendRequest() throws IOException {
        String url = request.getUrl();
        URL parseUrl = new URL(url);

        connection = openConnection(parseUrl);
        addHeaders();
        setRequestType();
    }

    private HttpURLConnection openConnection(URL url) throws IOException {
        HttpURLConnection connection = createConnection(url);
        int timeoutMs = request.getTimeout();
        connection.setConnectTimeout(timeoutMs);
        connection.setReadTimeout(timeoutMs);
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.addRequestProperty("Accept-Encoding", "gzip");

        return connection;
    }

    protected HttpURLConnection createConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    private void addHeaders() {
        Map<String, String> header = request.getHeaders();
        for (String headerName : header.keySet()) {
            connection.addRequestProperty(headerName, header.get(headerName));
        }
    }

    private void setRequestType() throws IOException {
        connection.setRequestMethod(request.getMethod());
        addBody();
    }

    private void addBody() throws IOException {
        RequestBody body = request.getBody();
        if (body != null) {
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type",
                    body.contentType());
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            body.writeTo(out);
            out.close();
        }
    }

    private HttpResponse readResponse(IProgress progress) throws IOException {
        int responseCode = connection.getResponseCode();
        if (responseCode == -1) {
            throw new IOException(
                    "Could not retrieve response code from HttpUrlConnection.");
        }

        HashMap<String, String> responseHeaders = new HashMap<String, String>();
        for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
            if (header.getKey() != null) {
                responseHeaders.put(header.getKey(), header.getValue().get(0));
            }
        }

        int totalContent = connection.getContentLength();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        InputStream input;
        try {
            input = connection.getInputStream();
            if (input == null) {
                throw new Exception();
            }
            if ("gzip".equals(connection.getContentEncoding())) {
                input = new GZIPInputStream(input);
            }

            int count;
            if (totalContent > 0) {
                while ((count = input.read(buffer)) != -1) {
                    output.write(buffer, 0, count);
                    if(progress != null) {
                        progress.reportProgress(output.size(), totalContent);
                    }
                }
            } else {
                while ((count = input.read(buffer)) != -1) {
                    output.write(buffer, 0, count);
                }
            }
            while ((count = input.read(buffer)) != -1) {
                output.write(buffer, 0, count);
            }
            return new BasicHttpResponse(responseCode, responseHeaders, output.toByteArray(), null);

        } catch (Exception e) {
            input = connection.getErrorStream();
            int count;
            while ((count = input.read(buffer)) != -1) {
                output.write(buffer, 0, count);
            }
            return new BasicHttpResponse(responseCode, responseHeaders, null, output.toByteArray());
        } finally {
            output.close();
        }
    }

    public interface IProgress {
        void reportProgress(int count, int total);
    }

}
