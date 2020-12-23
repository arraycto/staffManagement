package com.information.staff.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfoVo {
    private List<String> roles;//角色
    private String introduction;//介绍
    private String avatar; //头像
    private String name;//用户名
}
