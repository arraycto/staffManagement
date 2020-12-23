package com.information.staff.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.information.staff.entity.Attendance;
import com.information.staff.entity.vo.AttendanceQueryVo;
import com.information.staff.mapper.AttendanceMapper;
import com.information.staff.service.AttendanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 考勤表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-14
 */
@Service
public class AttendanceServiceImpl extends ServiceImpl<AttendanceMapper, Attendance> implements AttendanceService {

    @Override
    public IPage<Attendance> pageListQuery(Page<Attendance> attendancePage, AttendanceQueryVo attendanceQueryVo) {
        IPage<Attendance> attendanceIPage = baseMapper.pageListQuery(attendancePage,attendanceQueryVo);
        return attendanceIPage;
    }
}
