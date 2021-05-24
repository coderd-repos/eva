package com.yiwa.api.system;

import com.yiwa.api.BaseController;
import com.yiwa.biz.system.SystemDepartmentBiz;
import com.yiwa.core.model.ApiResponse;
import com.yiwa.core.model.PageData;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.dto.QuerySystemUserDTO;
import com.yiwa.dao.system.model.SystemDepartment;
import com.yiwa.dao.system.vo.SystemDepartmentListVO;
import com.yiwa.dao.system.vo.SystemUserListVO;
import com.yiwa.service.system.SystemDepartmentService;
import com.yiwa.service.system.SystemUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yiwa
 * @date 2021/05/16 11:59
 */
@Api(tags = "部门接口")
@RestController
@RequestMapping("/system/department")
public class SystemDepartmentController extends BaseController {

    @Autowired
    private SystemDepartmentService systemDepartmentService;

    @Autowired
    private SystemDepartmentBiz systemDepartmentBiz;

    @Autowired
    private SystemUserService systemUserService;

    /**
     * @author Yiwa
     * @date 2021/05/16 11:59
     */
    @ApiOperation("新建")
    @PostMapping("/create")
    @RequiresPermissions("system:department:create")
    public ApiResponse create(@RequestBody SystemDepartment systemDepartment) {
        return ApiResponse.success(systemDepartmentBiz.create(systemDepartment));
    }

    /**
     * @author Yiwa
     * @date 2021/05/16 11:59
     */
    @ApiOperation("根据ID删除")
    @GetMapping("/delete/{id}")
    @RequiresPermissions("system:department:delete")
    public ApiResponse deleteById(@PathVariable Integer id) {
        systemDepartmentService.deleteById(id);
        return ApiResponse.success(null);
    }

    /**
     * @author Yiwa
     * @date 2021/03/28 09:30
     */
    @ApiOperation("批量删除")
    @GetMapping("/delete/batch")
    @RequiresPermissions("system:department:delete")
    public ApiResponse deleteById(@RequestParam String ids) {
        String [] idArray = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String id : idArray) {
            idList.add(Integer.valueOf(id));
        }
        systemDepartmentService.deleteByIdInBatch(idList);
        return ApiResponse.success(null);
    }

    /**
     * @author Yiwa
     * @date 2021/05/16 11:59
     */
    @ApiOperation("根据ID修改")
    @PostMapping("/updateById")
    @RequiresPermissions("system:department:update")
    public ApiResponse updateById(@RequestBody SystemDepartment systemDepartment) {
        systemDepartmentBiz.updateById(systemDepartment);
        return ApiResponse.success(null);
    }

    /**
     * @author Yiwa
     * @date 2021/05/16 11:59
     */
    @ApiOperation("查询部门列表")
    @PostMapping("/list")
    @RequiresPermissions("system:department:query")
    public ApiResponse<List<SystemDepartmentListVO>> findList () {
        return ApiResponse.success(systemDepartmentBiz.findList());
    }

    /**
     * @author Caesar Liu
     * @date 2021-05-24 11:55
     */
    @ApiOperation("查询部门人员")
    @PostMapping("/users")
    @RequiresPermissions("system:department:queryUsers")
    public ApiResponse<PageData<SystemUserListVO>> findPage (@RequestBody PageWrap<QuerySystemUserDTO> pageWrap) {
        return ApiResponse.success(systemUserService.findPage(pageWrap));
    }

    /**
     * @author Yiwa
     * @date 2021/05/16 11:59
     */
    @ApiOperation("根据ID查询")
    @GetMapping("/{id}")
    @RequiresPermissions("system:department:query")
    public ApiResponse<SystemDepartment> finById(@PathVariable Integer id) {
        return ApiResponse.success(systemDepartmentService.findById(id));
    }
}