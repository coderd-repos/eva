package com.eva.api.system;

import com.eva.api.BaseController;
import com.eva.core.annotation.pr.PreventRepeat;
import com.eva.core.model.ApiResponse;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.model.Location;
import com.eva.service.system.LocationService;
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
public class LocationController extends BaseController {

    @Autowired
    private LocationService locationService;

    @PreventRepeat
    @ApiOperation("新建")
    @PostMapping("/create")
    @RequiresPermissions("system:location:create")
    public ApiResponse create(@RequestBody Location location) {
        return ApiResponse.success(locationService.create(location));
    }

    @ApiOperation("根据ID删除")
    @GetMapping("/delete/{id}")
    @RequiresPermissions("system:location:delete")
    public ApiResponse deleteById(@PathVariable Integer id) {
        locationService.deleteById(id);
        return ApiResponse.success(null);
    }

    @ApiOperation("根据ID修改")
    @PostMapping("/updateById")
    @RequiresPermissions("system:location:update")
    public ApiResponse updateById(@RequestBody Location location) {
        locationService.updateById(location);
        return ApiResponse.success(null);
    }

    @ApiOperation("分页查询")
    @PostMapping("/page")
    @RequiresPermissions("system:location:query")
    public ApiResponse findPage (@RequestBody PageWrap<Location> pageWrap) {
        return ApiResponse.success(locationService.findPage(pageWrap));
    }
}
