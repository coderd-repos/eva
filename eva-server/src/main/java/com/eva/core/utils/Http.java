package com.eva.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eva.core.model.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Http工具类
 * @author Eva
 * @date 2021-05-31 15:24
 */
@Slf4j
class Http {

    /**
     * 获取Http连接
     * @param url url 请求地址
     *
     * @return HttpWrap
     */
    public HttpWrap build(String url) throws IOException {
        return this.build(url, "UTF-8");
    }

    public HttpWrap build(String url, String charset) throws IOException {
        if (url == null) {
            throw new NullPointerException("url can not be null");
        }
        URL urlObj = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlObj.openConnection();
        httpURLConnection.setRequestProperty("ContentType", charset);
        return new HttpWrap(url, charset, httpURLConnection);
    }

    @AllArgsConstructor
    public static class HttpWrap {

        private String url;

        private String charset;

        private HttpURLConnection connection;

        public HttpWrap setRequestProperty (String key, String value) {
            connection.setRequestProperty(key, value);
            return this;
        }

        public HttpWrap setConnectTimeout (int timeout) {
            connection.setConnectTimeout(timeout);
            return this;
        }

        public HttpWrap setReadTimeout (int timeout) {
            connection.setReadTimeout(timeout);
            return this;
        }

        public HttpResult get () throws IOException {
            log.trace("Eva::Util::Http send http request by method GET, url=" + url);
            connection.setRequestMethod(HttpMethod.GET.toString());
            return new HttpResult(connection.getInputStream(), charset);
        }

        public HttpResult get (Map<String, String> paramsMap) throws IOException {
            if (paramsMap == null || paramsMap.size() == 0) {
                return get();
            }
            List<String> params = new ArrayList<>();
            for (String paramName : paramsMap.keySet()) {
                params.add(paramName + "=" + paramsMap.get(paramName));
            }
            url = url + "?" + StringUtils.join(params, "&");
            return get();
        }

        public HttpResult post () throws IOException {
            log.trace("Eva::Util::Http send http request by method POST, url=" + url);
            connection.setRequestMethod(HttpMethod.POST.toString());
            return new HttpResult(connection.getInputStream(), charset);
        }

        public HttpResult post (String params) throws IOException {
            log.trace("Eva::Util::Http send http request by method POST, url=" + url);
            OutputStreamWriter streamWriter = null;
            try {
                connection.setRequestMethod(HttpMethod.POST.toString());
                if (params != null || "".equals(params.trim())) {
                    connection.setDoInput(Boolean.TRUE);
                    connection.setDoOutput(Boolean.TRUE);
                    streamWriter = new OutputStreamWriter(connection.getOutputStream(), charset);
                    streamWriter.write(params);
                    streamWriter.flush();
                }
                return new HttpResult(connection.getInputStream(), charset);
            } finally {
                if (streamWriter != null) {
                    streamWriter.close();
                }
            }
        }

        public HttpResult postJSON(Map<String, Object> paramsMap) throws IOException {
            if (paramsMap == null || paramsMap.size() == 0) {
                return post();
            }
            return setRequestProperty("Content-Type", "application/json").post(JSON.toJSONString(paramsMap));
        }

    }

    @Getter
    @AllArgsConstructor
    public static class HttpResult {

        private InputStream inputStream;

        private String charset;

        public JSONObject toJSONObject () throws IOException {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(inputStream, charset));
                StringBuilder result = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return JSONObject.parseObject(result.toString());
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }

        public <T> T toClass (Class<T> clazz) throws IOException {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(inputStream, charset));
                StringBuilder result = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return JSONObject.parseObject(result.toString(), clazz);
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException{
        Map<String, Object> paramsMap = new HashMap<String, Object>(){{
            put("username", "");
            put("password", "");
        }};
        System.out.println(
            new Http()
                .build("http://localhost:8080/system/login")
                .postJSON(paramsMap)
                .toClass(ApiResponse.class)
        );
    }
}
