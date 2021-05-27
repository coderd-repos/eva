package com.eva.api;

import com.eva.core.model.ApiResponse;
import com.eva.core.exception.BusinessException;
import com.eva.core.model.LoginUserInfo;
import com.alibaba.fastjson.JSON;
import com.eva.core.model.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller基类
 * @author Eva
 * @date 2021/03/26 19:48
 */
@Slf4j
public class BaseController {

    /**
     * 获取当前登录用户
     * @author Eva
     * @date 2021-03-28 15:35
     */
    protected LoginUserInfo getLoginUser () {
        return (LoginUserInfo)SecurityUtils.getSubject().getPrincipal();
    }
  
    /**
     * 全局异常捕获
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandle(HttpServletResponse response, Exception ex){
        try {
            log.error(ex.getMessage(), ex);
            response.setHeader("content-type", "application/json;charset=UTF-8");
            // 业务异常
            if (ex instanceof BusinessException) {
                BusinessException be = (BusinessException) ex;
                response.getWriter().write(JSON.toJSONString(ApiResponse.failed(be.getCode(), be.getMessage())));
                return null;
            }
            // 无权限异常
            if (ex instanceof UnauthorizedException) {
                response.getWriter().write(JSON.toJSONString(ApiResponse.failed("没有操作权限")));
                return null;
            }
            // 验证异常
            if (ex instanceof MethodArgumentNotValidException) {
                MethodArgumentNotValidException exs = (MethodArgumentNotValidException)ex;
                BindingResult bindingResult = exs.getBindingResult();
                List<String> errors = new ArrayList<>();
                for(FieldError fieldError : bindingResult.getFieldErrors()){
                    errors.add(fieldError.getDefaultMessage());
                }
                response.getWriter().write(JSON.toJSONString(ApiResponse.failed(ResponseStatus.BAD_REQUEST.getCode(), StringUtils.join(errors))));
                return null;
            }
            response.getWriter().write(JSON.toJSONString(ApiResponse.failed(ex.getMessage() == null ? "系统繁忙" : ex.getMessage())));
            return null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ModelMap model = new ModelMap();
            model.addAttribute("error", "未知异常");
            return new ModelAndView("error/default", model);
        }
    }
}