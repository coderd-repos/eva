package com.eva.api.system;

import com.eva.api.BaseController;
import com.eva.biz.system.SystemDictDataBiz;
import com.eva.core.annotation.pr.PreventRepeat;
import com.eva.core.model.ApiResponse;
import com.eva.core.constants.OperaType;
import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.dto.QuerySystemDictDataDTO;
import com.eva.dao.system.model.SystemDictData;
import com.eva.dao.system.vo.SystemDictDataListVO;
import com.eva.service.system.SystemDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
@Api(tags = "字典数据")
@RestController
@RequestMapping("/system/dictData")
public class SystemDictDataController extends BaseController {

    @Autowired
    private SystemDictDataService systemDictDataService;

    @Autowired
    private SystemDictDataBiz systemDictDataBiz;

    @PreventRepeat
    @ApiOperation("新建")
    @PostMapping("/create")
    @RequiresPermissions("system:dict:update")
    public ApiResponse create(@Validated(OperaType.Create.class) @RequestBody SystemDictData systemDictData) {
        return ApiResponse.success(systemDictDataBiz.create(systemDictData));
    }

    @ApiOperation("删除")
    @GetMapping("/delete/{id}")
    @RequiresPermissions("system:dict:update")
    public ApiResponse deleteById(@PathVariable Integer id) {
        systemDictDataService.deleteById(id);
        return ApiResponse.success(null);
    }

    @ApiOperation("批量删除")
    @GetMapping("/delete/batch")
    @RequiresPermissions("system:dict:delete")
    public ApiResponse deleteByIdInBatch(@RequestParam String ids) {
        String [] idArray = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String id : idArray) {
            idList.add(Integer.valueOf(id));
        }
        systemDictDataService.deleteByIdInBatch(idList);
        return ApiResponse.success(null);
    }

    @ApiOperation("修改")
    @PostMapping("/updateById")
    @RequiresPermissions("system:dict:update")
    public ApiResponse updateById(@Validated(OperaType.Update.class) @RequestBody SystemDictData systemDictData) {
        systemDictDataBiz.updateById(systemDictData);
        return ApiResponse.success(null);
    }

    @ApiOperation("分页查询")
    @PostMapping("/page")
    @RequiresPermissions("system:dict:update")
    public ApiResponse<PageData<SystemDictDataListVO>> findPage (@RequestBody PageWrap<QuerySystemDictDataDTO> pageWrap) {
        return ApiResponse.success(systemDictDataService.findPage(pageWrap));
    }
}
