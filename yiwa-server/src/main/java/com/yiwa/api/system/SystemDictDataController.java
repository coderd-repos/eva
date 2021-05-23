package com.yiwa.api.system;

import com.yiwa.api.BaseController;
import com.yiwa.biz.system.SystemDictDataBiz;
import com.yiwa.core.model.ApiResponse;
import com.yiwa.core.model.PageData;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.dto.QuerySystemDictDataDTO;
import com.yiwa.dao.system.model.SystemDictData;
import com.yiwa.dao.system.vo.SystemDictDataListVO;
import com.yiwa.service.system.SystemDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yiwa
 * @date 2021/05/16 20:18
 */
@Api(tags = "字典数据接口")
@RestController
@RequestMapping("/system/dictData")
public class SystemDictDataController extends BaseController {

    @Autowired
    private SystemDictDataService systemDictDataService;

    @Autowired
    private SystemDictDataBiz systemDictDataBiz;

    /**
     * @author Yiwa
     * @date 2021/05/16 20:18
     */
    @ApiOperation("新建")
    @PostMapping("/create")
    @RequiresPermissions("system:dict:update")
    public ApiResponse create(@RequestBody SystemDictData systemDictData) {
        return ApiResponse.success(systemDictDataBiz.create(systemDictData));
    }

    /**
     * @author Yiwa
     * @date 2021/05/16 20:18
     */
    @ApiOperation("根据ID删除")
    @GetMapping("/delete/{id}")
    @RequiresPermissions("system:dict:update")
    public ApiResponse deleteById(@PathVariable Integer id) {
        systemDictDataService.deleteById(id);
        return ApiResponse.success(null);
    }

    /**
     * @author Yiwa
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
        systemDictDataService.deleteByIdInBatch(idList);
        return ApiResponse.success(null);
    }

    /**
     * @author Yiwa
     * @date 2021/05/16 20:18
     */
    @ApiOperation("根据ID修改")
    @PostMapping("/updateById")
    @RequiresPermissions("system:dict:update")
    public ApiResponse updateById(@RequestBody SystemDictData systemDictData) {
        systemDictDataBiz.updateById(systemDictData);
        return ApiResponse.success(null);
    }

    /**
     * @author Yiwa
     * @date 2021/05/16 20:18
     */
    @ApiOperation("分页查询")
    @PostMapping("/page")
    @RequiresPermissions("system:dict:update")
    public ApiResponse<PageData<SystemDictDataListVO>> findPage (@RequestBody PageWrap<QuerySystemDictDataDTO> pageWrap) {
        return ApiResponse.success(systemDictDataService.findPage(pageWrap));
    }

    /**
     * @author Yiwa
     * @date 2021/05/16 20:18
     */
    @ApiOperation("根据ID查询")
    @GetMapping("/{id}")
    @RequiresPermissions("system:dict:update")
    public ApiResponse<SystemDictData> finById(@PathVariable Integer id) {
        return ApiResponse.success(systemDictDataService.findById(id));
    }
}