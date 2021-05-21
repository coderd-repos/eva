package com.yiwa.api.system;

import com.yiwa.api.BaseController;
import com.yiwa.core.model.ApiResponse;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.dto.QuerySystemDictDataDTO;
import com.yiwa.dao.system.model.SystemDictData;
import com.yiwa.service.system.SystemDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 字典数据接口
 * @author Yiwa
 * @date 2021/05/16 20:18
 */
@RestController
@RequestMapping("/system/dictData")
@Api(tags = "字典数据接口")
public class SystemDictDataController extends BaseController {

    @Autowired
    private SystemDictDataService systemDictDataService;

    /**
     * 创建
     * @author Yiwa
     * @date 2021/05/16 20:18
     */
    @RequiresPermissions("system:dict:update")
    @PostMapping("/create")
    @ApiOperation("新建")
    public ApiResponse create(@RequestBody SystemDictData systemDictData) {
        return ApiResponse.success(systemDictDataService.create(systemDictData));
    }

    /**
     * 删除
     * @author Yiwa
     * @date 2021/05/16 20:18
     */
    @RequiresPermissions("system:dict:update")
    @GetMapping("/delete/{id}")
    @ApiOperation("根据ID删除")
    public ApiResponse deleteById(@PathVariable Integer id) {
        systemDictDataService.deleteById(id);
        return ApiResponse.success(null);
    }

    /**
     * 根据ID修改
     * @author Yiwa
     * @date 2021/05/16 20:18
     */
    @RequiresPermissions("system:dict:update")
    @PostMapping("/updateById")
    @ApiOperation("根据ID修改")
    public ApiResponse updateById(@RequestBody SystemDictData systemDictData) {
        systemDictDataService.updateById(systemDictData);
        return ApiResponse.success(null);
    }

    /**
     * 分页查询
     * @author Yiwa
     * @date 2021/05/16 20:18
     */
    @RequiresPermissions("system:dict:update")
    @PostMapping("/page")
    @ApiOperation("分页查询")
    public ApiResponse findPage (@RequestBody PageWrap<QuerySystemDictDataDTO> pageWrap) {
        return ApiResponse.success(systemDictDataService.findPage(pageWrap));
    }

    /**
     * 通过ID查询
     * @author Yiwa
     * @date 2021/05/16 20:18
     */
    @RequiresPermissions("system:dict:update")
    @GetMapping("/{id}")
    @ApiOperation("根据ID查询")
    public ApiResponse finById(@PathVariable Integer id) {
        return ApiResponse.success(systemDictDataService.findById(id));
    }
}