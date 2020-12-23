package com.information.staff.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.information.staff.entity.Announcement;
import com.information.staff.entity.Department;
import com.information.staff.service.*;
import com.information.staff.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 公告表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-14
 */
@RestController
@RequestMapping("/staff/announcement")
@CrossOrigin
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;
    @Autowired
    private RewardsPunishmentsService rewardsPunishmentsService;
    @Autowired
    private ClerkService clerkService;
    @Autowired
    private AttendanceService attendanceService;
    //1.分页查询所有
    @PostMapping("listPage/{current}/{limit}")
    public R getAnnouncementListPage(@PathVariable long current, @PathVariable long limit,@RequestBody(required = false) Announcement announcement) {
        QueryWrapper<Announcement> announcementQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(announcement.getTitle())) {
            announcementQueryWrapper.like("title",announcement.getTitle());
        }
        //分页 两个参数：当前页，每页数据个数
        Page<Announcement> announcementPage = new Page<>(current,limit);
        //查询数据放在 classifyPage 中
        announcementService.page(announcementPage,announcementQueryWrapper);
        //获取总记录数
        long total = announcementPage.getTotal();
        //每页类别集合
        List<Announcement> announcementList = announcementPage.getRecords();
        return R.ok().data("announcementList",announcementList).data("total",total);
    }
    //查询到日期的公告
    @PostMapping("listPageClerk/{current}/{limit}")
    public R getAnnouncementListPageClerk(@PathVariable long current, @PathVariable long limit,@RequestBody(required = false) Announcement announcement) {
        QueryWrapper<Announcement> announcementQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(announcement.getTitle())) {
            announcementQueryWrapper.like("title",announcement.getTitle());
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        announcementQueryWrapper.le("time",df.format(new Date()));
        //分页 两个参数：当前页，每页数据个数
        Page<Announcement> announcementPage = new Page<>(current,limit);
        //查询数据放在 classifyPage 中
        announcementService.page(announcementPage,announcementQueryWrapper);
        //获取总记录数
        long total = announcementPage.getTotal();
        //每页类别集合
        List<Announcement> announcementList = announcementPage.getRecords();
        return R.ok().data("announcementList",announcementList).data("total",total);
    }
    //3.查询所有
    @GetMapping("list")
    public R getAnnouncementList() {
        List<Announcement> announcementList = announcementService.list(null);
        return R.ok().data("announcementList",announcementList);
    }

    //2.根据id查询
    @GetMapping("{id}")
    public R getAnnouncementById(@PathVariable String id){
        Announcement announcement = announcementService.getById(id);
        if (StringUtils.isEmpty(announcement)){
            return R.error().message("没有此公告");
        }
        return R.ok().data("announcement",announcement);
    }

    //4.根据id删除
    @DeleteMapping("{id}")
    public R deleteAnnouncementById(@PathVariable String id){
        boolean removeById = announcementService.removeById(id);
        if (removeById){
            return R.ok();
        }
        return R.error().message("删除失败");
    }

    //5.修改
    @PutMapping
    public R updateAnnouncement(@RequestBody Announcement announcement){
        boolean updateById = announcementService.updateById(announcement);
        if (updateById){
            return R.ok().message("修改成功");
        }
        return R.error().message("修改失败");
    }
    //6.增加
    @PostMapping
    public R addAnnouncement(@RequestBody Announcement announcement){
        boolean save = announcementService.save(announcement);

        if (save){
            return R.ok().message("增加成功");
        }
        return R.error().message("增加失败");
    }
    //获取首页数据
    @GetMapping("numInfo")
    public R numInfo(){
        QueryWrapper<Announcement> announcementQueryWrapper = new QueryWrapper<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        announcementQueryWrapper.le("time",df.format(new Date()));
        int annCount = announcementService.count(announcementQueryWrapper);
        int clerkCount = clerkService.count(null);
        int attendanceCount = attendanceService.count(null);
        int rewardsPunishmentsCount = rewardsPunishmentsService.count(null);
        return R.ok()
                .data("annCount",annCount)
                .data("clerkCount",clerkCount)
                .data("attendanceCount",attendanceCount)
                .data("rewardsPunishmentsCount",rewardsPunishmentsCount);
    }
}

