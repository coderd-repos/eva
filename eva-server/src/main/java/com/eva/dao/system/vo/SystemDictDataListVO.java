package com.eva.dao.system.vo;

import com.eva.dao.system.model.SystemDictData;
import com.eva.dao.system.model.SystemUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Caesar Liu
 * @date 2021-05-16 20:43
 */
@Data
@ApiModel("字典数据列表视图对象")
public class SystemDictDataListVO extends SystemDictData {

    @ApiModelProperty(value = "创建人信息")
    private SystemUser createUserInfo;

    @ApiModelProperty(value = "更新人信息")
    private SystemUser updateUserInfo;
}
