package com.eva.api.common;

import com.eva.api.BaseController;
import com.eva.core.annotation.trace.Trace;
import com.eva.core.model.ApiResponse;
import com.eva.core.utils.OSS;
import com.eva.core.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Eva.Caesar Liu
 * @date 2021-07-09 09:58
 */
@Trace(exclude = true)
@Api(tags = "上传文件")
@RestController
@RequestMapping("/upload")
public class FileUploadController extends BaseController {

    @ApiOperation("上传图片")
    @PostMapping("/image")
    public ApiResponse<OSS.UploadResult> uploadImage(MultipartFile file) {
        return ApiResponse.success(Utils.OSS.setMaxSize(5).uploadImage(file, "avatar"));
    }

    @ApiOperation("上传文件")
    @PostMapping("/attach")
    public ApiResponse<OSS.UploadResult> uploadFile(MultipartFile file) {
        return ApiResponse.success(Utils.OSS.upload(file));
    }
}
