package com.information.staff.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.information.staff.entity.Attendance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.information.staff.entity.vo.AttendanceQueryVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 考勤表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-12-14
 */
public interface AttendanceMapper extends BaseMapper<Attendance> {

    IPage<Attendance> pageListQuery(Page<Attendance> attendancePage,@Param("attendanceQueryVo") AttendanceQueryVo attendanceQueryVo);
}
