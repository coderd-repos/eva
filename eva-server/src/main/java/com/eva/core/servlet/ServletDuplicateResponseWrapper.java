package com.eva.core.servlet;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

public class ContainBodyResponseWrapper extends HttpServletResponseWrapper {

    private ServletDuplicateOutputStream servletDuplicateOutputStream;

    public ContainBodyResponseWrapper (HttpServletResponse httpServletResponse) {
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
