package com.information.staff.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.information.staff.entity.Contract;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.information.staff.entity.vo.ContractQueryVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 合同表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-12-14
 */
public interface ContractMapper extends BaseMapper<Contract> {

    IPage<Contract> pageListQuery(Page<Contract> clerkPage,@Param("contractQueryVo")ContractQueryVo contractQueryVo);
}
