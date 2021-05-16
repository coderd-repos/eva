package com.yiwa.dao.system.vo;

import com.yiwa.dao.system.model.SystemDict;
import com.yiwa.dao.system.model.SystemUser;
import lombok.Data;

/**
 * @author Caesar Liu
 * @date 2021-05-16 20:07
 */
@Data
public class SystemDictListVO extends SystemDict {

    private SystemUser createUserInfo;

    private SystemUser updateUserInfo;
}
