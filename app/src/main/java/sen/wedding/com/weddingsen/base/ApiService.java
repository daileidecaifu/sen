package sen.wedding.com.weddingsen.base;

import android.content.Context;
import android.os.AsyncTask;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

import sen.wedding.com.weddingsen.http.base.DataService;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.response.HttpResponse;
import sen.wedding.com.weddingsen.http.service.ExecutorUtil;
import sen.wedding.com.weddingsen.http.service.HttpEngine;
import sen.wedding.com.weddingsen.utils.AppLog;

/**
 * Created by lorin on 17/4/2.
 */

public class ApiService implements DataService<ApiRequest, ApiResponse> {

    private Context context;
    private Executor executor;

    private ApiInterceptor interceptor;

    private final ConcurrentHashMap<ApiRequest, ApiTask> runningTasks = new ConcurrentHashMap<>();

    public ApiService(Context context) {
        this(context, ExecutorUtil.DEFAULT_EXECUTOR);
    }

    public ApiService(Context context, Executor executor) {
        this.context = context;
        this.executor = executor;
    }

    public void setInterceptor(ApiInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public void exec(ApiRequest req, RequestHandler<ApiRequest, ApiResponse> handler) {
        req.formatRequestParams();

        ApiTask currentTask = new ApiTask(req, handler);
        ApiTask prevTask = runningTasks.putIfAbsent(req, currentTask);
        if (prevTask == null) {
            currentTask.executeOnExecutor(executor);
        } else {
            AppLog.e("api cannot exec duplicate request (same instance)");
        }
    }

    @Override
    public void abort(ApiRequest req, RequestHandler<ApiRequest, ApiResponse> handler,
                      boolean mayInterruptIfRunning) {
        ApiTask runningTask = runningTasks.get(req);
        if (runningTask != null && runningTask.handler == handler) {
            runningTasks.remove(req, runningTask);
            runningTask.cancel(mayInterruptIfRunning);
        }
    }


    protected class ApiTask extends AsyncTask<Void, Integer, ApiResponse> implements HttpEngine.IProgress{

        protected final ApiRequest req;
        protected final RequestHandler<ApiRequest, ApiResponse> handler;

        protected HttpEngine httpEngine;

        public ApiTask(ApiRequest req, RequestHandler<ApiRequest, ApiResponse> handler) {
            super();

            this.req = req;
            this.handler = handler;
            httpEngine = new HttpEngine(context, req);
        }

        @Override
        protected void onPreExecute() {
            if(interceptor != null) {
                if(interceptor.beforeRequest(req, handler)) {
                    return;
                }
            }
            if (handler != null) {
                handler.onRequestStart(req);
            }
        }

        @Override
        protected ApiResponse doInBackground(Void... params) {


            HttpResponse httpResp = httpEngine.getResponse(this);
            ApiResponse resp = new ApiResponse(httpResp.getStatusCode(), httpResp.getHeaders(),
                    httpResp.getResult(), httpResp.getError());

            return resp;
        }

        @Override
        protected void onPostExecute(ApiResponse response) {
            runningTasks.remove(req, this);

            handleResponse(response);
        }

        private void handleResponse(ApiResponse response) {
            if (response.getStatusCode() >= 200 && response.getStatusCode() <= 299) {
                if(interceptor != null) {
                    if(interceptor.afterRequest(req, response, handler)) {
                        return;
                    }
                }
                ResultModel resultModel = response.getResultModel();
                if(handler != null) {
                    if(resultModel.status==997)
                    {
                        SenApplication.getInstance().logout();
                    }else
                    {
                        handler.onRequestFinish(req, response);
                    }
                }
            } else {
                if (handler != null) {
                    handler.onRequestFailed(req, response);
                }
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (handler != null) {
                handler.onRequestProgress(req, values[0], values[1]);
            }
        }

        @Override
        public void reportProgress(int count, int total) {
            publishProgress(count, total);
        }


    }

    public interface ApiInterceptor {

        boolean beforeRequest(ApiRequest req, RequestHandler<ApiRequest, ApiResponse> handler);

        boolean afterRequest(ApiRequest req, ApiResponse resp, RequestHandler<ApiRequest, ApiResponse> handler);
    }
}
