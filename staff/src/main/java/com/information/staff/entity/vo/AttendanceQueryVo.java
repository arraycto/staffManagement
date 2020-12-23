package com.information.staff.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AttendanceQueryVo {
    @ApiModelProperty(value = "考勤名称")
    private String name;

    @ApiModelProperty(value = "正常0,事假1,迟到2,早退3,病假4,旷工5,休补6")
    private String status;

    @ApiModelProperty(value = "审核状态:0正在审核，1审核通过，2审核不通过")
    private String audit;

    @ApiModelProperty(value = "员工姓名")
    private String clerkName;

    @ApiModelProperty(value = "部门姓名")
    private String departmentName;

}
