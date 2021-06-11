package com.eva.api.system;

import com.eva.api.BaseController;
import com.eva.core.annotation.pr.PreventRepeat;
import com.eva.core.model.ApiResponse;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.model.SystemDataPermission;
import com.eva.service.system.SystemDataPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;    
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eva.Caesar Liu
 * @date 2021/06/11 20:28
 */
@RestController
@RequestMapping("/system/dataPermission")
@Api(tags = "数据权限配置")
public class SystemDataPermissionController extends BaseController {

    @Autowired
    private SystemDataPermissionService systemDataPermissionService;

    @PreventRepeat
    @ApiOperation("新建")
    @PostMapping("/create")
    @RequiresPermissions("system:datapermission:create")
    public ApiResponse create(@RequestBody SystemDataPermission systemDataPermission) {
        return ApiResponse.success(systemDataPermissionService.create(systemDataPermission));
    }

    @ApiOperation("根据ID删除")
    @GetMapping("/delete/{id}")
    @RequiresPermissions("system:datapermission:delete")
    public ApiResponse deleteById(@PathVariable Integer id) {
        systemDataPermissionService.deleteById(id);
        return ApiResponse.success(null);
    }

    @ApiOperation("批量删除")
    @GetMapping("/delete/batch")
    @RequiresPermissions("system:datapermission:delete")
    public ApiResponse deleteByIdInBatch(@RequestParam String ids) {
        String [] idArray = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String id : idArray) {
            idList.add(Integer.valueOf(id));
        }
        systemDataPermissionService.deleteByIdInBatch(idList);
        return ApiResponse.success(null);
    }

    @ApiOperation("根据ID修改")
    @PostMapping("/updateById")
    @RequiresPermissions("system:datapermission:update")
    public ApiResponse updateById(@RequestBody SystemDataPermission systemDataPermission) {
        systemDataPermissionService.updateById(systemDataPermission);
        return ApiResponse.success(null);
    }

    @ApiOperation("分页查询")
    @PostMapping("/page")
    @RequiresPermissions("system:datapermission:query")
    public ApiResponse findPage (@RequestBody PageWrap<SystemDataPermission> pageWrap) {
        return ApiResponse.success(systemDataPermissionService.findPage(pageWrap));
    }
}
