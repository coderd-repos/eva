package com.yiwa.dao.system.vo;

import com.yiwa.dao.system.model.SystemDictData;
import com.yiwa.dao.system.model.SystemUser;
import lombok.Data;

/**
 * 字典数据列表视图对象
 * @author Yiwa
 * @date 2021-05-16 20:43
 */
@Data
public class SystemDictDataListVO extends SystemDictData {

    private SystemUser createUserInfo;

    private SystemUser updateUserInfo;
}
