package com.gs.test;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.HashMap;

public class HTTPUtils {
    public enum PARAMS {
        RESPONSE_CODE,
        RESPONSE_BODY,
        COOKIES
    }

    public static class HTTPSession {
        BasicCookieStore cookieStore = new BasicCookieStore();

        public HTTPResponse get(HTTPGetRequest getRequest) throws IOException {
            return getRequest.get(cookieStore);
        }

    }

    public static class HTTPResponse {
        private HashMap<PARAMS, String> response;
        protected HTTPResponse() {
            response = new HashMap<PARAMS, String>();
        }

        public int getStatusCode() {
            return Integer.valueOf(response.get(PARAMS.RESPONSE_CODE));
        }

        public String getBody() {
            return response.get(PARAMS.RESPONSE_BODY);
        }

        public String getCookies() {
            return response.get(PARAMS.COOKIES);
        }

        public String get(PARAMS param) {
            return response.get(param);
        }

        public void set(PARAMS param, String value) {
            response.put(param, value);
        }

        @Override
        public String toString() {
            return response.toString();
        }
    }

    public static class HTTPGetRequest {
        private final String _urlAsString;

        public HTTPGetRequest(String urlAsString) {
            _urlAsString = urlAsString;
        }

        protected HTTPResponse get(BasicCookieStore cookieStore) throws IOException {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            httpClient.setCookieStore(cookieStore);
            HttpGet httpGet = new HttpGet(_urlAsString);

            org.apache.http.HttpResponse response = httpClient.execute(httpGet);
            org.apache.http.HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            HTTPResponse result = new HTTPResponse();
            result.set(PARAMS.RESPONSE_BODY, responseString);
            result.set(PARAMS.RESPONSE_CODE, String.valueOf(response.getStatusLine().getStatusCode()));
            return result;
        }
    }
}