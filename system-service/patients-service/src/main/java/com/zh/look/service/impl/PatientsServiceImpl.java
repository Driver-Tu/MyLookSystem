package com.zh.look.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.look.ExceptionConfig.MyException;
import com.zh.look.bean.Patients;
import com.zh.look.domain.dto.PatientsDto;
import com.zh.look.mapper.PatientsMapper;
import com.zh.look.service.PatientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author admin
* @description 针对表【patients(患者信息表)】的数据库操作Service实现
* @createDate 2024-11-12 13:23:22
*/
@Service
public class PatientsServiceImpl extends ServiceImpl<PatientsMapper, Patients>
    implements PatientsService{

    @Autowired
    private PatientsMapper patientsMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    private Page<Patients> page=new Page<>(1,20);;
    @Override
    public Page<Patients> selectPatients(PatientsDto patientsDto) {
        page = new Page<>(patientsDto.getPageNum(), patientsDto.getPageSize());
        if (redisTemplate.opsForValue().get("patients:" + JSONUtil.toJsonStr(patientsDto)) == null) {
            return getPatients(patientsDto);
        }
        String s = redisTemplate.opsForValue().get("patients:"+JSONUtil.toJsonStr(patientsDto));
        List<Patients> patients = JSONUtil.toList(s, Patients.class);
        page.setRecords(patients);
        page.setTotal(patients.size());
        return page;
    }

    @Override
    @Transactional
    public boolean updatePatients(Patients patients) {
        if(patients.getId()!=StpUtil.getLoginIdAsInt()){
            throw new MyException(601,"只能修改自己的信息");
        }
        if(patientsMapper.updateById(patients)>0){
            PatientsDto patientsDto = new PatientsDto();
            patientsDto.setPageNum(1);
            patientsDto.setPageSize(20);
            //再次写入覆盖掉原来的数据
            redisTemplate.opsForValue().set("patients:"+JSONUtil.toJsonStr(patientsDto), JSONUtil.toJsonStr(getPatients(patientsDto)));
            return true;
        }else {
            throw new MyException(602,"修改失败");
        }
    }

    @Override
    public boolean addPatients(Patients patients) {
        if(patientsMapper.insert(patients)>0){
            PatientsDto patientsDto = new PatientsDto();
            patientsDto.setPageNum(1);
            patientsDto.setPageSize(20);
            //再次写入覆盖掉原来的数据
            redisTemplate.opsForValue().set("patients:"+JSONUtil.toJsonStr(patientsDto), JSONUtil.toJsonStr(getPatients(patientsDto)));
            return true;
        }else {
            throw new MyException(603,"添加失败");
        }
    }

    @Override
    public Boolean getADoctor(Patients patients) {
        return null;
    }


    private Page<Patients> getPatients(PatientsDto patientsDto) {
        Page<Patients> patientsPage = patientsMapper.selectPage(page, new LambdaQueryWrapper<Patients>()
                .like(patientsDto.getPatientName() != null, Patients::getName, patientsDto.getPatientName())
                .eq(patientsDto.getPatientSex() != null, Patients::getSex, patientsDto.getPatientSex())
                .eq(patientsDto.getPatientAge() != null, Patients::getAge, patientsDto.getPatientAge())
                .eq(patientsDto.getPhone() != null, Patients::getPhone, patientsDto.getPhone())
                .eq(patientsDto.getPatientId() != null, Patients::getUserId, patientsDto.getPatientId())
                .orderByDesc(Patients::getUpdatedAt));
        redisTemplate.opsForValue().set("patients:"+ JSONUtil.toJsonStr(patientsDto), JSONUtil.toJsonStr(patientsPage.getRecords()));
        return patientsPage;
    }
}




