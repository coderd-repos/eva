package com.eva.api.system;

import com.eva.api.BaseController;
import com.eva.biz.system.SystemUserBiz;
import com.eva.core.model.ApiResponse;
import com.eva.core.model.OperaType;
import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.dto.CreateSystemUserDTO;
import com.eva.dao.system.dto.CreateUserRoleDTO;
import com.eva.dao.system.dto.QuerySystemUserDTO;
import com.eva.dao.system.dto.ResetSystemUserPwdDTO;
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
 * @author Caesar Liu
 * @date 2021/03/28 09:30
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/system/user")
public class SystemUserController extends BaseController {

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private SystemUserBiz systemUserBiz;

    /**
     * @author Caesar Liu
     * @date 2021-03-29 22:36
     */
    @ApiOperation("配置用户角色")
    @PostMapping("/createUserRole")
    @RequiresPermissions("system:user:createUserRole")
    public ApiResponse createUserRole (@Validated @RequestBody CreateUserRoleDTO dto) {
        systemUserBiz.createUserRole(dto);
        return ApiResponse.success(null);
    }

    /**
     * @author Caesar Liu
     * @date 2021-03-31 20:25
     */
    @ApiOperation("重置密码")
    @PostMapping("/resetPwd")
    @RequiresPermissions("system:user:resetPwd")
    public ApiResponse resetPwd (@Validated @RequestBody ResetSystemUserPwdDTO dto) {
        dto.setOperaUserId(this.getLoginUser().getId());
        systemUserBiz.resetPwd(dto);
        return ApiResponse.success(null);
    }

    /**
     * @author Caesar Liu
     * @date 2021/03/28 09:30
     */
    @ApiOperation("新建")
    @PostMapping("/create")
    @RequiresPermissions("system:user:create")
    public ApiResponse create(@Validated(OperaType.Create.class) @RequestBody CreateSystemUserDTO systemUser) {
        systemUser.setCreateUser(this.getLoginUser().getId());
        systemUserBiz.create(systemUser);
        return ApiResponse.success(null);
    }

    /**
     * @author Caesar Liu
     * @date 2021/03/28 09:30
     */
    @ApiOperation("根据ID删除")
    @GetMapping("/delete/{id}")
    @RequiresPermissions("system:user:delete")
    public ApiResponse deleteById(@PathVariable Integer id) {
        systemUserService.deleteById(id);
        return ApiResponse.success(null);
    }

    /**
     * @author Caesar Liu
     * @date 2021/03/28 09:30
     */
    @ApiOperation("批量删除")
    @GetMapping("/delete/batch")
    @RequiresPermissions("system:user:delete")
    public ApiResponse deleteById(@RequestParam String ids) {
        String [] idArray = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String id : idArray) {
            idList.add(Integer.valueOf(id));
        }
        systemUserService.deleteByIdInBatch(idList);
        return ApiResponse.success(null);
    }

    /**
     * @author Caesar Liu
     * @date 2021/03/28 09:30
     */
    @ApiOperation("根据ID修改")
    @PostMapping("/updateById")
    @RequiresPermissions("system:user:update")
    public ApiResponse updateById(@Validated(OperaType.Update.class) @RequestBody CreateSystemUserDTO systemUser) {
        systemUser.setUpdateUser(this.getLoginUser().getId());
        systemUserBiz.updateById(systemUser);
        return ApiResponse.success(null);
    }

    /**
     * @author Caesar Liu
     * @date 2021/03/28 09:30
     */
    @ApiOperation("分页查询")
    @PostMapping("/page")
    @RequiresPermissions("system:user:query")
    public ApiResponse<PageData<SystemUserListVO>> findPage (@RequestBody PageWrap<QuerySystemUserDTO> pageWrap) {
        return ApiResponse.success(systemUserService.findPage(pageWrap));
    }
}