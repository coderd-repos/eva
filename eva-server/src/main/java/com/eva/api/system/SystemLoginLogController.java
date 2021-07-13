package com.eva.api.system;

import com.eva.api.BaseController;
import com.eva.core.annotation.excel.ExcelExporter;
import com.eva.core.model.ApiResponse;
import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.dto.QuerySystemLoginLogDTO;
import com.eva.dao.system.model.SystemLoginLog;
import com.eva.service.system.SystemLoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;    
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
@RestController
@RequestMapping("/system/loginLog")
@Api(tags = "登录日志")
public class SystemLoginLogController extends BaseController {

    @Autowired
    private SystemLoginLogService systemLoginLogService;

    @PostMapping("/page")
    @ApiOperation("分页查询")
    @RequiresPermissions("system:loginLog:query")
    public ApiResponse<PageData<SystemLoginLog>> findPage (@RequestBody PageWrap<QuerySystemLoginLogDTO> pageWrap) {
        return ApiResponse.success(systemLoginLogService.findPage(pageWrap));
    }

    @PostMapping("/exportExcel")
    @ApiOperation("导出Excel")
    @RequiresPermissions("system:loginLog:query")
    public void export (@RequestBody PageWrap<QuerySystemLoginLogDTO> pageWrap, HttpServletResponse response) {
        ExcelExporter.build(SystemLoginLog.class).export(systemLoginLogService.findPage(pageWrap).getRecords(), "登录日志", response);
    }
}
