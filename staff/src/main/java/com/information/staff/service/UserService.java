package com.information.staff.service;

import com.information.staff.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.information.staff.entity.vo.UserVo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-06
 */
public interface UserService extends IService<User> {
    //如果验证成功 返回tokrn 否则返回null或者异常
    public String login(UserVo userVo);
}
