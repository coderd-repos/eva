package com.yiwa.api.system;

import com.yiwa.api.BaseController;
import com.yiwa.core.model.ApiResponse;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.dto.QuerySystemPermissionDTO;
import com.yiwa.dao.system.model.SystemPermission;
import com.yiwa.service.system.SystemPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统权限接口
 * @author Caesar Liu
 * @date 2021/03/27 22:36
 */
@RestController
@RequestMapping("/system/permission")
@Api(tags = "系统权限接口")
public class SystemPermissionController extends BaseController {

    @Autowired
    private SystemPermissionService systemPermissionService;

    /**
     * @author Caesar Liu
     * @date 2021/03/27 22:36
     */
    @RequiresPermissions("system:permission:create")
    @PostMapping("/create")
    @ApiOperation("新建")
    public ApiResponse create(@RequestBody SystemPermission systemPermission) {
        systemPermission.setCreateUser(this.getLoginUser().getId());
        return ApiResponse.success(systemPermissionService.create(systemPermission));
    }

    /**
     * @author Caesar Liu
     * @date 2021/03/27 22:36
     */
    @RequiresPermissions("system:permission:delete")
    @GetMapping("/delete/{id}")
    @ApiOperation("根据ID删除")
    public ApiResponse deleteById(@PathVariable Integer id) {
        systemPermissionService.deleteById(id);
        return ApiResponse.success(null);
    }

    /**
     * @author Caesar Liu
     * @date 2021/03/28 09:30
     */
    @RequiresPermissions("system:permission:delete")
    @GetMapping("/delete/batch")
    @ApiOperation("批量删除")
    public ApiResponse deleteById(@RequestParam String ids) {
        String [] idArray = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String id : idArray) {
            idList.add(Integer.valueOf(id));
        }
        systemPermissionService.deleteByIdInBatch(idList);
        return ApiResponse.success(null);
    }

    /**
     * @author Caesar Liu
     * @date 2021/03/27 22:36
     */
    @RequiresPermissions("system:permission:update")
    @PostMapping("/updateById")
    @ApiOperation("根据ID修改")
    public ApiResponse updateById(@RequestBody SystemPermission systemPermission) {
        systemPermission.setUpdateUser(this.getLoginUser().getId());
        systemPermissionService.updateById(systemPermission);
        return ApiResponse.success(null);
    }

    /**
     * @author Caesar Liu
     * @date 2021/03/27 22:36
     */
    @RequiresPermissions("system:permission:query")
    @PostMapping("/page")
    @ApiOperation("分页查询")
    public ApiResponse findPage (@RequestBody PageWrap<SystemPermission> pageWrap) {
        return ApiResponse.success(systemPermissionService.findPage(pageWrap));
    }
}