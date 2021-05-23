package com.yiwa.api.system;

import com.yiwa.api.BaseController;
import com.yiwa.biz.system.SystemRoleBiz;
import com.yiwa.core.model.ApiResponse;
import com.yiwa.core.model.PageData;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.dto.CreateRoleMenuDTO;
import com.yiwa.dao.system.dto.CreateRolePermissionDTO;
import com.yiwa.dao.system.dto.QuerySystemRoleDTO;
import com.yiwa.dao.system.model.SystemRole;
import com.yiwa.dao.system.vo.SystemRoleListVO;
import com.yiwa.service.system.SystemRoleService;
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
@Api(tags = "角色接口")
@RestController
@RequestMapping("/system/role")
public class SystemRoleController extends BaseController {

    @Autowired
    private SystemRoleService systemRoleService;

    @Autowired
    private SystemRoleBiz systemRoleBiz;

    /**
     * @author Yiwa
     * @date 2021-03-30 15:47
     */
    @ApiOperation("配置角色菜单")
    @PostMapping("/createRoleMenu")
    @RequiresPermissions("system:role:createRoleMenu")
    public ApiResponse createRoleMenu (@RequestBody CreateRoleMenuDTO dto) {
        systemRoleBiz.createRoleMenu(dto);
        return ApiResponse.success(null);
    }

    /**
     * @author Yiwa
     * @date 2021-03-29 22:36
     */
    @ApiOperation("配置角色权限")
    @PostMapping("/createRolePermission")
    @RequiresPermissions("system:role:createRolePermission")
    public ApiResponse createRolePermission (@RequestBody CreateRolePermissionDTO dto) {
        systemRoleBiz.createRolePermission(dto);
        return ApiResponse.success(null);
    }

    /**
     * @author Yiwa
     * @date 2021/03/27 22:36
     */
    @ApiOperation("新建")
    @PostMapping("/create")
    @RequiresPermissions("system:role:create")
    public ApiResponse create(@RequestBody SystemRole systemRole) {
        return ApiResponse.success(systemRoleService.create(systemRole));
    }

    /**
     * @author Yiwa
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
     * @author Yiwa
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
     * @author Yiwa
     * @date 2021/03/27 22:36
     */
    @ApiOperation("根据ID修改")
    @PostMapping("/updateById")
    @RequiresPermissions("system:role:update")
    public ApiResponse updateById(@RequestBody SystemRole systemRole) {
        systemRoleService.updateById(systemRole);
        return ApiResponse.success(null);
    }

    /**
     * @author Yiwa
     * @date 2021/03/27 22:36
     */
    @ApiOperation("分页查询")
    @PostMapping("/page")
    @RequiresPermissions("system:role:query")
    public ApiResponse<PageData<SystemRoleListVO>> findPage (@RequestBody PageWrap<QuerySystemRoleDTO> pageWrap) {
        return ApiResponse.success(systemRoleService.findPage(pageWrap));
    }

    /**
     * @author Yiwa
     * @date 2021/03/27 22:36
     */
    @ApiOperation("查询所有")
    @GetMapping("/all")
    @RequiresPermissions("system:role:query")
    public ApiResponse<List<SystemRole>> findAll () {
        return ApiResponse.success(systemRoleService.findList(new SystemRole()));
    }
}