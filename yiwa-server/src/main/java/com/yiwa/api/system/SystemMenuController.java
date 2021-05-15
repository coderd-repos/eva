package com.yiwa.api.system;

import com.yiwa.api.BaseController;
import com.yiwa.core.model.ApiResponse;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.model.SystemMenu;
import com.yiwa.service.system.SystemMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统菜单接口
 * @author Caesar Liu
 * @date 2021/05/15 19:40
 */
@RestController
@RequestMapping("/systemMenu")
@Api(tags = "系统菜单接口")
public class SystemMenuController extends BaseController {

    @Autowired
    private SystemMenuService systemMenuService;
}