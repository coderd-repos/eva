package com.eva.core.servlet;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 包含副本的输出流
 * @author Eva.Caesar Liu
 * @date 2021-05-29 21:19
 */
public class ServletDuplicateOutputStream extends ServletOutputStream {

    private ServletOutputStream stream;

    private ByteArrayOutputStream duplicate;

    public ServletDuplicateOutputStream(ServletOutputStream servletOutputStream)  {
        this.stream = servletOutputStream;
        this.duplicate = new ByteArrayOutputStream();
    }

    @Override
    public boolean isReady() {
        return stream.isReady();
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        stream.setWriteListener(writeListener);
    }

    @Override
    public void write(byte[] b) throws IOException {
        stream.write(b);
        duplicate.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        stream.write(b, off, len);
        duplicate.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        stream.flush();
        duplicate.flush();
    }

    @Override
    public void close() throws IOException {
        stream.close();
        duplicate.close();
    }

    @Override
    public void write(int b) throws IOException {
        stream.write(b);
        duplicate.write(b);
    }

    public String getContent() {
        return duplicate.toString();
    }
}
