package com.information.staff.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.information.staff.entity.Clerk;
import com.information.staff.entity.Role;
import com.information.staff.entity.User;
import com.information.staff.entity.vo.UserInfoVo;
import com.information.staff.entity.vo.UserVo;
import com.information.staff.service.RoleService;
import com.information.staff.service.UserService;
import com.information.staff.utils.JwtUtils;
import com.information.staff.utils.MD5;
import com.information.staff.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-06
 */
@RestController
@RequestMapping("/staff/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @PostMapping("/login")
    public R login(@RequestBody UserVo userVo) {
        String token = userService.login(userVo);
        try {
            if (!StringUtils.isEmpty(token)) {
                return R.ok().data("token",token);
            } else {
                return R.error().message("用户名和密码不匹配");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return R.error().message("用户名和密码不匹配");
    }
    
    @GetMapping("/info")
    public R info(@RequestParam("token") String token){
        //验证token是否合法
        String userId = JwtUtils.getMemberIdByToken(token);
        User byId = userService.getById(userId);
        UserInfoVo userInfoVo = new UserInfoVo();
        if (!StringUtils.isEmpty(byId)) {
            QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userid",byId.getId());
            List<Role> list = roleService.list(queryWrapper);

            BeanUtils.copyProperties(byId,userInfoVo);
            List<String> roles = new ArrayList<>();
            for (Role role:list) {

                roles.add(role.getName());
            }
            userInfoVo.setRoles(roles);
            return R.ok().data("roles",roles).data("introduction",userInfoVo.getIntroduction()).data("avatar",userInfoVo.getAvatar()).data("name",userInfoVo.getName());
        } else {
            return R.error().message("请先登录");
        }
    }

    @PostMapping("/logout")
    public R logout(@RequestHeader("X-Token") String token) {
        boolean b = JwtUtils.checkToken(token);
        if (b){
            String id = JwtUtils.getMemberIdByToken(token);
            //移除redis中的标记
            return R.ok();
        }
        return R.error();
    }
    //修改信息
    //5.修改
    @PutMapping
    public R updateClerk(@RequestBody User user){
        boolean updateById = userService.updateById(user);
        if (updateById){
            return R.ok().message("修改成功");
        }
        return R.error().message("修改失败");
    }
    //查询自身密码是否与输入的相同
    @PostMapping("getPassword/{password}")
    public R getPassword(@RequestHeader("X-Token") String token,@PathVariable String password) {
        String id = JwtUtils.getMemberIdByToken(token);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id",id);
        userQueryWrapper.eq("password",MD5.encrypt(password));
        int count = userService.count(userQueryWrapper);
        if (count > 0){
            return R.ok();
        }
        return R.error().message("密码错误,请重新输入!");
    }
    //修改密码
    @PutMapping("putPassword/{newPassword}")
    public R putPassword(@PathVariable String newPassword,@RequestHeader("X-Token") String token) {
        String id = JwtUtils.getMemberIdByToken(token);
        User user = new User();
        user.setPassword(MD5.encrypt(newPassword));
        user.setId(id);
        userService.updateById(user);
        return R.ok();
    }
}

