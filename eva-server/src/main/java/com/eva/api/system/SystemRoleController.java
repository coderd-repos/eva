package com.eva.api.system;

import com.eva.api.BaseController;
import com.eva.biz.system.SystemRoleBiz;
import com.eva.core.annotation.duplicate.DuplicateSubmit;
import com.eva.core.model.ApiResponse;
import com.eva.core.model.OperaType;
import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.dto.CreateRoleMenuDTO;
import com.eva.dao.system.dto.CreateRolePermissionDTO;
import com.eva.dao.system.dto.QuerySystemRoleDTO;
import com.eva.dao.system.model.SystemRole;
import com.eva.dao.system.vo.SystemRoleListVO;
import com.eva.service.system.SystemRoleService;
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
 * @date 2021/03/27 22:36
 */
@Api(tags = "角色接口")
@RestController
@RequestMapping("/system/role")
public class SystemRoleController extends BaseController {

    @Autowired
    private SystemRoleService systemRoleService;

    @Autowired
    private SystemRoleBiz systemRoleBiz;

    /**
     * @author Eva
     * @date 2021-03-30 15:47
     */
    @DuplicateSubmit
    @ApiOperation("配置角色菜单")
    @PostMapping("/createRoleMenu")
    @RequiresPermissions("system:role:createRoleMenu")
    public ApiResponse createRoleMenu (@Validated @RequestBody CreateRoleMenuDTO dto) {
        systemRoleBiz.createRoleMenu(dto);
        return ApiResponse.success(null);
    }

    /**
     * @author Eva
     * @date 2021-03-29 22:36
     */
    @DuplicateSubmit
    @ApiOperation("配置角色权限")
    @PostMapping("/createRolePermission")
    @RequiresPermissions("system:role:createRolePermission")
    public ApiResponse createRolePermission (@Validated @RequestBody CreateRolePermissionDTO dto) {
        systemRoleBiz.createRolePermission(dto);
        return ApiResponse.success(null);
    }

    /**
     * @author Eva
     * @date 2021/03/27 22:36
     */
    @DuplicateSubmit
    @ApiOperation("新建")
    @PostMapping("/create")
    @RequiresPermissions("system:role:create")
    public ApiResponse create(@Validated(OperaType.Create.class) @RequestBody SystemRole systemRole) {
        return ApiResponse.success(systemRoleService.create(systemRole));
    }

    /**
     * @author Eva
     * @date 2021/03/27 22:36
     */
    @ApiOperation("根据ID删除")
    @GetMapping("/delete/{id}")
    @RequiresPermissions("system:role:delete")
    public ApiResponse deleteById(@PathVariable Integer id) {
        systemRoleService.deleteById(id);
        return ApiResponse.success(null);
    }

    /**
     * @author Eva
     * @date 2021/03/28 09:30
     */
    @ApiOperation("批量删除")
    @GetMapping("/delete/batch")
    @RequiresPermissions("system:role:delete")
    public ApiResponse deleteById(@RequestParam String ids) {
        String [] idArray = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String id : idArray) {
            idList.add(Integer.valueOf(id));
        }
        systemRoleService.deleteByIdInBatch(idList);
        return ApiResponse.success(null);
    }

    /**
     * @author Eva
     * @date 2021/03/27 22:36
     */
    @ApiOperation("根据ID修改")
    @PostMapping("/updateById")
    @RequiresPermissions("system:role:update")
    public ApiResponse updateById(@Validated(OperaType.Update.class) @RequestBody SystemRole systemRole) {
        systemRoleService.updateById(systemRole);
        return ApiResponse.success(null);
    }

    /**
     * @author Eva
     * @date 2021/03/27 22:36
     */
    @ApiOperation("分页查询")
    @PostMapping("/page")
    @RequiresPermissions("system:role:query")
    public ApiResponse<PageData<SystemRoleListVO>> findPage (@RequestBody PageWrap<QuerySystemRoleDTO> pageWrap) {
        return ApiResponse.success(systemRoleService.findPage(pageWrap));
    }

    /**
     * @author Eva
     * @date 2021/03/27 22:36
     */
    @ApiOperation("查询所有")
    @GetMapping("/all")
    @RequiresPermissions("system:role:query")
    public ApiResponse<List<SystemRole>> findAll () {
        SystemRole systemRole = new SystemRole();
        systemRole.setDeleted(Boolean.FALSE);
        return ApiResponse.success(systemRoleService.findList(systemRole));
    }
}