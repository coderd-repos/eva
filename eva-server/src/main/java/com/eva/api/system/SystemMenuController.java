package com.eva.api.system;

import com.eva.api.BaseController;
import com.eva.biz.system.SystemMenuBiz;
import com.eva.core.annotation.duplicate.DuplicateSubmit;
import com.eva.core.model.ApiResponse;
import com.eva.core.model.OperaType;
import com.eva.dao.system.dto.UpdateSystemMenuSortDTO;
import com.eva.dao.system.model.SystemMenu;
import com.eva.dao.system.vo.SystemMenuListVO;
import com.eva.dao.system.vo.SystemMenuNodeVO;
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
@Api(tags = "系统菜单")
@RestController
@RequestMapping("/system/menu")
public class SystemMenuController extends BaseController {

    @Autowired
    private SystemMenuBiz systemMenuBiz;

    /**
     * @author Eva
     * @date 2021-03-30 22:22
     */
    @ApiOperation("菜单排序")
    @PostMapping("/updateSort")
    @RequiresPermissions("system:menu:sort")
    public ApiResponse updateSort (@Validated @RequestBody UpdateSystemMenuSortDTO dto) {
        systemMenuBiz.updateSort(dto);
        return ApiResponse.success(null);
    }

    /**
     * @author Eva
     * @date 2021-03-29 15:31
     */
    @ApiOperation("获取菜单树")
    @GetMapping("/tree")
    public ApiResponse<List<SystemMenuNodeVO>> getTreeMenu () {
        return ApiResponse.success(systemMenuBiz.findTree(this.getLoginUser().getId()));
    }

    /**
     * @author Eva
     * @date 2021/03/27 22:36
     */
    @ApiOperation("查询列表树")
    @PostMapping("/tree")
    @RequiresPermissions("system:menu:query")
    public ApiResponse<List<SystemMenuListVO>> findTree () {
        return ApiResponse.success(systemMenuBiz.findTree());
    }

    /**
     * @author Eva
     * @date 2021/03/27 22:36
     */
    @DuplicateSubmit
    @ApiOperation("新建")
    @PostMapping("/create")
    @RequiresPermissions("system:menu:create")
    public ApiResponse create(@Validated(OperaType.Create.class) @RequestBody SystemMenu systemMenu) {
        return ApiResponse.success(systemMenuBiz.create(systemMenu));
    }

    /**
     * @author Eva
     * @date 2021/03/27 22:36
     */
    @ApiOperation("删除")
    @GetMapping("/delete/{id}")
    @RequiresPermissions("system:menu:delete")
    public ApiResponse deleteById(@PathVariable Integer id) {
        systemMenuBiz.deleteById(id);
        return ApiResponse.success(null);
    }

    /**
     * @author Eva
     * @date 2021/03/28 09:30
     */
    @ApiOperation("批量删除")
    @GetMapping("/delete/batch")
    @RequiresPermissions("system:menu:delete")
    public ApiResponse deleteByIdInBatch(@RequestParam String ids) {
        String [] idArray = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String id : idArray) {
            idList.add(Integer.valueOf(id));
        }
        systemMenuBiz.deleteByIdInBatch(idList);
        return ApiResponse.success(null);
    }

    /**
     * @author Eva
     * @date 2021/03/27 22:36
     */
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @RequiresPermissions("system:menu:update")
    public ApiResponse updateById(@Validated(OperaType.Update.class) @RequestBody SystemMenu systemMenu) {
        systemMenuBiz.updateById(systemMenu);
        return ApiResponse.success(null);
    }

    /**
     * @author Eva
     * @date 2021-05-25 19:43
     */
    @ApiOperation("修改菜单状态")
    @PostMapping("/updateStatus")
    @RequiresPermissions("system:menu:update")
    public ApiResponse updateStatus(@Validated(OperaType.UpdateStatus.class) @RequestBody SystemMenu systemMenu) {
        systemMenuBiz.updateById(systemMenu);
        return ApiResponse.success(null);
    }

}