package com.yiwa.api.system;

import com.yiwa.api.BaseController;
import com.yiwa.core.model.ApiResponse;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.model.SystemDepartment;
import com.yiwa.service.system.SystemDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 接口
 * @author Caesar Liu
 * @date 2021/05/16 11:59
 */
@RestController
@RequestMapping("/system/department")
@Api(tags = "接口")
public class SystemDepartmentController extends BaseController {

    @Autowired
    private SystemDepartmentService systemDepartmentService;

    /**
     * 创建
     * @author Caesar Liu
     * @date 2021/05/16 11:59
     */
    @RequiresPermissions("system:department:create")
    @PostMapping("/create")
    @ApiOperation("新建")
    public ApiResponse create(@RequestBody SystemDepartment systemDepartment) {
        return ApiResponse.success(systemDepartmentService.create(systemDepartment));
    }

    /**
     * 用户删除
     * @author Caesar Liu
     * @date 2021/05/16 11:59
     */
    @RequiresPermissions("system:department:delete")
    @GetMapping("/delete/{id}")
    @ApiOperation("根据ID删除")
    public ApiResponse deleteById(@PathVariable Integer id) {
        systemDepartmentService.deleteById(id);
        return ApiResponse.success(null);
    }

    /**
     * 修改部门
     * @author Caesar Liu
     * @date 2021/05/16 11:59
     */
    @RequiresPermissions("system:department:update")
    @PostMapping("/updateById")
    @ApiOperation("根据ID修改")
    public ApiResponse updateById(@RequestBody SystemDepartment systemDepartment) {
        systemDepartmentService.updateById(systemDepartment);
        return ApiResponse.success(null);
    }

    /**
     * 分页查询
     * @author Caesar Liu
     * @date 2021/05/16 11:59
     */
    @RequiresPermissions("system:department:query")
    @PostMapping("/page")
    @ApiOperation("分页查询")
    public ApiResponse findPage (@RequestBody PageWrap<SystemDepartment> pageWrap) {
        return ApiResponse.success(systemDepartmentService.findPage(pageWrap));
    }

    /**
     * 通过ID查询
     * @author Caesar Liu
     * @date 2021/05/16 11:59
     */
    @RequiresPermissions("system:department:query")
    @GetMapping("/{id}")
    @ApiOperation("根据ID查询")
    public ApiResponse finById(@PathVariable Integer id) {
        return ApiResponse.success(systemDepartmentService.findById(id));
    }
}