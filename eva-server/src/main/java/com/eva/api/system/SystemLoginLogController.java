package com.eva.api.system;

import com.eva.api.BaseController;
import com.eva.core.annotation.excel.ExcelExporter;
import com.eva.core.constants.ResponseStatus;
import com.eva.core.exception.BusinessException;
import com.eva.core.model.ApiResponse;
import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.dto.QuerySystemLoginLogDTO;
import com.eva.dao.system.model.SystemLoginLog;
import com.eva.service.system.SystemLoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Eva.Caesar Liu
 * @date 2021/05/30 22:54
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
    public ApiResponse findPage (@RequestBody PageWrap<QuerySystemLoginLogDTO> pageWrap) {
        return ApiResponse.success(systemLoginLogService.findPage(pageWrap));
    }

    @PostMapping("/export")
    @ApiOperation("导出")
    public void export (@RequestBody PageWrap<QuerySystemLoginLogDTO> pageWrap, HttpServletResponse response) {
        PageData<SystemLoginLog> pageData = systemLoginLogService.findPage(pageWrap);
        try {
            ExcelExporter.build(SystemLoginLog.class).export(pageData.getRecords(), "登录日志", "MySheet", response);
        } catch (Exception e) {
            throw new BusinessException(ResponseStatus.SERVER_ERROR, e);
        }
    }
}