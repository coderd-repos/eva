package com.eva.api;

import com.eva.core.model.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;

/**
 * Controller基类
 * @author Eva
 * @date 2021/03/26 19:48
 */
@Slf4j
public class BaseController {

    /**
     * 获取当前登录用户
     * @author Eva
     * @date 2021-03-28 15:35
     */
    protected LoginUserInfo getLoginUser () {
        return (LoginUserInfo)SecurityUtils.getSubject().getPrincipal();
    }

}