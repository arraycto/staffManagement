package com.information.staff.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.information.staff.entity.Clerk;
import com.information.staff.entity.Contract;
import com.information.staff.entity.Department;
import com.information.staff.entity.RewardsPunishments;
import com.information.staff.entity.vo.ClerkQueryVo;
import com.information.staff.entity.vo.RPQueryVo;
import com.information.staff.service.ClerkService;
import com.information.staff.service.RewardsPunishmentsService;
import com.information.staff.utils.JwtUtils;
import com.information.staff.utils.R;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 奖惩表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-14
 */
@RestController
@RequestMapping("/staff/rewards_punishments")
@CrossOrigin
public class RewardsPunishmentsController {
    @Autowired
    private RewardsPunishmentsService rewardsPunishmentsService;
    @Autowired
    private ClerkService clerkService;
    //1.分页查询所有
    @PostMapping("listPage/{current}/{limit}")
    public R getRewardsPunishmentsListPage(
            @PathVariable long current,
            @PathVariable long limit,
            @RequestBody(required = false) RPQueryVo rpQueryVo) {
        Page<RewardsPunishments> clerkPage = new Page<>(current,limit);
        IPage<RewardsPunishments> clerkIPage = rewardsPunishmentsService.pageListQuery(clerkPage,rpQueryVo);
        //获取总记录数
        long total = clerkIPage.getTotal();
        //每页类别集合
        List<RewardsPunishments> rewardsPunishmentsList = clerkIPage.getRecords();
        for (RewardsPunishments rewardsPunishments:rewardsPunishmentsList) {
            if (rewardsPunishments.getType().equals("0")) {
                rewardsPunishments.setType("奖励");
            } else {
                rewardsPunishments.setType("惩罚");
            }
        }
        return R.ok().data("rewardsPunishmentsList",rewardsPunishmentsList).data("total",total);
    }

    //3.查询所有
    @GetMapping("list")
    public R getRewardsPunishmentsList() {
        List<RewardsPunishments> rewardsPunishmentsList = rewardsPunishmentsService.list(null);
        return R.ok().data("rewardsPunishmentsList",rewardsPunishmentsList);
    }

    //2.根据id查询
    @GetMapping("{id}")
    public R getRewardsPunishmentsById(@PathVariable String id){
        RewardsPunishments rewardsPunishments = rewardsPunishmentsService.getById(id);
        if (StringUtils.isEmpty(rewardsPunishments)){
            return R.error();
        }
        return R.ok().data("rewardsPunishments",rewardsPunishments);
    }

    //4.根据id删除
    @DeleteMapping("{id}")
    public R deleteRewardsPunishmentsById(@PathVariable String id){
        boolean removeById = rewardsPunishmentsService.removeById(id);
        if (removeById){
            return R.ok();
        }
        return R.error().message("删除失败");
    }

    //5.修改
    @PutMapping
    public R updateRewardsPunishments(@RequestBody RewardsPunishments rewardsPunishments){
        boolean updateById = rewardsPunishmentsService.updateById(rewardsPunishments);
        if (updateById){
            return R.ok().message("修改成功");
        }
        return R.error().message("修改失败");
    }
    //6.增加
    @PostMapping
    public R addRewardsPunishments(@RequestBody RewardsPunishments rewardsPunishments){
        boolean save = rewardsPunishmentsService.save(rewardsPunishments);
        if (save){
            return R.ok().message("增加成功");
        }
        return R.error().message("增加失败");
    }
    //根据token获取他的奖惩信息
    @PostMapping("getRewardsPunishments/{current}/{limit}")
    public R getRewardsPunishments(@PathVariable long current,
                                   @PathVariable long limit,
                                   @RequestBody(required = false) RPQueryVo rpQueryVo,
                                   @RequestHeader("X-Token") String token) {
        String id = JwtUtils.getMemberIdByToken(token);
        QueryWrapper<Clerk> clerkQueryWrapper = new QueryWrapper<>();
        clerkQueryWrapper.eq("user_id",id);
        Clerk clerk = clerkService.getOne(clerkQueryWrapper);
        String clerkId = clerk.getId();
        QueryWrapper<RewardsPunishments> rewardsPunishmentsQueryWrapper = new QueryWrapper<>();
        rewardsPunishmentsQueryWrapper.eq("clerk_id",clerkId);
        Page<RewardsPunishments> rewardsPunishmentsPage = new Page<>(current,limit);
        if (!StringUtils.isEmpty(rpQueryVo.getType())) {
            rewardsPunishmentsQueryWrapper.eq("type",rpQueryVo.getType());
        }
        if (!StringUtils.isEmpty(rpQueryVo.getReason())) {
            rewardsPunishmentsQueryWrapper.like("reason",rpQueryVo.getReason());
        }
        if (!StringUtils.isEmpty(rpQueryVo.getAmount())) {
            rewardsPunishmentsQueryWrapper.like("amount",rpQueryVo.getAmount());
        }
        rewardsPunishmentsService.page(rewardsPunishmentsPage,rewardsPunishmentsQueryWrapper);
        List<RewardsPunishments> rewardsPunishmentsList = rewardsPunishmentsPage.getRecords();
        for (RewardsPunishments rewardsPunishments:rewardsPunishmentsList) {
            if (rewardsPunishments.getType().equals("0")) {
                rewardsPunishments.setType("奖励");
            } else {
                rewardsPunishments.setType("惩罚");
            }
        }
        long total = rewardsPunishmentsPage.getTotal();
        return R.ok().data("rewardsPunishmentsList",rewardsPunishmentsList).data("total",total);
    }
}

