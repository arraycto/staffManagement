package com.information.staff.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.information.staff.entity.Attendance;
import com.information.staff.entity.Clerk;
import com.information.staff.entity.RewardsPunishments;
import com.information.staff.entity.vo.AttendanceQueryVo;
import com.information.staff.entity.vo.RPQueryVo;
import com.information.staff.service.AttendanceService;
import com.information.staff.service.ClerkService;
import com.information.staff.service.RewardsPunishmentsService;
import com.information.staff.utils.JwtUtils;
import com.information.staff.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 考勤表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-14
 */
@RestController
@RequestMapping("/staff/attendance")
@CrossOrigin
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private ClerkService clerkService;
    //1.分页查询所有
    @PostMapping("listPage/{current}/{limit}")
    public R getAttendanceListPage(
            @PathVariable long current,
            @PathVariable long limit,
            @RequestBody(required = false) AttendanceQueryVo attendanceQueryVo) {
        Page<Attendance> attendancePage = new Page<>(current,limit);
        IPage<Attendance> clerkIPage = attendanceService.pageListQuery(attendancePage,attendanceQueryVo);
        //获取总记录数
        long total = clerkIPage.getTotal();
        //每页类别集合
        List<Attendance> attendanceList = clerkIPage.getRecords();

        for (Attendance attendance:attendanceList) {

//            事假1,迟到2,早退3,病假4,旷工5,休补6
            if (attendance.getStatus().equals("1")) {
                attendance.setStatus("事假");
            } else
            if (attendance.getStatus().equals("2")) {
                attendance.setStatus("迟到");
            }else
            if (attendance.getStatus().equals("3")) {
                attendance.setStatus("早退");
            }else
            if (attendance.getStatus().equals("4")) {
                attendance.setStatus("病假");
            }else
            if (attendance.getStatus().equals("5")) {
                attendance.setStatus("旷工");
            }else
            if (attendance.getStatus().equals("6")) {
                attendance.setStatus("休补");
            }else{
                attendance.setStatus("正常");
            }
        }
        return R.ok().data("attendanceList",attendanceList).data("total",total);
    }

    //3.查询所有
    @GetMapping("list")
    public R getAttendanceList() {
        List<Attendance> attendanceList = attendanceService.list(null);
        return R.ok().data("attendanceList",attendanceList);
    }

    //2.根据id查询
    @GetMapping("{id}")
    public R getAttendanceById(@PathVariable String id){
        Attendance attendance = attendanceService.getById(id);
        if (StringUtils.isEmpty(attendance)){
            return R.error();
        }
        return R.ok().data("attendance",attendance);
    }

    //4.根据id删除
    @DeleteMapping("{id}")
    public R deleteAttendanceById(@PathVariable String id){
        boolean removeById = attendanceService.removeById(id);
        if (removeById){
            return R.ok();
        }
        return R.error().message("删除失败");
    }

    //5.修改
    @PutMapping
    public R updateAttendance(@RequestBody Attendance attendance){
        attendance.setAudit("0");
        boolean updateById = attendanceService.updateById(attendance);
        if (updateById){
            return R.ok().message("修改成功");
        }
        return R.error().message("修改失败");
    }
    //6.增加
    @PostMapping
    public R addAttendance(@RequestBody Attendance attendance){
        boolean save = attendanceService.save(attendance);
        if (save){
            return R.ok().message("增加成功");
        }
        return R.error().message("增加失败");
    }
    //根据token获取他的奖惩信息
    @PostMapping("getAttendance/{current}/{limit}")
    public R getAttendance(@PathVariable long current,
                                   @PathVariable long limit,
                                   @RequestBody(required = false) AttendanceQueryVo attendanceQueryVo,
                                   @RequestHeader("X-Token") String token) {
        String id = JwtUtils.getMemberIdByToken(token);
        QueryWrapper<Clerk> clerkQueryWrapper = new QueryWrapper<>();
        clerkQueryWrapper.eq("user_id",id);
        Clerk clerk = clerkService.getOne(clerkQueryWrapper);
        String clerkId = clerk.getId();

        QueryWrapper<Attendance> attendanceQueryWrapper = new QueryWrapper<>();
        attendanceQueryWrapper.eq("clerk_id",clerkId);
        Page<Attendance> attendancePage = new Page<>(current,limit);
        if (!StringUtils.isEmpty(attendanceQueryVo.getName())) {
            attendanceQueryWrapper.like("name",attendanceQueryVo.getName());
        }
        if (!StringUtils.isEmpty(attendanceQueryVo.getStatus())) {
            attendanceQueryWrapper.like("status",attendanceQueryVo.getStatus());
        }

        attendanceService.page(attendancePage,attendanceQueryWrapper);
        List<Attendance> attendanceList = attendancePage.getRecords();
        for (Attendance attendance:attendanceList) {
            if (attendance.getAudit().equals("0")) {

                attendance.setAudit("正在审核");
            } else if (attendance.getAudit().equals("1")) {
                attendance.setAudit("已通过");
            } else {
                attendance.setAudit("未通过");
            }
            if (attendance.getStatus().equals("1")) {
                attendance.setStatus("事假");
            } else
            if (attendance.getStatus().equals("2")) {
                attendance.setStatus("迟到");
            }else
            if (attendance.getStatus().equals("3")) {
                attendance.setStatus("早退");
            }else
            if (attendance.getStatus().equals("4")) {
                attendance.setStatus("病假");
            }else
            if (attendance.getStatus().equals("5")) {
                attendance.setStatus("旷工");
            }else
            if (attendance.getStatus().equals("6")) {
                attendance.setStatus("休补");
            }else{
                attendance.setStatus("正常");
            }
        }
        long total = attendancePage.getTotal();
        return R.ok().data("attendanceList",attendanceList).data("total",total);
    }
    //审核通过
    @PutMapping("updateAttendanceOk/{id}")
    public R updateAttendanceOk(@PathVariable String id){
        Attendance attendance = new Attendance();
        attendance.setId(id);
        attendance.setAudit("1");
        attendanceService.updateById(attendance);
        return R.ok();
    }
    //审核失败
    @PutMapping("updateAttendanceNo/{id}")
    public R updateAttendanceNo(@PathVariable String id){
        Attendance attendance = new Attendance();
        attendance.setId(id);
        attendance.setAudit("2");
        attendanceService.updateById(attendance);
        return R.ok();
    }
}

