package com.eva.api.system;

import com.eva.api.BaseController;
import com.eva.core.annotation.trace.Trace;
import com.eva.core.model.ApiResponse;
import com.eva.core.utils.Monitor;
import com.eva.core.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
@Api(tags = "系统监听")
@Trace(exclude = true)
@RestController
@RequestMapping("/system/monitor")
public class SystemMonitorController extends BaseController {

    @ApiOperation("获取系统信息")
    @GetMapping("/getSystemInfo")
    @RequiresPermissions("system:monitor:query")
    public ApiResponse<Monitor> getSystemInfo () {
        return ApiResponse.success(Utils.Monitor.current());
    }
}
