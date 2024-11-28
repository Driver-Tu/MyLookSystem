package com.zh.look.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.look.bean.Patients;
import com.zh.look.domain.dto.PatientsDto;
import com.zh.look.domain.vo.PatientsVo;

/**
* @author admin
* @description 针对表【patients(患者信息表)】的数据库操作Service
* @createDate 2024-11-12 13:23:22
*/
public interface PatientsService extends IService<Patients> {
    /**
     * 查询患者信息
     */
    Page<PatientsVo> selectPatients(PatientsDto patientsDto);
    /**
     * 修改患者信息
     */
    boolean updatePatients(Patients patients);

    /**
     * 添加患者信息
     * @param patients
     * @return
     */
    boolean addPatients(Patients patients);

    Boolean getADoctor(Patients patients);
}
