package com.eva.api.system;

import com.eva.api.BaseController;
import com.eva.biz.system.SystemPositionBiz;
import com.eva.core.model.ApiResponse;
import com.eva.dao.system.model.SystemPosition;
import com.eva.dao.system.vo.SystemPositionListVO;
import com.eva.service.system.SystemPositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 岗位接口
 * @author Eva
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
     * @author Eva
     * @date 2021/05/16 17:03
     */
    @ApiOperation("新建")
    @PostMapping("/create")
    @RequiresPermissions("system:position:create")
    public ApiResponse create(@RequestBody SystemPosition systemPosition) {
        return ApiResponse.success(systemPositionBiz.create(systemPosition));
    }

    /**
     * @author Eva
     * @date 2021/05/16 17:03
     */
    @ApiOperation("根据ID删除")
    @GetMapping("/delete/{id}")
    @RequiresPermissions("system:position:delete")
    public ApiResponse deleteById(@PathVariable Integer id) {
        systemPositionService.deleteById(id);
        return ApiResponse.success(null);
    }

    /**
     * @author Eva
     * @date 2021/03/28 09:30
     */
    @ApiOperation("批量删除")
    @GetMapping("/delete/batch")
    @RequiresPermissions("system:position:delete")
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
     * @author Eva
     * @date 2021/05/16 17:03
     */
    @ApiOperation("根据ID修改")
    @PostMapping("/updateById")
    @RequiresPermissions("system:position:update")
    public ApiResponse updateById(@RequestBody SystemPosition systemPosition) {
        systemPositionBiz.updateById(systemPosition);
        return ApiResponse.success(null);
    }

    /**
     * @author Eva
     * @date 2021/05/16 17:03
     */
    @ApiOperation("查询岗位列表")
    @PostMapping("/list")
    @RequiresPermissions("system:position:query")
    public ApiResponse<List<SystemPositionListVO>> findList () {
        return ApiResponse.success(systemPositionBiz.findList());
    }

    /**
     * @author Eva
     * @date 2021/05/16 17:03
     */
    @ApiOperation("根据ID查询")
    @GetMapping("/{id}")
    @RequiresPermissions("system:position:query")
    public ApiResponse<SystemPosition> finById(@PathVariable Integer id) {
        return ApiResponse.success(systemPositionService.findById(id));
    }
}