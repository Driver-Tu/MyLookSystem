package com.zh.look.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.look.ExceptionConfig.MyException;
import com.zh.look.bean.Doctors;
import com.zh.look.domain.dto.DoctorDto;
import com.zh.look.mapper.DoctorsMapper;
import com.zh.look.service.DoctorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @description 针对表【doctors(医生信息表)】的数据库操作Service实现
 * @createDate 2024-11-11 11:35:57
 */
@Service
public class DoctorsServiceImpl extends ServiceImpl<DoctorsMapper, Doctors>
        implements DoctorsService {

    @Autowired
    private DoctorsMapper doctorMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    private Page<Doctors> page;
    Random random = new Random();
    @Override
    @Transactional
    public Page<Doctors> selectDoctors(DoctorDto doctorDto) {
        page = new Page<>(doctorDto.getPageNum(), doctorDto.getPageSize());
        if (redisTemplate.opsForValue().get("doctors:" + JSONUtil.toJsonStr(doctorDto)) == null) {
            getDoctor(page,doctorDto);
        }
        String s = redisTemplate.opsForValue().get("doctors:"+JSONUtil.toJsonStr(doctorDto));
        List<Doctors> doctors = JSONUtil.toList(s, Doctors.class);
        page.setRecords(doctors);
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDoctor(Doctors doctors) {
        if(doctors.getUserId()!= StpUtil.getLoginIdAsInt()){
            throw new MyException(520,"无法修改");
        }
        if(updateById(doctors)){
            //将最基本的分页参数删除，并且再次缓存
            page=new Page<Doctors>(1,20);
            DoctorDto doctorDto = new DoctorDto();
            doctorDto.setPageNum(1);
            doctorDto.setPageSize(20);
            //校验redis里面是否有该数据
            getDoctor(page,doctorDto);
            return updateById(doctors);
        }else {
         throw new MyException(521,"修改失败");
        }
    }

    public void getDoctor(Page<Doctors> page,DoctorDto doctorDto) {
        Page<Doctors> doctorsPage = doctorMapper.selectPage(page, new LambdaQueryWrapper<Doctors>()
                .like(doctorDto.getName() != null, Doctors::getName, doctorDto.getName())
                .like(doctorDto.getSpecialty() != null, Doctors::getSpecialty, doctorDto.getSpecialty())
                .like(doctorDto.getHospital() != null, Doctors::getHospital, doctorDto.getHospital())
                .like(doctorDto.getBio() != null, Doctors::getBio, doctorDto.getBio()));
        redisTemplate.opsForValue().set("doctors:"+JSONUtil.toJsonStr(doctorDto), JSONUtil.toJsonStr(doctorsPage.getRecords()), random.nextInt(31) + 30, TimeUnit.SECONDS);
    }
}




