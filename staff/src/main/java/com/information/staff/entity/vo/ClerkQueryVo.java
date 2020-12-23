package com.information.staff.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClerkQueryVo {
    @ApiModelProperty(value = "职员姓名")
    private String name;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    private Integer isDeleted;
}
