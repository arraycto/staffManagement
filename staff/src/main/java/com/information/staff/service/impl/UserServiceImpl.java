package com.information.staff.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.information.staff.entity.User;
import com.information.staff.entity.vo.UserVo;
import com.information.staff.mapper.UserMapper;
import com.information.staff.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.information.staff.utils.JwtUtils;
import com.information.staff.utils.MD5;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public String login(UserVo userVo) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (userVo.getUsername() != null) {
            userQueryWrapper.eq("username",userVo.getUsername());
            User user = baseMapper.selectOne(userQueryWrapper);
            if (user == null){
                return null;
            } else {
                if (user.getPassword().equals(MD5.encrypt(userVo.getPassword()))) {
                    String token = JwtUtils.getJwtToken(user.getId(), user.getUsername());
                    return token;
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
    }
}
