package com.eva.core.servlet;

import lombok.Getter;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;

/**
 * 增加请求流副本
 * 技术参考：https://blog.csdn.net/AlbenXie/article/details/114868245
 * @author Eva
 * @date 2021-05-25 16:13
 */
@Getter
public class ServletDuplicateRequestWrapper extends HttpServletRequestWrapper {

    private ServletDuplicateInputStream servletDuplicateInputStream;

    public ServletDuplicateRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException{
        if (servletDuplicateInputStream == null) {
            servletDuplicateInputStream = new ServletDuplicateInputStream(super.getInputStream());
        }
        return servletDuplicateInputStream;
    }

}
