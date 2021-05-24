package com.eva.api.system;

import com.eva.api.BaseController;
import com.eva.biz.system.SystemUserBiz;
import com.eva.core.model.ApiResponse;
import com.eva.core.model.LoginUserInfo;
import com.eva.dao.system.dto.LoginDTO;
import com.eva.dao.system.dto.UpdatePwdDto;
import com.eva.service.common.CaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eva
 * @date 2021-05-21 22:10
 */
@Api(tags = "系统接口")
@Slf4j
@RestController
@RequestMapping("/system")
public class SystemController extends BaseController {

    @Value("${session.timeout}")
    private Long sessionTimeout;

    @Autowired
    private SystemUserBiz systemUserBiz;

    @Autowired
    private CaptchaService captchaService;

    /**
     * @author Eva
     * @date 2021-03-27 21:36
     */
    @ApiOperation("登录")
    @PostMapping("/login")
    public ApiResponse<LoginUserInfo> login (@RequestBody LoginDTO dto, HttpServletRequest request) {
        // 校验验证码
        captchaService.check(dto.getCode(), request);
        // 校验用户名和密码
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setTimeout(sessionTimeout);
        UsernamePasswordToken token = new UsernamePasswordToken(dto.getUsername(), dto.getPassword());
        try {
            subject.login(token);
            return ApiResponse.success(this.getLoginUser());
        } catch (AuthenticationException e) {
            log.error("登录失败", e);
            return ApiResponse.failed("用户名或密码错误");
        }
    }

    /**
     * @author Eva
     * @date 2021-03-27 21:36
     */
    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public ApiResponse logout () {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ApiResponse.success(null);
    }

    /**
     * @author Eva
     * @date 2021-03-31 14:16
     */
    @ApiOperation("修改密码")
    @PostMapping("/updatePwd")
    public ApiResponse updatePwd (@RequestBody UpdatePwdDto dto) {
        dto.setUserId(this.getLoginUser().getId());
        systemUserBiz.updatePwd(dto);
        return ApiResponse.success(null);
    }

    /**
     * @author Eva
     * @date 2021-03-28 17:04
     */
    @ApiOperation("获取当前登录的用户信息")
    @GetMapping("/getUserInfo")
    public ApiResponse<LoginUserInfo> getUserInfo () {
        return ApiResponse.success(this.getLoginUser());
    }
}


