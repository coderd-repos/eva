package com.yiwa.api.system;

import com.yiwa.api.BaseController;
import com.yiwa.biz.system.SystemPositionBiz;
import com.yiwa.core.model.ApiResponse;
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
@Api(tags = "岗位接口")
@RestController
@RequestMapping("/system/position")
public class SystemPositionController extends BaseController {

    @Autowired
    private SystemPositionService systemPositionService;

    @Autowired
    private SystemPositionBiz systemPositionBiz;

    /**
     * @author Yiwa
     * @date 2021/05/16 17:03
     */
    @RequiresPermissions("system:position:create")
    @PostMapping("/create")
    @ApiOperation("新建")
    public ApiResponse create(@RequestBody SystemPosition systemPosition) {
        return ApiResponse.success(systemPositionBiz.create(systemPosition));
    }

    /**
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
     * @author Yiwa
     * @date 2021/05/16 17:03
     */
    @RequiresPermissions("system:position:update")
    @PostMapping("/updateById")
    @ApiOperation("根据ID修改")
    public ApiResponse updateById(@RequestBody SystemPosition systemPosition) {
        systemPositionBiz.updateById(systemPosition);
        return ApiResponse.success(null);
    }

    /**
     * @author Yiwa
     * @date 2021/05/16 17:03
     */
    @RequiresPermissions("system:position:query")
    @PostMapping("/list")
    @ApiOperation("查询岗位列表")
    public ApiResponse findList () {
        return ApiResponse.success(systemPositionBiz.findList());
    }

    /**
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