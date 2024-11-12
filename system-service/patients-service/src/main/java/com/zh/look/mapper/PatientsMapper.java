package com.zh.look.mapper;

import com.zh.look.bean.Patients;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author admin
* @description 针对表【patients(患者信息表)】的数据库操作Mapper
* @createDate 2024-11-12 13:23:22
* @Entity com.zh.look.bean.Patients
*/
@Mapper
public interface PatientsMapper extends BaseMapper<Patients> {

}




