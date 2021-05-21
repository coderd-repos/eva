package com.yiwa.dao.system.vo;

import com.yiwa.dao.system.model.SystemDict;
import com.yiwa.dao.system.model.SystemUser;
import lombok.Data;

/**
 * 字典列表视图对象
 * @author Yiwa
 * @date 2021-05-16 20:07
 */
@Data
public class SystemDictListVO extends SystemDict {

    private SystemUser createUserInfo;

    private SystemUser updateUserInfo;
}
