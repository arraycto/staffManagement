package com.information.staff.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.information.staff.entity.RewardsPunishments;
import com.information.staff.entity.vo.RPQueryVo;
import com.information.staff.mapper.RewardsPunishmentsMapper;
import com.information.staff.service.RewardsPunishmentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 奖惩表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-14
 */
@Service
public class RewardsPunishmentsServiceImpl extends ServiceImpl<RewardsPunishmentsMapper, RewardsPunishments> implements RewardsPunishmentsService {

    @Override
    public IPage<RewardsPunishments> pageListQuery(Page<RewardsPunishments> clerkPage, RPQueryVo rpQueryVo) {
        IPage<RewardsPunishments> rewardsPunishmentsIPage = baseMapper.pageListQuery(clerkPage,rpQueryVo);
        return rewardsPunishmentsIPage;
    }
}
