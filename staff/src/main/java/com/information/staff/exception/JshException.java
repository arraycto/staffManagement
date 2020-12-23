package com.information.staff.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor //生成有参构造
@NoArgsConstructor  //生成无参构造
public class JshException extends RuntimeException{
    private Integer code;//状态码
    private String msg;//异常信息
}
