package com.yiwa.service.common;

import com.yiwa.core.constants.Constants;
import com.yiwa.core.model.BusinessException;
import com.yiwa.core.model.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 图片验证码
 * @author Yiwa
 * @date 2021-05-20 19:17
 */
@Service
public class CaptchaService {

    // 验证码字符集
    private static final char[] CODES = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J',
            'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'a', 'b', 'c', 'd', 'e', 'f', 'h', 'j',
            'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', '3', '4', '5', '6', '7', '8' };

    // 验证码图片宽度
    private static final Integer WIDTH = 90;

    // 验证码图片高度
    private static final Integer HEIGHT = 30;

    /**
     * 生成验证码图片
     * @author Yiwa
     * @date 2021-05-20 18:04
     */
    public Captcha genCaptcha () {
        // 定义图像buffer
        BufferedImage buffImg = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics gd = buffImg.getGraphics();
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色
        gd.setColor(Color.decode("#FFFFFF"));
        gd.fillRect(0, 0, WIDTH, HEIGHT);
        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Dialog", 1, 23);
        // 设置字体
        gd.setFont(font);
        // 画边框
        // gd.setColor(Color.decode("#E6BA09"));
        gd.drawRect(0, 0, WIDTH, HEIGHT);
        // 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
        gd.setColor(Color.decode("#990000"));
        for (int i = 0; i < 30; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            gd.drawLine(x, y, x + xl, y + yl);
        }
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();
        int red, green, blue;
        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < 4; i++) {
            // 得到随机产生的验证码数字。
            String code = String.valueOf(CODES[random.nextInt(CODES.length)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);

            // 用随机产生的颜色将验证码绘制到图像中。
            gd.setColor(new Color(red, green, blue));
            gd.drawString(code, (i + 1) * 15, 24);

            // 将产生的四个随机数组合在一起。
            randomCode.append(code);
        }
        return new Captcha(randomCode.toString(), buffImg);
    }

    /**
     * 校验验证码
     * @author Yiwa
     * @date 2021-05-20 22:09
     */
    public void check(String code, HttpServletRequest request) {
        Object sessionCode = request.getSession().getAttribute(Constants.VERIFICATION_CODE_KEY);
        request.getSession().removeAttribute(Constants.VERIFICATION_CODE_KEY);
        if (sessionCode == null || !code.equalsIgnoreCase(sessionCode.toString())) {
            throw new BusinessException(ResponseStatus.VERIFICATION_CODE_INCORRECT.getCode(), ResponseStatus.VERIFICATION_CODE_INCORRECT.getMessage());
        }
    }

    /**
     * 验证码对象
     * @author Yiwa
     * @date 2021-05-20 18:01
     */
    @Data
    @AllArgsConstructor
    public static class Captcha {

        private String text;

        private BufferedImage image;
    }
}
