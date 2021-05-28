package com.eva.service.system.impl;

import com.eva.core.exception.BusinessException;
import com.eva.core.model.ResponseStatus;
import com.eva.dao.system.dto.LoginDTO;
import com.eva.service.common.CaptchaService;
import com.eva.service.proxy.CacheProxy;
import com.eva.service.system.SystemLoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SystemLoginServiceImpl implements SystemLoginService {

    @Autowired
    private CaptchaService captchaService;

    @Override
    public String login(LoginDTO dto) {
        // 校验验证码
        captchaService.check(dto.getUuid(), dto.getCode());
        // 校验用户名和密码
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(dto.getUsername(), dto.getPassword());
        try {
            subject.login(token);
            return (String)subject.getSession().getId();
        } catch (AuthenticationException e) {
            log.error(ResponseStatus.ACCOUNT_INCORRECT.getMessage(), e);
            throw new BusinessException(ResponseStatus.ACCOUNT_INCORRECT);
        }
    }
}
