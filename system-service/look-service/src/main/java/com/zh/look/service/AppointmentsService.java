package com.zh.look.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.look.bean.Appointments;
import com.zh.look.bean.dto.AppointmentsDto;

/**
* @author admin
* @description 针对表【appointments(医生和患者的预约记录表)】的数据库操作Service
* @createDate 2024-11-18 15:27:00
*/
public interface AppointmentsService extends IService<Appointments> {

    Page<Appointments> selectAppointments(AppointmentsDto appointmentsDto);
}
