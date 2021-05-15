package com.yiwa.api.system;

import com.yiwa.api.BaseController;
import com.yiwa.core.model.ApiResponse;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.model.SystemUserRole;
import com.yiwa.service.system.SystemUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户角色关联接口
 * @author Caesar Liu
 * @date 2021/05/15 19:41
 */
@RestController
@RequestMapping("/systemUserRole")
@Api(tags = "用户角色关联接口")
public class SystemUserRoleController extends BaseController {

    @Autowired
    private SystemUserRoleService systemUserRoleService;
}