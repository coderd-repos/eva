package com.eva.service.system;

import com.eva.dao.system.dto.LoginDTO;

/**
 * 系统登录
 * @author Caesar Liu
 * @date 2021-05-28 00:05
 */
public interface SystemLoginService {

    /**
     * 登录
     * @author Caesar Liu
     * @date 2021-05-28 00:05
     */
    String login (LoginDTO dto);
}
