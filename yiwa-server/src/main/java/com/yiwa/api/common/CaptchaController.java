package com.yiwa.api.common;

import com.yiwa.api.BaseController;
import com.yiwa.core.constants.Constants;
import com.yiwa.service.common.CaptchaService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 验证码
 * @author Caesar Liu
 * @date 2021-05-20 17:55
 */
@Api(tags = "验证码接口")
@RestController
@RequestMapping("/common")
public class CaptchaController extends BaseController {

    @Autowired
    private CaptchaService captchaService;

    @GetMapping("/captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CaptchaService.Captcha captcha = captchaService.genCaptcha();
        // 将四位数字的验证码保存到Session中。
        HttpSession session = request.getSession();
        session.setAttribute(Constants.VERIFICATION_CODE_KEY, captcha.getText());
        // 禁止图像缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        // 输出图像
        ServletOutputStream sos = response.getOutputStream();
        ImageIO.write(captcha.getImage(), "jpeg", sos);
        sos.close();
    }
}
