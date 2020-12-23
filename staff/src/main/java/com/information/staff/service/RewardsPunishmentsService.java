package com.information.staff.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.information.staff.entity.RewardsPunishments;
import com.baomidou.mybatisplus.extension.service.IService;
import com.information.staff.entity.vo.RPQueryVo;

/**
 * <p>
 * 奖惩表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-14
 */
public interface RewardsPunishmentsService extends IService<RewardsPunishments> {

    IPage<RewardsPunishments> pageListQuery(Page<RewardsPunishments> clerkPage, RPQueryVo rpQueryVo);
}
