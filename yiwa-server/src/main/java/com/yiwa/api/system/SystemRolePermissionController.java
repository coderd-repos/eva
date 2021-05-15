package com.yiwa.api.system;

import com.yiwa.api.BaseController;
import com.yiwa.core.model.ApiResponse;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.model.SystemRolePermission;
import com.yiwa.service.system.SystemRolePermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 角色权限关联接口
 * @author Caesar Liu
 * @date 2021/05/15 19:41
 */
@RestController
@RequestMapping("/systemRolePermission")
@Api(tags = "角色权限关联接口")
public class SystemRolePermissionController extends BaseController {

    @Autowired
    private SystemRolePermissionService systemRolePermissionService;
}