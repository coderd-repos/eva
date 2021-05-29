package com.eva.service.system;

import com.eva.dao.system.dto.LoginDTO;

/**
 * 系统登录
 * @author Eva
 * @date 2021-05-28 00:05
 */
public interface SystemLoginService {

    /**
     * 密码登录
     * @author Eva
     * @date 2021-05-28 00:05
     */
    String loginByPassword (LoginDTO dto);
}
