package com.eva.core.servlet;

import lombok.Getter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * 增加请求流副本
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
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
