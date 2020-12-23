package com.information.staff.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.information.staff.entity.*;
import com.information.staff.entity.vo.ClerkQueryVo;
import com.information.staff.service.*;
import com.information.staff.utils.JwtUtils;
import com.information.staff.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 职员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-14
 */
@RestController
@RequestMapping("/staff/clerk")
@CrossOrigin
public class ClerkController {
    @Autowired
    private ClerkService clerkService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DepartmentService departmentService;
    //1.分页查询
    @PostMapping("listPage/{current}/{limit}")
    public R getClerkListPage(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) ClerkQueryVo clerkQueryVo) {
        Page<Clerk> clerkPage = new Page<>(current,limit);
        clerkQueryVo.setIsDeleted(0);
        IPage<Clerk> clerkIPage = clerkService.pageListQuery(clerkPage,clerkQueryVo);
        //获取总记录数
        long total = clerkIPage.getTotal();
        //每页类别集合
        List<Clerk> clerkList = clerkIPage.getRecords();
        return R.ok().data("clerkList",clerkList).data("total",total);
    }

    //3.查询所有
    @GetMapping("list")
    public R getClerkList() {
        List<Clerk> clerkList = clerkService.list(null);
        return R.ok().data("clerkList",clerkList);
    }

    //2.根据id查询
    @GetMapping("{id}")
    public R getClerkById(@PathVariable String id){
        Clerk clerk = clerkService.getById(id);
        return R.ok().data("clerk",clerk);
    }

    //4.根据id删除
    @DeleteMapping("{id}")
    public R deleteClerkById(@PathVariable String id){
        boolean removeById = clerkService.deleteClerkById(id);
        if (removeById){
            return R.ok();
        }
        return R.error().message("删除失败");
    }

    //5.修改
    @PutMapping
    public R updateClerk(@RequestBody Clerk clerk){
        boolean updateById = clerkService.updateClerk(clerk);
        String userId = clerk.getUserId();
        User user = new User();
        user.setId(userId);
        user.setUsername(clerk.getPhoneNumber());
        userService.updateById(user);
        if (updateById){
            return R.ok().message("修改成功");
        }
        return R.error().message("修改失败");
    }
    //6.增加
    @PostMapping
    public R addClerk(@RequestBody Clerk clerk){
        String phoneNumber = clerk.getPhoneNumber();
        QueryWrapper<Clerk> clerkQueryWrapper = new QueryWrapper<>();
        clerkQueryWrapper.eq("phone_number",phoneNumber);
        int count = clerkService.count(clerkQueryWrapper);
        if (count >= 1 ){
            R.error().message("增加失败,请检查手机号是否重复!");
        }
        boolean save = clerkService.addClerk(clerk);

        if (save){
            return R.ok().message("增加成功");
        }
        return R.error().message("增加失败,请检查手机号是否重复!");
    }
    //根据部门查询
    @GetMapping("listClerkByDepartmentId/{id}")
    public R listClerkByDepartmentId(@PathVariable String id){
        QueryWrapper<Clerk> clerkQueryWrapper = new QueryWrapper<>();
        clerkQueryWrapper.eq("department_id",id);
        List<Clerk> clerkList = clerkService.list(clerkQueryWrapper);
        return R.ok().data("clerkList",clerkList);
    }
    //用户获取个人信息
    @PostMapping("getClerkAndUserRole")
    public R getClerkAndUserRole(@RequestHeader("X-Token") String token) {
        String id = JwtUtils.getMemberIdByToken(token);
        //获取账户信息
        User user = userService.getById(id);
        //获取权限信息
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("userid",id);
        List<Role> roleList = roleService.list(roleQueryWrapper);
        //获取个人信息
        QueryWrapper<Clerk> clerkQueryWrapper = new QueryWrapper<>();
        clerkQueryWrapper.eq("user_id",id);
        Clerk clerk = clerkService.getOne(clerkQueryWrapper);
        Department department = departmentService.getById(clerk.getDepartmentId());
        clerk.setDepartmentId(department.getName());
        return R.ok().data("user",user).data("roleList",roleList).data("clerk",clerk);
    }
    //根据部门编号查询员工
    @GetMapping("getClerkByDepartmentId/{departmentId}")
    public R getClerkByDepartmentId(@PathVariable String departmentId){
        QueryWrapper<Clerk> clerkQueryWrapper = new QueryWrapper<>();
        clerkQueryWrapper.eq("department_id",departmentId);
        List<Clerk> clerkList = clerkService.list(clerkQueryWrapper);
        return R.ok().data("clerkList",clerkList);
    }
    //查询离职员工
    //1.分页查询
    @PostMapping("listPageLiZhi/{current}/{limit}")
    public R getClerkListPageLiZhi(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) ClerkQueryVo clerkQueryVo) {
        Page<Clerk> clerkPage = new Page<>(current,limit);
        clerkQueryVo.setIsDeleted(1);
        IPage<Clerk> clerkIPage = clerkService.pageListQuery(clerkPage,clerkQueryVo);
        //获取总记录数
        long total = clerkIPage.getTotal();
        //每页类别集合
        List<Clerk> clerkList = clerkIPage.getRecords();
        return R.ok().data("clerkList",clerkList).data("total",total);
    }
}

