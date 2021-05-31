package com.eva.service.system.impl;

import com.eva.core.exception.BusinessException;
import com.eva.core.model.LoginUserInfo;
import com.eva.core.model.ResponseStatus;
import com.eva.core.utils.RequestHeaderUtil;
import com.eva.core.utils.ServerUtil;
import com.eva.core.utils.Util;
import com.eva.dao.system.dto.LoginDTO;
import com.eva.dao.system.model.SystemLoginLog;
import com.eva.service.common.CaptchaService;
import com.eva.service.system.SystemLoginLogService;
import com.eva.service.system.SystemLoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@Service
public class SystemLoginServiceImpl implements SystemLoginService {

    @Value("${project.version}")
    private String systemVersion;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private SystemLoginLogService systemLoginLogService;

    @Override
    public String loginByPassword(LoginDTO dto, HttpServletRequest request) {
        SystemLoginLog loginLog = new SystemLoginLog();
        loginLog.setClientInfo(RequestHeaderUtil.getClientInfo(request));
        loginLog.setOsInfo(RequestHeaderUtil.getOsInfo(request));
        loginLog.setIp(RequestHeaderUtil.getRequestIp(request));
        loginLog.setLoginUsername(dto.getUsername());
        loginLog.setLoginTime(new Date());
        loginLog.setPlatform(dto.getPlatform());
        loginLog.setSystemVersion(systemVersion);
        loginLog.setServerIp(ServerUtil.getIpAddress());
        loginLog.setLocation(Util.LOCATION.getLocationString(loginLog.getIp()));
        // 校验验证码
        try {
            captchaService.check(dto.getUuid(), dto.getCode());
        } catch (Exception e) {
            loginLog.setReason(e.getMessage());
            loginLog.setSuccess(Boolean.FALSE);
            systemLoginLogService.create(loginLog);
            throw e;
        }
        // 校验用户名和密码
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(dto.getUsername(), dto.getPassword());
        try {
            subject.login(token);
            loginLog.setUserId(((LoginUserInfo)subject.getPrincipal()).getId());
            loginLog.setSuccess(Boolean.TRUE);
            systemLoginLogService.create(loginLog);
            return (String)subject.getSession().getId();
        } catch (AuthenticationException e) {
            log.error(ResponseStatus.ACCOUNT_INCORRECT.getMessage(), e);
            loginLog.setReason(e.getMessage());
            loginLog.setSuccess(Boolean.FALSE);
            systemLoginLogService.create(loginLog);
            throw new BusinessException(ResponseStatus.ACCOUNT_INCORRECT);
        }
    }
}
