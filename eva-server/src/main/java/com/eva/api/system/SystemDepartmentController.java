package com.eva.api.system;

import com.eva.api.BaseController;
import com.eva.biz.system.SystemDepartmentBiz;
import com.eva.core.annotation.duplicate.DuplicateSubmit;
import com.eva.core.annotation.trace.Trace;
import com.eva.core.model.ApiResponse;
import com.eva.core.model.OperaType;
import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.dto.QuerySystemUserDTO;
import com.eva.dao.system.model.SystemDepartment;
import com.eva.dao.system.vo.SystemDepartmentListVO;
import com.eva.dao.system.vo.SystemUserListVO;
import com.eva.service.system.SystemUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eva
 * @date 2021/05/16 11:59
 */
@Api(tags = "部门")
@RestController
@RequestMapping("/system/department")
public class SystemDepartmentController extends BaseController {

    @Autowired
    private SystemDepartmentBiz systemDepartmentBiz;

    @Autowired
    private SystemUserService systemUserService;

    /**
     * @author Eva
     * @date 2021/05/16 11:59
     */
    @DuplicateSubmit
    @ApiOperation("新建")
    @PostMapping("/create")
    @RequiresPermissions("system:department:create")
    public ApiResponse create(@Validated(OperaType.Create.class) @RequestBody SystemDepartment systemDepartment) {
        return ApiResponse.success(systemDepartmentBiz.create(systemDepartment));
    }

    /**
     * @author Eva
     * @date 2021/05/16 11:59
     */
    @ApiOperation("删除")
    @GetMapping("/delete/{id}")
    @RequiresPermissions("system:department:delete")
    public ApiResponse deleteById(@PathVariable Integer id) {
        systemDepartmentBiz.deleteById(id);
        return ApiResponse.success(null);
    }

    /**
     * @author Eva
     * @date 2021/03/28 09:30
     */
    @ApiOperation("批量删除")
    @GetMapping("/delete/batch")
    @DuplicateSubmit
    @RequiresPermissions("system:department:delete")
    public ApiResponse deleteByIdInBatch(@RequestParam String ids) {
        String [] idArray = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String id : idArray) {
            idList.add(Integer.valueOf(id));
        }
        systemDepartmentBiz.deleteByIdInBatch(idList);
        return ApiResponse.success(null);
    }

    /**
     * @author Eva
     * @date 2021/05/16 11:59
     */
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @RequiresPermissions("system:department:update")
    public ApiResponse updateById(@Validated(OperaType.Update.class) @RequestBody SystemDepartment systemDepartment) {
        systemDepartmentBiz.updateById(systemDepartment);
        return ApiResponse.success(null);
    }

    /**
     * @author Eva
     * @date 2021/05/16 11:59
     */
    @ApiOperation("查询部门列表")
    @PostMapping("/tree")
    @RequiresPermissions("system:department:query")
    public ApiResponse<List<SystemDepartmentListVO>> findTree () {
        return ApiResponse.success(systemDepartmentBiz.findTree());
    }

    /**
     * @author Eva
     * @date 2021-05-24 11:55
     */
    @Trace(exclude = true)
    @ApiOperation("查询部门人员")
    @PostMapping("/users")
    @RequiresPermissions("system:department:queryUsers")
    public ApiResponse<PageData<SystemUserListVO>> findPage (@RequestBody PageWrap<QuerySystemUserDTO> pageWrap) {
        return ApiResponse.success(systemUserService.findPage(pageWrap));
    }
}