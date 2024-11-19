package com.zh.look.mapper;

import com.zh.look.bean.Appointments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author admin
* @description 针对表【appointments(医生和患者的预约记录表)】的数据库操作Mapper
* @createDate 2024-11-18 15:27:00
* @Entity com.zh.look.bean.Appointments
*/
@Mapper
public interface AppointmentsMapper extends BaseMapper<Appointments> {

}




