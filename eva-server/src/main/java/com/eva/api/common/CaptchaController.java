package com.eva.api.common;

import com.eva.api.BaseController;
import com.eva.core.annotation.trace.Trace;
import com.eva.core.model.ApiResponse;
import com.eva.service.common.CaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码
 * @author Eva.Caesar Liu
 * @date 2021-05-20 17:55
 */
@Api(tags = "验证码接口")
@Trace(exclude = true)
@RestController
@RequestMapping("/common")
public class CaptchaController extends BaseController {

    @Autowired
    private CaptchaService captchaService;

    /**
     * @author Eva.Caesar Liu
     * @date 2021-05-23 17:11
     */
    @ApiOperation("获取验证码")
    @GetMapping("/captcha")
    public ApiResponse<CaptchaService.Captcha> getCaptcha() {
        return ApiResponse.success(captchaService.genCaptcha());
    }
}
