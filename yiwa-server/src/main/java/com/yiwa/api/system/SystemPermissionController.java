package com.yiwa.api.system;

import com.yiwa.api.BaseController;
import com.yiwa.biz.system.SystemPermissionBiz;
import com.yiwa.core.model.ApiResponse;
import com.yiwa.core.model.PageData;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.dto.QuerySystemPermissionDTO;
import com.yiwa.dao.system.model.SystemPermission;
import com.yiwa.dao.system.vo.SystemPermissionListVO;
import com.yiwa.service.system.SystemPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yiwa
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
     * @author Yiwa
     * @date 2021/03/27 22:36
     */
    @ApiOperation("新建")
    @PostMapping("/create")
    @RequiresPermissions("system:permission:create")
    public ApiResponse create(@RequestBody SystemPermission systemPermission) {
        return ApiResponse.success(systemPermissionBiz.create(systemPermission));
    }

    /**
     * @author Yiwa
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
     * @author Yiwa
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
     * @author Yiwa
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
     * @author Yiwa
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