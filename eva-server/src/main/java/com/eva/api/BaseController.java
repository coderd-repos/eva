package com.eva.api;

import com.eva.core.model.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;

/**
 * Controller基类
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
@Slf4j
public class BaseController {

    /**
     * 获取当前登录用户
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    protected LoginUserInfo getLoginUser () {
        return (LoginUserInfo)SecurityUtils.getSubject().getPrincipal();
    }

}
