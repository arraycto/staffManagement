package com.information.staff.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.information.staff.entity.Clerk;
import com.information.staff.entity.Department;
import com.information.staff.entity.Role;
import com.information.staff.entity.User;
import com.information.staff.service.ClerkService;
import com.information.staff.service.DepartmentService;
import com.information.staff.service.RoleService;
import com.information.staff.service.UserService;
import com.information.staff.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-06
 */
@RestController
@RequestMapping("/staff/role")
@CrossOrigin
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClerkService clerkService;
    @Autowired
    private DepartmentService departmentService;
    //1.根据用户id获取权限
    @GetMapping("getRoleByClerkId/{id}")
    public R getRoleByClerkId(@PathVariable String id){
        //查询用户信息
        Clerk clerk = clerkService.getById(id);
        //获取账户id
        String userId = clerk.getUserId();
        //查询权限
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("userid",userId);
        List<Role> roleList = roleService.list(roleQueryWrapper);
        if (roleList.size()==2){
            return R.ok().data("role",true);
        }
        return R.ok().data("role",false);
    }
    //修改用户权限
    @PutMapping("uodateRoleByClerkId/{clerkId}/{managementInfo}")
    public R uodateRoleByClerkId(@PathVariable String clerkId,
                                 @PathVariable String managementInfo){
        //查询用户信息
        Clerk clerk = clerkService.getById(clerkId);
        //获取账户id
        String userId = clerk.getUserId();
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("userid",userId);
        List<Role> list = roleService.list(roleQueryWrapper);
        System.out.println(list.size());
        if (list.size() == 1 && managementInfo.equals("是")) {
            Role role = new Role();
            role.setName("人事经理");
            role.setUserid(userId);
            roleService.save(role);
            System.out.println(1);
        }
        if (list.size() == 2 && managementInfo.equals("否")) {
            roleQueryWrapper.eq("name","人事经理");
            roleService.remove(roleQueryWrapper);
            System.out.println(2);

        }
        return R.ok();
    }
    //查询所有具有管理员权限的用户
    @GetMapping("getAdminList")
    public R getAdminList(){
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("name","人事经理");
        List<Role> roleList = roleService.list(roleQueryWrapper);
        List<Clerk> clerkList = new ArrayList<>();
        for (Role role:roleList){
            String userid = role.getUserid();
            QueryWrapper<Clerk> clerkQueryWrapper = new QueryWrapper<>();
            clerkQueryWrapper.eq("user_id",userid);
            Clerk clerk = clerkService.getOne(clerkQueryWrapper);
            String departmentId = clerk.getDepartmentId();
            Department department = departmentService.getById(departmentId);
            clerk.setDepartmentId(department.getName());
            clerkList.add(clerk);
        }
        return R.ok().data("clerkList",clerkList);
    }
}

