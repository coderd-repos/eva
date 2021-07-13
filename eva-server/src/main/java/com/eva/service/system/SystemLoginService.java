package com.eva.service.system;

import com.eva.dao.system.dto.LoginDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统登录
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
public interface SystemLoginService {

    /**
     * 密码登录
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    String loginByPassword (LoginDTO dto, HttpServletRequest request);
}
