package com.yiwa.api.system;

import com.yiwa.api.BaseController;
import com.yiwa.core.model.ApiResponse;
import com.yiwa.core.model.monitor.Monitor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yiwa
 * @date 2021-04-13 23:25
 */
@Api(tags = "系统监听接口")
@Slf4j
@RequestMapping("/system/monitor")
@RestController
public class SystemMonitorController extends BaseController {

    /**
     * @author Yiwa
     * @date 2021-04-13 22:42
     */
    @RequiresPermissions("system:monitor:query")
    @ApiOperation("获取系统信息")
    @GetMapping("/getSystemInfo")
    public ApiResponse getSystemInfo () {
        return ApiResponse.success(Monitor.getSystemInfo());
    }
}
