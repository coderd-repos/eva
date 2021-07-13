package com.eva.api.system;

import com.eva.api.BaseController;
import com.eva.biz.system.SystemUserBiz;
import com.eva.core.annotation.pr.PreventRepeat;
import com.eva.core.annotation.trace.Trace;
import com.eva.core.model.ApiResponse;
import com.eva.core.model.LoginUserInfo;
import com.eva.dao.system.dto.LoginDTO;
import com.eva.dao.system.dto.UpdatePwdDto;
import com.eva.service.system.SystemLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
@Api(tags = "系统功能")
@Trace(exclude = true)
@Slf4j
@RestController
@RequestMapping("/system")
public class SystemController extends BaseController {

    @Autowired
    private SystemUserBiz systemUserBiz;

    @Autowired
    private SystemLoginService systemLoginService;

    @PreventRepeat(limit = 10, lockTime = 10000)
    @ApiOperation("登录")
    @PostMapping("/login")
    public ApiResponse<String> login (@Validated @RequestBody LoginDTO dto, HttpServletRequest request) {
        return ApiResponse.success(systemLoginService.loginByPassword(dto, request));
    }

    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public ApiResponse logout () {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ApiResponse.success(null);
    }

    @Trace(withRequestParameters = false)
    @ApiOperation("修改当前用户密码")
    @PostMapping("/updatePwd")
    public ApiResponse updatePwd (@Validated @RequestBody UpdatePwdDto dto) {
        dto.setUserId(this.getLoginUser().getId());
        systemUserBiz.updatePwd(dto);
        return ApiResponse.success(null);
    }

    @ApiOperation("获取当前登录的用户信息")
    @GetMapping("/getUserInfo")
    public ApiResponse<LoginUserInfo> getUserInfo () {
        return ApiResponse.success(this.getLoginUser());
    }
}
