package com.eva.core.utils;

import com.alibaba.fastjson.JSON;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * 七牛云工具类
 * @author Eva.Caesar Liu
 * @date 2021-07-08 16:11
 */
@Slf4j
@Component
public class Qiniu {

    /**
     * 上传文件
     *
     * @param file 文件
     * @param fileKey 文件的key
     * @param params 七牛云原生上传方法params参数
     * @param mime 七牛云原生上传方法mime参数
     * @return String
     */
    public String upload(MultipartFile file, String fileKey, StringMap params, String mime) throws IOException {
        return null;
    }

    /**
     * 下载
     *
     * @param fileKey 文件key
     * @return
     */
    public String download(String fileKey) throws UnsupportedEncodingException {
        return null;
    }
}
