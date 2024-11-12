package com.zh.look.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.look.bean.Appointments;
import com.zh.look.service.AppointmentsService;
import com.zh.look.mapper.AppointmentsMapper;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【appointments(医生和患者的预约记录表)】的数据库操作Service实现
* @createDate 2024-11-12 13:23:22
*/
@Service
public class AppointmentsServiceImpl extends ServiceImpl<AppointmentsMapper, Appointments>
    implements AppointmentsService{

}




