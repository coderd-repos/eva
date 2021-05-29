package com.eva.api.system;

import com.eva.api.BaseController;
import com.eva.core.model.ApiResponse;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.model.SystemTraceLog;
import com.eva.service.system.SystemTraceLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Eva
 * @date 2021/05/29 13:53
 */
@RestController
@RequestMapping("/system/traceLog")
@Api(tags = "跟踪日志接口")
public class SystemTraceLogController extends BaseController {

    @Autowired
    private SystemTraceLogService systemTraceLogService;

    /**
     * 分页查询
     * @author Eva
     * @date 2021/05/29 13:53
     */
    @PostMapping("/page")
    @ApiOperation("分页查询")
    public ApiResponse findPage (@RequestBody PageWrap<SystemTraceLog> pageWrap) {
        return ApiResponse.success(systemTraceLogService.findPage(pageWrap));
    }
}