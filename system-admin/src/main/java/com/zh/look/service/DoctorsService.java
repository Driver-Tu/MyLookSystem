package com.zh.look.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.look.bean.Doctors;
import com.zh.look.domain.dto.DoctorDto;

/**
* @author admin
* @description 针对表【doctors(医生信息表)】的数据库操作Service
* @createDate 2024-11-11 11:35:57
*/
public interface DoctorsService extends IService<Doctors> {
    /**
     * 查询医生信息
     */
    Page<Doctors> selectDoctors(DoctorDto doctorDto);

    /**
     * 修改医生信息
     */
    boolean updateDoctor(Doctors doctors);
}
