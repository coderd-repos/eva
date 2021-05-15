package com.yiwa.api.system;

import com.yiwa.api.BaseController;
import com.yiwa.core.model.ApiResponse;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.model.SystemPermission;
import com.yiwa.service.system.SystemPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统权限接口
 * @author Caesar Liu
 * @date 2021/05/15 19:41
 */
@RestController
@RequestMapping("/systemPermission")
@Api(tags = "系统权限接口")
public class SystemPermissionController extends BaseController {

    @Autowired
    private SystemPermissionService systemPermissionService;
}