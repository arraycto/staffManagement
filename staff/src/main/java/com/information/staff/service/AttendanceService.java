package com.information.staff.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.information.staff.entity.Attendance;
import com.baomidou.mybatisplus.extension.service.IService;
import com.information.staff.entity.vo.AttendanceQueryVo;

/**
 * <p>
 * 考勤表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-14
 */
public interface AttendanceService extends IService<Attendance> {

    IPage<Attendance> pageListQuery(Page<Attendance> attendancePage, AttendanceQueryVo attendanceQueryVo);
}
