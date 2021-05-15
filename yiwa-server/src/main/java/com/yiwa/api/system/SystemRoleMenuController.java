package com.yiwa.api.system;

import com.yiwa.api.BaseController;
import com.yiwa.core.model.ApiResponse;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.model.SystemRoleMenu;
import com.yiwa.service.system.SystemRoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 角色菜单关联接口
 * @author Caesar Liu
 * @date 2021/05/15 19:41
 */
@RestController
@RequestMapping("/systemRoleMenu")
@Api(tags = "角色菜单关联接口")
public class SystemRoleMenuController extends BaseController {

    @Autowired
    private SystemRoleMenuService systemRoleMenuService;
}