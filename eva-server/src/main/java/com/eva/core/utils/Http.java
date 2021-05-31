package com.eva.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Http工具类
 * @author Eva.Caesar Liu
 * @date 2021-05-31 15:24
 */
@Slf4j
class Http {

    /**
     * 获取Http连接
     * @param url 请求地址
     *
     * @return HttpWrap
     */
    public HttpWrap build(String url) throws IOException {
        return this.build(url, "UTF-8");
    }

    /**
     * 获取Http连接
     * @param url 请求地址
     * @param charset 编码，默认UTF-8
     *
     * @return HttpWrap
     */
    public HttpWrap build(String url, String charset) throws IOException {
        if (url == null) {
            throw new NullPointerException("url can not be null");
        }
        URL urlObj = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlObj.openConnection();
        httpURLConnection.setRequestProperty("ContentType", charset);
        return new HttpWrap(url, charset, httpURLConnection);
    }

    @Getter
    public static class HttpWrap {

        private String url;

        private String charset;

        private HttpURLConnection connection;

        private boolean gzip;

        public HttpWrap (String url, String charset, HttpURLConnection connection) {
            this.url = url;
            this.charset = charset;
            this.connection = connection;
        }

        /**
         * 开启GZIP压缩
         */
        public HttpWrap gzip () {
            this.gzip = Boolean.TRUE;
            return this;
        }

        /**
         * 设置请求属性
         * @param key 属性
         * @param value 属性值
         *
         * @return HttpWrap
         */
        public HttpWrap setRequestProperty (String key, String value) {
            connection.setRequestProperty(key, value);
            return this;
        }

        /**
         * 设置连接超时时间
         * @param timeout 连接超时时间
         *
         * @return HttpWrap
         */
        public HttpWrap setConnectTimeout (int timeout) {
            connection.setConnectTimeout(timeout);
            return this;
        }

        /**
         * 设置读取超时时间
         * @param timeout 读取超时时间
         *
         * @return HttpWrap
         */
        public HttpWrap setReadTimeout (int timeout) {
            connection.setReadTimeout(timeout);
            return this;
        }

        /**
         * 发送GET请求
         *
         * @return HttpResult
         */
        public HttpResult get () throws IOException {
            log.trace("Eva::Util::Http send http request by method GET, url=" + url);
            connection.setRequestMethod(HttpMethod.GET.toString());
            return new HttpResult(connection.getInputStream(), charset, gzip);
        }

        /**
         * 发送POST请求
         *
         * @return HttpResult
         */
        public HttpResult post () throws IOException {
            log.trace("Eva::Util::Http send http request by method POST, url=" + url);
            connection.setRequestMethod(HttpMethod.POST.toString());
            return new HttpResult(connection.getInputStream(), charset, gzip);
        }

        /**
         * 发送POST请求
         * @param params 请求参数
         *
         * @return HttpResult
         */
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
                return new HttpResult(connection.getInputStream(), charset, gzip);
            } finally {
                if (streamWriter != null) {
                    streamWriter.close();
                }
            }
        }

        /**
         * 发送POST请求，请求参数类型为JSON
         * @param paramsMap 请求参数
         *
         * @return HttpResult
         */
        public HttpResult postJSON(Map<String, Object> paramsMap) throws IOException {
            setRequestProperty("Content-Type", "application/json");
            if (paramsMap == null || paramsMap.size() == 0) {
                return post();
            }
            return post(JSON.toJSONString(paramsMap));
        }

        /**
         * 发送POST请求，请求参数类型为JSON
         * @param paramJSONObject 请求参数
         *
         * @return HttpResult
         */
        public HttpResult postJSON(JSONObject paramJSONObject) throws IOException {
            setRequestProperty("Content-Type", "application/json");
            if (paramJSONObject == null || paramJSONObject.size() == 0) {
                return post();
            }
            return post(paramJSONObject.toJSONString());
        }

    }

    @Getter
    @AllArgsConstructor
    public static class HttpResult {

        private InputStream inputStream;

        private String charset;

        private boolean gzip;

        /**
         * 转为字符串
         */
        public String toStringResult () throws IOException{
            BufferedReader reader = null;
            InputStream is = inputStream;
            try {
                if (gzip) {
                    is = new GZIPInputStream(inputStream);
                }
                reader = new BufferedReader(new InputStreamReader(is, charset));
                StringBuilder result = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();
            } finally {
                if (reader != null) {
                    reader.close();
                }
                if (is != null) {
                    is.close();
                }
            }
        }

        /**
         * 转为JSONObject对象
         */
        public JSONObject toJSONObject () throws IOException {
            return JSONObject.parseObject(toStringResult());
        }

        /**
         * 转为目标Class对象
         */
        public <T> T toClass (Class<T> clazz) throws IOException {
            return JSONObject.parseObject(toStringResult(), clazz);
        }
    }
}
