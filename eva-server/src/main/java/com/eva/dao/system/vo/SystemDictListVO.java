package com.eva.dao.system.vo;

import com.eva.dao.system.model.SystemDict;
import com.eva.dao.system.model.SystemUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Eva.Caesar Liu
 * @date 2021-05-16 20:07
 */
@Data
@ApiModel("字典列表视图对象")
public class SystemDictListVO extends SystemDict {

    @ApiModelProperty(value = "创建人信息")
    private SystemUser createUserInfo;

    @ApiModelProperty(value = "更新人信息")
    private SystemUser updateUserInfo;
}
