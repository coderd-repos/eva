package com.yiwa.api.system;

import com.yiwa.api.BaseController;
import com.yiwa.biz.SystemMenuBiz;
import com.yiwa.core.model.ApiResponse;
import com.yiwa.dao.system.dto.UpdateSystemMenuSortDTO;
import com.yiwa.dao.system.model.SystemMenu;
import com.yiwa.service.system.SystemMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统菜单接口
 * @author Caesar Liu
 * @date 2021/03/27 22:36
 */
@RestController
@RequestMapping("/system/menu")
@Api(tags = "系统菜单接口")
public class SystemMenuController extends BaseController {

    @Autowired
    private SystemMenuService systemMenuService;

    @Autowired
    private SystemMenuBiz systemMenuBiz;

    /**
     * @author Caesar Liu
     * @date 2021-03-30 22:22
     */
    @RequiresPermissions("system:menu:sort")
    @PostMapping("/sort")
    @ApiOperation("菜单排序")
    public ApiResponse updateSort (@RequestBody UpdateSystemMenuSortDTO dto) {
        systemMenuBiz.updateSort(dto);
        return ApiResponse.success(null);
    }

    /**
     * @author Caesar Liu
     * @date 2021-03-29 15:31
     */
    @GetMapping("/tree")
    @ApiOperation("获取菜单树")
    public ApiResponse getTreeMenu () {
        return ApiResponse.success(systemMenuBiz.findTree(this.getLoginUser().getId()));
    }

    /**
     * @author Caesar Liu
     * @date 2021/03/27 22:36
     */
    @RequiresPermissions("system:menu:query")
    @PostMapping("/list")
    @ApiOperation("查询")
    public ApiResponse findList () {
        return ApiResponse.success(systemMenuBiz.findList());
    }

    /**
     * @author Caesar Liu
     * @date 2021/03/27 22:36
     */
    @RequiresPermissions("system:menu:create")
    @PostMapping("/create")
    @ApiOperation("新建")
    public ApiResponse create(@RequestBody SystemMenu systemMenu) {
        return ApiResponse.success(systemMenuService.create(systemMenu));
    }

    /**
     * @author Caesar Liu
     * @date 2021/03/27 22:36
     */
    @RequiresPermissions("system:menu:delete")
    @GetMapping("/delete/{id}")
    @ApiOperation("根据ID删除")
    public ApiResponse deleteById(@PathVariable Integer id) {
        systemMenuService.deleteById(id);
        return ApiResponse.success(null);
    }

    /**
     * @author Caesar Liu
     * @date 2021/03/28 09:30
     */
    @RequiresPermissions("system:menu:delete")
    @GetMapping("/delete/batch")
    @ApiOperation("批量删除")
    public ApiResponse deleteById(@RequestParam String ids) {
        String [] idArray = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String id : idArray) {
            idList.add(Integer.valueOf(id));
        }
        systemMenuService.deleteByIdInBatch(idList);
        return ApiResponse.success(null);
    }

    /**
     * @author Caesar Liu
     * @date 2021/03/27 22:36
     */
    @RequiresPermissions("system:menu:update")
    @PostMapping("/updateById")
    @ApiOperation("根据ID修改")
    public ApiResponse updateById(@RequestBody SystemMenu systemMenu) {
        systemMenuService.updateById(systemMenu);
        return ApiResponse.success(null);
    }

}