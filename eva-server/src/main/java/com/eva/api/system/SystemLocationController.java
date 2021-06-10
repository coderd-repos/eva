package com.eva.api.system;

import com.eva.api.BaseController;
import com.eva.core.annotation.pr.PreventRepeat;
import com.eva.core.annotation.trace.Trace;
import com.eva.core.model.ApiResponse;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.model.SystemLocation;
import com.eva.service.system.SystemLocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;    
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Eva
 * @date 2021/06/10 17:09
 */
@RestController
@RequestMapping("/system/location")
@Api(tags = "地区表")
public class SystemLocationController extends BaseController {

    @Autowired
    private SystemLocationService locationService;

    @PreventRepeat
    @ApiOperation("新建")
    @PostMapping("/create")
    @RequiresPermissions("system:location:create")
    public ApiResponse create(@RequestBody SystemLocation location) {
        return ApiResponse.success(locationService.create(location));
    }

    @ApiOperation("根据ID修改")
    @PostMapping("/updateById")
    @RequiresPermissions("system:location:update")
    public ApiResponse updateById(@RequestBody SystemLocation location) {
        locationService.updateById(location);
        return ApiResponse.success(null);
    }

    @ApiOperation("根据ID修改")
    @PostMapping("/updateStatus")
    @RequiresPermissions("system:location:update")
    public ApiResponse updateStatus(@RequestBody SystemLocation location) {
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
    @ApiOperation("根据父ID查询")
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
