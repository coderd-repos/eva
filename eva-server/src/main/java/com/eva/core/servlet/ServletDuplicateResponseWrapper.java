package com.eva.core.servlet;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**
 * 增加响应流副本
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
public class ServletDuplicateResponseWrapper extends HttpServletResponseWrapper {

    private ServletDuplicateOutputStream servletDuplicateOutputStream;

    public ServletDuplicateResponseWrapper(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (servletDuplicateOutputStream == null) {
            servletDuplicateOutputStream = new ServletDuplicateOutputStream(super.getOutputStream());
        }
        return servletDuplicateOutputStream;
    }
}
