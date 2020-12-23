package com.information.staff.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RPQueryVo {

    @ApiModelProperty(value = "奖惩类型:0奖励，1惩罚")
    private String type;

    private String name;

    @ApiModelProperty(value = "奖惩原因")
    private String reason;

    @ApiModelProperty(value = "奖惩金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "员工")
    private String clerkName;

    @ApiModelProperty(value = "部门")
    private String departmentName;

}
