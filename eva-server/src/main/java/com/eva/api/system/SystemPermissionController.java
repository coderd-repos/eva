package com.eva.api.system;

import com.eva.api.BaseController;
import com.eva.biz.system.SystemPermissionBiz;
import com.eva.core.model.ApiResponse;
import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.dto.QuerySystemPermissionDTO;
import com.eva.dao.system.model.SystemPermission;
import com.eva.dao.system.vo.SystemPermissionListVO;
import com.eva.service.system.SystemPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eva
 * @date 2021/03/27 22:36
 */
@Api(tags = "权限接口")
@RestController
@RequestMapping("/system/permission")
public class SystemPermissionController extends BaseController {

    @Autowired
    private SystemPermissionService systemPermissionService;

    @Autowired
    private SystemPermissionBiz systemPermissionBiz;

    /**
     * @author Eva
     * @date 2021/03/27 22:36
     */
    @ApiOperation("新建")
    @PostMapping("/create")
    @RequiresPermissions("system:permission:create")
    public ApiResponse create(@RequestBody SystemPermission systemPermission) {
        return ApiResponse.success(systemPermissionBiz.create(systemPermission));
    }

    /**
     * @author Eva
     * @date 2021/03/27 22:36
     */
    @ApiOperation("根据ID删除")
    @GetMapping("/delete/{id}")
    @RequiresPermissions("system:permission:delete")
    public ApiResponse deleteById(@PathVariable Integer id) {
        systemPermissionService.deleteById(id);
        return ApiResponse.success(null);
    }

    /**
     * @author Eva
     * @date 2021/03/28 09:30
     */
    @ApiOperation("批量删除")
    @GetMapping("/delete/batch")
    @RequiresPermissions("system:permission:delete")
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
     * @author Eva
     * @date 2021/03/27 22:36
     */
    @ApiOperation("根据ID修改")
    @PostMapping("/updateById")
    @RequiresPermissions("system:permission:update")
    public ApiResponse updateById(@RequestBody SystemPermission systemPermission) {
        systemPermissionBiz.updateById(systemPermission);
        return ApiResponse.success(null);
    }

    /**
     * @author Eva
     * @date 2021/03/27 22:36
     */
    @ApiOperation("分页查询")
    @PostMapping("/page")
    @RequiresPermissions("system:permission:query")
    public ApiResponse<PageData<SystemPermissionListVO>> findPage (@RequestBody PageWrap<QuerySystemPermissionDTO> pageWrap) {
        return ApiResponse.success(systemPermissionService.findPage(pageWrap));
    }

    @ApiOperation("分页查询")
    @GetMapping("/all")
    @RequiresPermissions("system:permission:query")
    public ApiResponse<List<SystemPermission>> findAll () {
        SystemPermission systemPermission = new SystemPermission();
        systemPermission.setDeleted(Boolean.FALSE);
        return ApiResponse.success(systemPermissionService.findList(systemPermission));
    }
}