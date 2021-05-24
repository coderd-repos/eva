package com.eva.api.system;

import com.eva.api.BaseController;
import com.eva.biz.system.SystemDictBiz;
import com.eva.core.model.ApiResponse;
import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.dto.QuerySystemDictDTO;
import com.eva.dao.system.model.SystemDict;
import com.eva.dao.system.vo.SystemDictListVO;
import com.eva.service.system.SystemDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eva
 * @date 2021/05/16 17:40
 */
@Api(tags = "字典接口")
@RestController
@RequestMapping("/system/dict")
public class SystemDictController extends BaseController {

    @Autowired
    private SystemDictService systemDictService;

    @Autowired
    private SystemDictBiz systemDictBiz;

    /**
     * @author Eva
     * @date 2021/05/16 17:40
     */
    @ApiOperation("新建")
    @PostMapping("/create")
    @RequiresPermissions("system:dict:create")
    public ApiResponse create(@RequestBody SystemDict systemDict) {
        return ApiResponse.success(systemDictBiz.create(systemDict));
    }

    /**
     * @author Eva
     * @date 2021/05/16 17:40
     */
    @ApiOperation("根据ID删除")
    @GetMapping("/delete/{id}")
    @RequiresPermissions("system:dict:delete")
    public ApiResponse deleteById(@PathVariable Integer id) {
        systemDictService.deleteById(id);
        return ApiResponse.success(null);
    }

    /**
     * @author Eva
     * @date 2021/03/28 09:30
     */
    @ApiOperation("批量删除")
    @GetMapping("/delete/batch")
    @RequiresPermissions("system:dict:delete")
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
     * @author Eva
     * @date 2021/05/16 17:40
     */
    @ApiOperation("根据ID修改")
    @PostMapping("/updateById")
    @RequiresPermissions("system:dict:update")
    public ApiResponse updateById(@RequestBody SystemDict systemDict) {
        systemDictBiz.updateById(systemDict);
        return ApiResponse.success(null);
    }

    /**
     * @author Eva
     * @date 2021/05/16 17:40
     */
    @ApiOperation("分页查询")
    @PostMapping("/page")
    @RequiresPermissions("system:dict:query")
    public ApiResponse<PageData<SystemDictListVO>> findPage (@RequestBody PageWrap<QuerySystemDictDTO> pageWrap) {
        return ApiResponse.success(systemDictService.findPage(pageWrap));
    }

    /**
     * @author Eva
     * @date 2021/05/16 17:40
     */
    @ApiOperation("根据ID查询")
    @GetMapping("/{id}")
    @RequiresPermissions("system:dict:query")
    public ApiResponse<SystemDict> finById(@PathVariable Integer id) {
        return ApiResponse.success(systemDictService.findById(id));
    }
}