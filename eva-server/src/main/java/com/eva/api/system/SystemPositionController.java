package com.eva.api.system;

import com.eva.api.BaseController;
import com.eva.biz.system.SystemPositionBiz;
import com.eva.core.annotation.duplicate.DuplicateSubmit;
import com.eva.core.model.ApiResponse;
import com.eva.core.model.OperaType;
import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.dto.QuerySystemUserDTO;
import com.eva.dao.system.model.SystemPosition;
import com.eva.dao.system.vo.SystemPositionListVO;
import com.eva.dao.system.vo.SystemUserListVO;
import com.eva.service.system.SystemPositionService;
import com.eva.service.system.SystemUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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

    @Autowired
    private SystemUserService systemUserService;

    /**
     * @author Eva
     * @date 2021/05/16 17:03
     */
    @DuplicateSubmit
    @ApiOperation("新建")
    @PostMapping("/create")
    @RequiresPermissions("system:position:create")
    public ApiResponse create(@Validated(OperaType.Create.class) @RequestBody SystemPosition systemPosition) {
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
        systemPositionBiz.deleteById(id);
        return ApiResponse.success(null);
    }

    /**
     * @author Eva
     * @date 2021/03/28 09:30
     */
    @ApiOperation("批量删除")
    @GetMapping("/delete/batch")
    @RequiresPermissions("system:position:delete")
    public ApiResponse deleteByIdInBatch(@RequestParam String ids) {
        String [] idArray = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String id : idArray) {
            idList.add(Integer.valueOf(id));
        }
        systemPositionBiz.deleteByIdInBatch(idList);
        return ApiResponse.success(null);
    }

    /**
     * @author Eva
     * @date 2021/05/16 17:03
     */
    @ApiOperation("根据ID修改")
    @PostMapping("/updateById")
    @RequiresPermissions("system:position:update")
    public ApiResponse updateById(@Validated(OperaType.Update.class) @RequestBody SystemPosition systemPosition) {
        systemPositionBiz.updateById(systemPosition);
        return ApiResponse.success(null);
    }

    /**
     * @author Eva
     * @date 2021/05/16 17:03
     */
    @ApiOperation("查询岗位树列表")
    @PostMapping("/tree")
    @RequiresPermissions("system:position:query")
    public ApiResponse<List<SystemPositionListVO>> findTree () {
        return ApiResponse.success(systemPositionBiz.findTree());
    }

    /**
     * @author Eva
     * @date 2021-05-24 22:46
     */
    @ApiOperation("查询岗位列表")
    @PostMapping("/all")
    @RequiresPermissions("system:position:query")
    public ApiResponse<List<SystemPosition>> findList () {
        SystemPosition systemPosition = new SystemPosition();
        systemPosition.setDeleted(Boolean.FALSE);
        return ApiResponse.success(systemPositionService.findList(systemPosition));
    }

    /**
     * @author Eva
     * @date 2021-05-24 11:55
     */
    @ApiOperation("查询岗位人员")
    @PostMapping("/users")
    @RequiresPermissions("system:position:queryUsers")
    public ApiResponse<PageData<SystemUserListVO>> findPage (@RequestBody PageWrap<QuerySystemUserDTO> pageWrap) {
        return ApiResponse.success(systemUserService.findPage(pageWrap));
    }

    /**
     * @author Eva
     * @date 2021/05/16 17:03
     */
    @ApiOperation("根据ID查询")
    @GetMapping("/{id}")
    @RequiresPermissions("system:position:query")
    public ApiResponse<SystemPosition> findById(@PathVariable Integer id) {
        return ApiResponse.success(systemPositionService.findById(id));
    }
}