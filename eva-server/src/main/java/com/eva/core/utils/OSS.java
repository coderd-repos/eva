package com.eva.core.utils;

import com.eva.core.constants.ResponseStatus;
import com.eva.core.exception.BusinessException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * OSS工具类
 * @author Eva.Caesar Liu
 * @date 2021-07-08 18:04
 */
@Slf4j
@Component
public class OSS {

    @Value("${oss.access-prefix}")
    private String accessPrefix;

    // 文件大小限制
    private ThreadLocal<Integer> maxSize = new ThreadLocal<>();

    // 文件类型限制
    private ThreadLocal<String[]> fileTypes = new ThreadLocal<>();

    @Autowired
    private Qiniu qiniu;

    @Autowired
    private AliOSS aliOSS;

    /**
     * 上传图片
     *
     * @param imageFile 文件
     * @return 文件访问路径
     */
    public UploadResult uploadImage(MultipartFile imageFile) {
        return this.uploadImage(imageFile, null);
    }

    /**
     * 上传图片
     *
     * @param imageFile 文件
     * @param businessPath 业务路径，如使用"avatar"表示用户头像路径，"goods/cover"表示商品封面图片等
     * @return 文件访问路径
     */
    public UploadResult uploadImage(MultipartFile imageFile, String businessPath) {
        try {
            // 设置图片文件默认类型限制
            if (this.fileTypes.get() == null || this.fileTypes.get().length == 0) {
                this.setFileTypes(".jpg,.jpeg,.png,.gif");
            }
            // 验证文件
            this.checkUpload(imageFile);
            // 执行上传
            String fileKey = this.doUpload(imageFile, businessPath);
            // 返回上传结果
            if (StringUtils.isBlank(businessPath)) {
                return new UploadResult(imageFile.getOriginalFilename(), fileKey,accessPrefix + "/image?f=" + fileKey);
            }
            return new UploadResult(imageFile.getOriginalFilename(), fileKey,accessPrefix + "?f=" + fileKey);
        } catch (IOException e) {
            log.error("图片上传失败", e);
            throw new BusinessException(ResponseStatus.SERVER_ERROR.getCode(), "图片上传失败");
        }
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件访问路径
     */
    public UploadResult upload(MultipartFile file) {
        return this.upload(file, null);
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @param businessPath 业务路径，如使用"contract"表示合同文件，"contract/attach"表示合同附件
     * @return 文件访问路径
     */
    public UploadResult upload(MultipartFile file, String businessPath) {
        try {
            // 验证文件
            this.checkUpload(file);
            // 执行上传
            String fileKey = this.doUpload(file, businessPath);
            // 返回上传结果
            if (StringUtils.isBlank(businessPath)) {
                return new UploadResult(file.getOriginalFilename(), fileKey,accessPrefix + "/attach?f=" + fileKey);
            }
            return new UploadResult(file.getOriginalFilename(), fileKey,accessPrefix + "?f=" + fileKey);
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new BusinessException(ResponseStatus.SERVER_ERROR.getCode(), "文件上传失败");
        }
    }

    /**
     * 下载
     *
     * @param fileKey 文件在存储空间中的key
     * @return 流
     */
    public InputStream download(String fileKey) {
//        try {
//            return Utils.Http.build(qiniu.download(fileKey)).get().getInputStream();
//        } catch (FileNotFoundException e) {
//            log.error("文件下载失败，未找到文件！fileKey=" + fileKey, e);
//            throw new BusinessException(ResponseStatus.DATA_EMPTY.getCode(), "未找到目标文件");
//        } catch (IOException e) {
//            log.error("文件下载失败, fileKey=" + fileKey, e);
//            throw new BusinessException(ResponseStatus.SERVER_ERROR.getCode(), "文件下载失败");
//        }
        return aliOSS.download(fileKey);
    }

    /**
     * 设置文件大小限制
     */
    public OSS setMaxSize(int maxSize) {
        this.maxSize.set(maxSize);
        return this;
    }

    /**
     * 设置文件类型限制（多个类型使用","隔开，如".jpg,jpeg,.png"）
     */
    public OSS setFileTypes (String fileTypes) {
        this.fileTypes.set(fileTypes.split(","));
        return this;
    }

    /**
     * @author Eva.Caesar Liu
     * @date 2021-07-09 23:22
     */
    @Data
    @ApiModel("上传结果")
    @AllArgsConstructor
    public static class UploadResult {

        @ApiModelProperty("源文件名称")
        private String originalFilename;

        @ApiModelProperty("文件的key")
        private String fileKey;

        @ApiModelProperty("访问路径/下载路径")
        private String accessUri;
    }

    /**
     * 执行文件上传
     */
    private String doUpload(MultipartFile file, String businessPath) throws IOException {
        String fileKey = StringUtils.isBlank(businessPath) ? file.getOriginalFilename(): (businessPath + "/" + file.getOriginalFilename());
//        qiniu.upload(file, fileKey, null, null);
        aliOSS.upload(file, fileKey);
        return fileKey;
    }

    /**
     * 验证文件上传
     */
    private void checkUpload(MultipartFile file) {
        try {
            // 大小验证
            if (this.maxSize.get() != null) {
                if(this.maxSize.get() * 1024 * 1024 < file.getSize()) {
                    throw new BusinessException(ResponseStatus.NOT_ALLOWED.getCode(), "文件大小超过限制");
                }
            }
            // 格式验证
            if (this.fileTypes.get() != null && this.fileTypes.get().length > 0) {
                // 获取文件名称
                String filename = file.getOriginalFilename();
                if (filename != null) {
                    filename = filename.toLowerCase();
                }
                // 无后缀 && 存在格式限制
                if (filename == null && this.fileTypes.get().length > 0) {
                    throw new BusinessException(ResponseStatus.NOT_ALLOWED.getCode(), "文件格式不正确");
                }
                // 验证是否存在预设的格式
                boolean isFault = true;
                for (String fileType: this.fileTypes.get()) {
                    if (filename.endsWith(fileType.toLowerCase())) {
                        isFault = false;
                        break;
                    }
                }
                if (isFault) {
                    throw new BusinessException(ResponseStatus.NOT_ALLOWED.getCode(), "文件格式不正确");
                }
            }
        } finally {
            // 清理大小限制、类型限制等数据（支持并发上传）
            this.maxSize.set(null);
            this.fileTypes.set(null);
        }
    }
}
