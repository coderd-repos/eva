package com.eva.api.system;

import com.eva.api.BaseController;
import com.eva.core.model.ApiResponse;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.model.SystemLoginLog;
import com.eva.service.system.SystemLoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;    
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Eva
 * @date 2021/05/30 22:54
 */
@RestController
@RequestMapping("/system/loginLog")
@Api(tags = "登录日志")
public class SystemLoginLogController extends BaseController {

    @Autowired
    private SystemLoginLogService systemLoginLogService;

    /**
     * 分页查询
     * @author Eva
     * @date 2021/05/30 22:54
     */
    @PostMapping("/page")
    @ApiOperation("分页查询")
    @RequiresPermissions("system:loginLog:query")
    public ApiResponse findPage (@RequestBody PageWrap<SystemLoginLog> pageWrap) {
        return ApiResponse.success(systemLoginLogService.findPage(pageWrap));
    }
}