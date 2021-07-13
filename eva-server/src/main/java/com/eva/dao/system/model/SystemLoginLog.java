package com.eva.dao.system.model;

import com.eva.core.annotation.excel.ExcelColumn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.util.Date;

/**
 * 登录日志
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
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
    @ExcelColumn(name="登录用户名")
    private String loginUsername;

    @ApiModelProperty(value = "登录IP")
    @ExcelColumn(name="登录IP", color = IndexedColors.RED, width = 8)
    private String ip;

    @ApiModelProperty(value = "登录地址")
    @ExcelColumn(name="登录地址", width = 10)
    private String location;

    @ApiModelProperty(value = "客户端")
    @ExcelColumn(name="客户端", width = 10)
    private String clientInfo;

    @ApiModelProperty(value = "操作系统")
    @ExcelColumn(name="操作系统")
    private String osInfo;

    @ApiModelProperty(value = "登录平台")
    @ExcelColumn(name="登录平台")
    private String platform;

    @ApiModelProperty(value = "系统版本")
    @ExcelColumn(name="系统版本")
    private String systemVersion;

    @ApiModelProperty(value = "服务器IP")
    @ExcelColumn(name="服务器IP", width = 8)
    private String serverIp;

    @ApiModelProperty(value = "是否登录成功")
    @ExcelColumn(name="是否登录成功", valueMapping = "true=是;false=否", align = HorizontalAlignment.CENTER)
    private Boolean success;

    @ApiModelProperty(value = "失败原因")
    @ExcelColumn(name="失败原因", color = IndexedColors.RED, width = 16)
    private String reason;

    @ApiModelProperty(value = "登录时间")
    @ExcelColumn(name="登录时间", dateFormat = "yyyy-MM-dd HH:mm:ss", width = 10)
    private Date loginTime;

}
