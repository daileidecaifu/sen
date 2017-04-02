package sen.wedding.com.weddingsen.http.service;

import android.content.Context;
import android.os.AsyncTask;



import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

import sen.wedding.com.weddingsen.http.base.DataService;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.request.HttpRequest;
import sen.wedding.com.weddingsen.http.response.HttpResponse;
import sen.wedding.com.weddingsen.utils.AppLog;

public class HttpService implements DataService<HttpRequest, HttpResponse> {

    protected Context context;
    protected Executor executor;

    protected final ConcurrentHashMap<HttpRequest, HttpTask> runningTasks = new ConcurrentHashMap<>();

    public HttpService(Context context) {
        this(context, ExecutorUtil.DEFAULT_EXECUTOR);
    }

    public HttpService(Context context, Executor executor) {
        this.context = context;
        this.executor = executor;
    }

    @Override
    public void exec(HttpRequest req, RequestHandler<HttpRequest, HttpResponse> handler) {
        req.formatRequestParams();

        HttpTask currentTask = new HttpTask(req, handler);
        HttpTask prevTask = runningTasks.putIfAbsent(req, currentTask);
        if (prevTask == null) {
            currentTask.executeOnExecutor(executor);
        } else {
            AppLog.e("http cannot exec duplicate request (same instance)");
        }
    }

    @Override
    public void abort(HttpRequest req, RequestHandler<HttpRequest, HttpResponse> handler,
                      boolean mayInterruptIfRunning) {
        HttpTask runningTask = runningTasks.get(req);
        if (runningTask != null && runningTask.handler == handler) {
            runningTasks.remove(req, runningTask);
            runningTask.cancel(mayInterruptIfRunning);
        }
    }

    protected class HttpTask extends AsyncTask<Void, Integer, HttpResponse> implements HttpEngine.IProgress{

        protected final HttpRequest req;
        protected final RequestHandler<HttpRequest, HttpResponse> handler;

        protected HttpEngine httpEngine;

        public HttpTask(HttpRequest req, RequestHandler<HttpRequest, HttpResponse> handler) {
            super();

            this.req = req;
            this.handler = handler;
            httpEngine = new HttpEngine(context, req);
        }

        @Override
        protected void onPreExecute() {
            if (handler != null) {
                handler.onRequestStart(req);
            }
        }

        @Override
        protected HttpResponse doInBackground(Void... params) {
            return httpEngine.getResponse(this);
        }

        @Override
        protected void onPostExecute(HttpResponse response) {
            runningTasks.remove(req, this);

            if (response.getStatusCode() >= 200 && response.getStatusCode() <= 299) {
                if(handler != null) {
                    handler.onRequestFinish(req, response);
                }
            } else {
                if(handler != null) {
                    handler.onRequestFailed(req, response);
                }
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (handler!= null) {
                handler.onRequestProgress(req, values[0], values[1]);
            }
        }

        @Override
        public void reportProgress(int count, int total) {
            publishProgress(count, total);
        }
    }


}
