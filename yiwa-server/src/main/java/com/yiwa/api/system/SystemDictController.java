package com.yiwa.api.system;

import com.yiwa.api.BaseController;
import com.yiwa.biz.system.SystemDictBiz;
import com.yiwa.core.model.ApiResponse;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.dto.QuerySystemDictDTO;
import com.yiwa.dao.system.model.SystemDict;
import com.yiwa.service.system.SystemDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典接口
 * @author Yiwa
 * @date 2021/05/16 17:40
 */
@RestController
@RequestMapping("/system/dict")
@Api(tags = "字典接口")
public class SystemDictController extends BaseController {

    @Autowired
    private SystemDictService systemDictService;

    @Autowired
    private SystemDictBiz systemDictBiz;

    /**
     * 创建
     * @author Yiwa
     * @date 2021/05/16 17:40
     */
    @RequiresPermissions("system:dict:create")
    @PostMapping("/create")
    @ApiOperation("新建")
    public ApiResponse create(@RequestBody SystemDict systemDict) {
        return ApiResponse.success(systemDictBiz.create(systemDict));
    }

    /**
     * 删除
     * @author Yiwa
     * @date 2021/05/16 17:40
     */
    @RequiresPermissions("system:dict:delete")
    @GetMapping("/delete/{id}")
    @ApiOperation("根据ID删除")
    public ApiResponse deleteById(@PathVariable Integer id) {
        systemDictService.deleteById(id);
        return ApiResponse.success(null);
    }

    /**
     * @author Yiwa
     * @date 2021/03/28 09:30
     */
    @RequiresPermissions("system:dict:delete")
    @GetMapping("/delete/batch")
    @ApiOperation("批量删除")
    public ApiResponse deleteById(@RequestParam String ids) {
        String [] idArray = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String id : idArray) {
            idList.add(Integer.valueOf(id));
        }
        systemDictService.deleteByIdInBatch(idList);
        return ApiResponse.success(null);
    }

    /**
     * 根据ID修改
     * @author Yiwa
     * @date 2021/05/16 17:40
     */
    @RequiresPermissions("system:dict:update")
    @PostMapping("/updateById")
    @ApiOperation("根据ID修改")
    public ApiResponse updateById(@RequestBody SystemDict systemDict) {
        systemDictBiz.updateById(systemDict);
        return ApiResponse.success(null);
    }

    /**
     * 分页查询
     * @author Yiwa
     * @date 2021/05/16 17:40
     */
    @RequiresPermissions("system:dict:query")
    @PostMapping("/page")
    @ApiOperation("分页查询")
    public ApiResponse findPage (@RequestBody PageWrap<QuerySystemDictDTO> pageWrap) {
        return ApiResponse.success(systemDictService.findPage(pageWrap));
    }

    /**
     * 通过ID查询
     * @author Yiwa
     * @date 2021/05/16 17:40
     */
    @RequiresPermissions("system:dict:query")
    @GetMapping("/{id}")
    @ApiOperation("根据ID查询")
    public ApiResponse finById(@PathVariable Integer id) {
        return ApiResponse.success(systemDictService.findById(id));
    }
}