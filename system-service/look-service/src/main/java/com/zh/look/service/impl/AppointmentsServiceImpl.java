package com.zh.look.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.look.bean.Appointments;
import com.zh.look.bean.dto.AppointmentsDto;
import com.zh.look.mapper.AppointmentsMapper;
import com.zh.look.service.AppointmentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
* @author admin
* @description 针对表【appointments(医生和患者的预约记录表)】的数据库操作Service实现
* @createDate 2024-11-18 15:27:00
*/
@Service
@Slf4j
public class AppointmentsServiceImpl extends ServiceImpl<AppointmentsMapper, Appointments>
    implements AppointmentsService{
    private final AppointmentsMapper appointmentsMapper;
    private final StringRedisTemplate stringRedisTemplate;
    Page<Appointments> page = new Page<>(1, 20);

    public AppointmentsServiceImpl(AppointmentsMapper appointmentsMapper, StringRedisTemplate stringRedisTemplate) {
        this.appointmentsMapper = appointmentsMapper;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Page<Appointments> selectAppointments(AppointmentsDto appointmentsDto) {
        if(stringRedisTemplate.opsForValue().get("appointments:"+JSONUtil.toJsonStr(appointmentsDto))!=null){
            String s = stringRedisTemplate.opsForValue().get("appointments:" + JSONUtil.toJsonStr(appointmentsDto));
            Page bean = JSONUtil.toBean(s, Page.class);
            return page;
        }
        return getAppointmentsPage(appointmentsDto);
    }

    private Page<Appointments> getAppointmentsPage(AppointmentsDto appointmentsDto) {
        Page<Appointments> appointmentsPage = appointmentsMapper
                .selectPage(page, new LambdaQueryWrapper<Appointments>()
                        .eq(null != appointmentsDto.getStatus(), Appointments::getStatus, appointmentsDto.getStatus())
                        .eq(null != appointmentsDto.getAppointmentDate(), Appointments::getAppointmentDate, appointmentsDto.getAppointmentDate())
                        .eq(null != appointmentsDto.getDoctorId(), Appointments::getDoctorId,appointmentsDto.getDoctorId())
                        .eq(null != appointmentsDto.getPatientId(), Appointments::getPatientId,appointmentsDto.getPatientId()));
        stringRedisTemplate.opsForValue().set("appointments:"+JSONUtil.toJsonStr(appointmentsDto), JSONUtil.toJsonStr(appointmentsPage),30, TimeUnit.SECONDS);
        return appointmentsPage;
    }
}




