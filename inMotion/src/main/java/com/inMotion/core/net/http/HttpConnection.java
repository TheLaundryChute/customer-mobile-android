package com.inMotion.core.net.http;

import android.os.AsyncTask;
import android.util.Log;

import com.inMotion.core.*;
import com.inMotion.core.net.http.delegate.IHttpConnectionDelegate;
import com.inMotion.session.Context;

import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;


/**
 * Created by sfbechtold on 9/5/15.
 */
public class HttpConnection {


    public boolean send(HttpRequest request, IHttpConnectionDelegate delegate) {
        HttpConnectionTask task = new HttpConnectionTask();
        task.setRequest(request);
        task.setDelegate(delegate);
        task.execute();

        return  true;
    }


    private class HttpConnectionTask  extends AsyncTask<Void, Void, HttpResponse> {

        private HttpRequest request;
        private IHttpConnectionDelegate delegate;

        protected HttpResponse doInBackground(Void... params) {
            if (this.getRequest() == null) {
                return new HttpResponse(com.inMotion.core.Error.error(100, "No request was provided for HttpConnection", "HttpConnection.send"));
            }

            if (this.getRequest().getUri() == null) {
                return new HttpResponse(com.inMotion.core.Error.error(100, "No URL was provided for HttpConnection", "HttpConnection.send"));
            }

            try {
                HttpURLConnection connection = (HttpURLConnection) this.getRequest().getUri().openConnection();
                connection.setRequestProperty("Content-Type", this.getRequest().getContentType().toString());
                connection.setRequestMethod(this.getRequest().getMethod().toString());

                // Add the security to the request if it exists.

                // Enable the request to override the context authorization
                if (request.getAuthorization() == null) {
                    if (Context.getCurrent() != null && Context.getCurrent().getAuthorization() != null) {
                        connection.setRequestProperty("Authorization", Context.getCurrent().getAuthorization().createHeaderValue());
                    }
                }
                else
                    connection.setRequestProperty("Authorization", request.getAuthorization().createHeaderValue());


                byte[] data = this.getRequest().getData().toByteArray();
                if (data.length > 0) {
                    connection.getOutputStream().write(data);
                    connection.getOutputStream().flush();
                }
                // NUll this out to release memory
                data = null;

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024]; // Adjust if you want
                int bytesRead;
                while ((bytesRead = connection.getInputStream().read(buffer)) != -1)
                {
                    stream.write(buffer, 0, bytesRead);
                }
                stream.close();

                HttpResponse response = new HttpResponse(connection.getResponseCode(), stream);
                connection.disconnect();

                return response;
            } catch (Exception ex) {
                return new HttpResponse(com.inMotion.core.Error.error(100, ex.getMessage(), "HttpConnection.send"));
            }
        }

        @Override
        protected void onPostExecute(HttpResponse result) {
            if (this.getDelegate() != null) {
                if (result.getError() != null) {
                    this.getDelegate().didReceiveError(this.getRequest(), result.getError());
                }
                else {
                    this.getDelegate().didReceiveResponse(this.getRequest(), result.getStatusCode(), result.getContent());
                }
            }
        }

        public HttpRequest getRequest() {
            return request;
        }

        public void setRequest(HttpRequest request) {
            this.request = request;
        }

        public IHttpConnectionDelegate getDelegate() {
            return delegate;
        }

        public void setDelegate(IHttpConnectionDelegate delegate) {
            this.delegate = delegate;
        }
    }
}
