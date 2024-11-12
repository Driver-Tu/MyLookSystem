package com.zh.look.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.look.bean.Patients;
import com.zh.look.service.PatientsService;
import com.zh.look.mapper.PatientsMapper;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【patients(患者信息表)】的数据库操作Service实现
* @createDate 2024-11-12 13:23:22
*/
@Service
public class PatientsServiceImpl extends ServiceImpl<PatientsMapper, Patients>
    implements PatientsService{

}




