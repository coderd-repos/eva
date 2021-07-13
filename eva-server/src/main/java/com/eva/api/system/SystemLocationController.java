package com.eva.api.system;

import com.eva.api.BaseController;
import com.eva.core.annotation.pr.PreventRepeat;
import com.eva.core.annotation.trace.Trace;
import com.eva.core.constants.OperaType;
import com.eva.core.model.ApiResponse;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.model.SystemLocation;
import com.eva.service.system.SystemLocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;    
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
@RestController
@RequestMapping("/system/location")
@Api(tags = "地区")
public class SystemLocationController extends BaseController {

    @Autowired
    private SystemLocationService locationService;

    @PreventRepeat
    @ApiOperation("新建")
    @PostMapping("/create")
    @RequiresPermissions("system:location:create")
    public ApiResponse create(@Validated(OperaType.Create.class) @RequestBody SystemLocation location) {
        return ApiResponse.success(locationService.create(location));
    }

    @ApiOperation("修改")
    @PostMapping("/updateById")
    @RequiresPermissions("system:location:update")
    public ApiResponse updateById(@Validated(OperaType.Update.class) @RequestBody SystemLocation location) {
        locationService.updateById(location);
        return ApiResponse.success(null);
    }

    @ApiOperation("修改禁用状态")
    @PostMapping("/updateStatus")
    @RequiresPermissions("system:location:update")
    public ApiResponse updateStatus(@Validated(OperaType.UpdateStatus.class) @RequestBody SystemLocation location) {
        locationService.updateById(location);
        return ApiResponse.success(null);
    }

    @ApiOperation("分页查询")
    @PostMapping("/page")
    @RequiresPermissions("system:location:query")
    public ApiResponse findPage (@RequestBody PageWrap<SystemLocation> pageWrap) {
        return ApiResponse.success(locationService.findPage(pageWrap));
    }

    @Trace(exclude = true)
    @ApiOperation("查询子地区")
    @GetMapping("/children/{parentId}")
    public ApiResponse findPage (@PathVariable Integer parentId) {
        SystemLocation queryDto = new SystemLocation();
        if (parentId == -1) {
            queryDto.setLevel((byte)1);
        } else {
            queryDto.setParentId(parentId);
        }
        queryDto.setDisabled(Boolean.FALSE);
        return ApiResponse.success(locationService.findList(queryDto));
    }
}
