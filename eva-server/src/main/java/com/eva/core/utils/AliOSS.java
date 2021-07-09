package com.eva.core.utils;

import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 阿里OSS工具类
 * @author Eva.Caesar Liu
 * @date 2021-07-09 19:48
 */
@Service
public class AliOSS {

    /**
     * 上传文件
     *
     * @param file 文件
     * @param fileKey 文件的key
     */
    public void upload(MultipartFile file, String fileKey) throws IOException {
    }

    /**
     * 下载文件
     *
     * @author Caesar Liu
     * @date 2019-09-25 14:25
     */
    public InputStream download(String fileKey) {
        return null;
    }

}
