package com.eva.dao.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.util.Date;

/**
 * 登录日志
 * @author Eva
 * @date 2021/05/30 22:54
 */
@Data
@ApiModel("登录日志")
public class SystemLoginLog {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键", example = "1")
    private Integer id;

    @ApiModelProperty(value = "登录用户ID", example = "1")
    private Integer userId;

    @ApiModelProperty(value = "登录用户名")
    private String loginUsername;

    @ApiModelProperty(value = "登录IP")
    private String ip;

    @ApiModelProperty(value = "登录地址")
    private String location;

    @ApiModelProperty(value = "客户端")
    private String clientInfo;

    @ApiModelProperty(value = "操作系统")
    private String osInfo;

    @ApiModelProperty(value = "登录平台")
    private String platform;

    @ApiModelProperty(value = "平台版本")
    private String platformVersion;

    @ApiModelProperty(value = "服务器IP")
    private String serverIp;

    @ApiModelProperty(value = "是否登录成功")
    private Boolean success;

    @ApiModelProperty(value = "失败原因")
    private String reason;

    @ApiModelProperty(value = "登录时间")
    private Date loginTime;

}