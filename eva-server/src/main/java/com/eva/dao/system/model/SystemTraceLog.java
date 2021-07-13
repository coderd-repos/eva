package com.eva.dao.system.model;

import com.eva.core.annotation.excel.ExcelColumn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 跟踪日志
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
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
    @ExcelColumn(name="操作人用户名")
    private String username;

    @ApiModelProperty(value = "固化用户姓名")
    @ExcelColumn(name="操作人姓名")
    private String userRealname;

    @ApiModelProperty(value = "固化用户角色")
    private String userRoles;

    @ApiModelProperty(value = "固化用户权限")
    private String userPermissions;

    @ApiModelProperty(value = "操作模块")
    @ExcelColumn(name="操作模块")
    private String operaModule;

    @ApiModelProperty(value = "操作类型")
    @ExcelColumn(name="操作类型")
    private String operaType;

    @ApiModelProperty(value = "操作备注")
    @ExcelColumn(name="备注", width = 5)
    private String operaRemark;

    @ApiModelProperty(value = "操作开始时间")
    @ExcelColumn(name="操作开始时间", dateFormat = "yyyy-MM-dd HH:mm:ss", width = 10)
    private Date operaTime;

    @ApiModelProperty(value = "耗时", example = "1")
    @ExcelColumn(name="耗时（ms）")
    private Integer operaSpendTime;

    @ApiModelProperty(value = "请求方式")
    @ExcelColumn(name="请求方式")
    private String requestMethod;

    @ApiModelProperty(value = "请求地址")
    @ExcelColumn(name="请求地址", width = 14)
    private String requestUri;

    @ApiModelProperty(value = "请求参数")
    @ExcelColumn(name="请求参数", width = 16)
    private String requestParams;

    @ApiModelProperty(value = "请求结果")
    @ExcelColumn(name="请求结果", width = 10)
    private String requestResult;

    @ApiModelProperty(value = "状态（0操作失败，1操作成功，-1未得到处理）", example = "1")
    @ExcelColumn(name="状态", valueMapping = "0=操作失败;1=操作成功;-1=未处理", width = 4)
    private Byte status;

    @ApiModelProperty(value = "异常等级")
    @ExcelColumn(name="异常等级", valueMapping = "0=低;5=中;10=高")
    private Byte exceptionLevel;

    @ApiModelProperty(value = "异常信息")
    @ExcelColumn(name="异常信息", width = 16)
    private String exceptionStack;

    @ApiModelProperty(value = "IP")
    @ExcelColumn(name="IP", width = 8)
    private String ip;

    @ApiModelProperty(value = "服务器IP")
    @ExcelColumn(name="服务器IP", width = 8)
    private String serverIp;

    @ApiModelProperty(value = "接口版本")
    @ExcelColumn(name="接口版本")
    private String systemVersion;

    @ApiModelProperty(value = "操作平台")
    @ExcelColumn(name="操作平台")
    private String platform;

    @ApiModelProperty(value = "客户端信息")
    @ExcelColumn(name="客户端信息", width = 10)
    private String clientInfo;

    @ApiModelProperty(value = "系统信息")
    @ExcelColumn(name="系统信息")
    private String osInfo;

}
