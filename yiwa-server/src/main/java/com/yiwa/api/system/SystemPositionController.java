package com.yiwa.api.system;

import com.yiwa.api.BaseController;
import com.yiwa.core.model.ApiResponse;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.dto.QuerySystemPositionDTO;
import com.yiwa.dao.system.model.SystemPosition;
import com.yiwa.service.system.SystemPositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 岗位接口
 * @author Yiwa
 * @date 2021/05/16 17:03
 */
@RestController
@RequestMapping("/system/position")
@Api(tags = "岗位接口")
public class SystemPositionController extends BaseController {

    @Autowired
    private SystemPositionService systemPositionService;

    /**
     * 创建
     * @author Yiwa
     * @date 2021/05/16 17:03
     */
    @RequiresPermissions("system:position:create")
    @PostMapping("/create")
    @ApiOperation("新建")
    public ApiResponse create(@RequestBody SystemPosition systemPosition) {
        return ApiResponse.success(systemPositionService.create(systemPosition));
    }

    /**
     * 删除
     * @author Yiwa
     * @date 2021/05/16 17:03
     */
    @RequiresPermissions("system:position:delete")
    @GetMapping("/delete/{id}")
    @ApiOperation("根据ID删除")
    public ApiResponse deleteById(@PathVariable Integer id) {
        systemPositionService.deleteById(id);
        return ApiResponse.success(null);
    }

    /**
     * @author Yiwa
     * @date 2021/03/28 09:30
     */
    @RequiresPermissions("system:position:delete")
    @GetMapping("/delete/batch")
    @ApiOperation("批量删除")
    public ApiResponse deleteById(@RequestParam String ids) {
        String [] idArray = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String id : idArray) {
            idList.add(Integer.valueOf(id));
        }
        systemPositionService.deleteByIdInBatch(idList);
        return ApiResponse.success(null);
    }

    /**
     * 根据ID修改
     * @author Yiwa
     * @date 2021/05/16 17:03
     */
    @RequiresPermissions("system:position:update")
    @PostMapping("/updateById")
    @ApiOperation("根据ID修改")
    public ApiResponse updateById(@RequestBody SystemPosition systemPosition) {
        systemPositionService.updateById(systemPosition);
        return ApiResponse.success(null);
    }

    /**
     * 分页查询
     * @author Yiwa
     * @date 2021/05/16 17:03
     */
    @RequiresPermissions("system:position:query")
    @PostMapping("/page")
    @ApiOperation("分页查询")
    public ApiResponse findPage (@RequestBody PageWrap<QuerySystemPositionDTO> pageWrap) {
        return ApiResponse.success(systemPositionService.findPage(pageWrap));
    }

    /**
     * 通过ID查询
     * @author Yiwa
     * @date 2021/05/16 17:03
     */
    @RequiresPermissions("system:position:query")
    @GetMapping("/{id}")
    @ApiOperation("根据ID查询")
    public ApiResponse finById(@PathVariable Integer id) {
        return ApiResponse.success(systemPositionService.findById(id));
    }
}