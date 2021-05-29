package com.eva.dao.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 跟踪日志
 * @author Eva
 * @date 2021/05/29 13:53
 */
@Data
@ApiModel("跟踪日志")
public class SystemTraceLog implements Serializable {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键", example = "1")
    private Integer id;

    @ApiModelProperty(value = "用户", example = "1")
    private Integer userId;

    @ApiModelProperty(value = "固化用户名")
    private String username;

    @ApiModelProperty(value = "固化用户姓名")
    private String userRealname;

    @ApiModelProperty(value = "固化用户角色")
    private String userRoles;

    @ApiModelProperty(value = "固化用户权限")
    private String userPermissions;

    @ApiModelProperty(value = "操作模块")
    private String operaModule;

    @ApiModelProperty(value = "操作类型")
    private String operaType;

    @ApiModelProperty(value = "操作备注")
    private String operaRemark;

    @ApiModelProperty(value = "操作开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date operaTime;

    @ApiModelProperty(value = "耗时", example = "1")
    private Integer operaSpendTime;

    @ApiModelProperty(value = "请求方式")
    private String requestMethod;

    @ApiModelProperty(value = "请求地址")
    private String requestUri;

    @ApiModelProperty(value = "请求参数")
    private String requestParams;

    @ApiModelProperty(value = "请求结果")
    private String requestResult;

    @ApiModelProperty(value = "状态（0操作失败，1操作成功，-1未得到处理）", example = "1")
    private Byte status;

    @ApiModelProperty(value = "异常信息")
    private String exceptionStack;

    @ApiModelProperty(value = "IP")
    private String ip;

    @ApiModelProperty(value = "接口版本")
    private String serviceVersion;

    @ApiModelProperty(value = "操作平台")
    private String platform;

    @ApiModelProperty(value = "客户端信息")
    private String clientInfo;

    @ApiModelProperty(value = "系统信息")
    private String systemInfo;

}