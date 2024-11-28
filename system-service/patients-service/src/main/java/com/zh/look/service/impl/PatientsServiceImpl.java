package com.zh.look.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.look.ExceptionConfig.MyException;
import com.zh.look.IDCardLock.IDCardEncryptor;
import com.zh.look.bean.Patients;
import com.zh.look.domain.dto.PatientsDto;
import com.zh.look.domain.vo.PatientsVo;
import com.zh.look.mapper.PatientsMapper;
import com.zh.look.service.PatientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @description 针对表【patients(患者信息表)】的数据库操作Service实现
 * @createDate 2024-11-12 13:23:22
 */
@Service
public class PatientsServiceImpl extends ServiceImpl<PatientsMapper, Patients>
        implements PatientsService {

    @Autowired
    private PatientsMapper patientsMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    private Page<Patients> page = new Page<>(1, 20);
    ;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private IDCardEncryptor iDCardEncryptor;

    @Override
    public Page<PatientsVo> selectPatients(PatientsDto patientsDto) {
        page = new Page<>(patientsDto.getPageNum(), patientsDto.getPageSize());
        if (redisTemplate.opsForValue().get("patients:" + JSONUtil.toJsonStr(patientsDto)) == null) {
            return getPatients(patientsDto);
        }
        String s = redisTemplate.opsForValue().get("patients:" + JSONUtil.toJsonStr(patientsDto));
        return JSONUtil.toBean(s, Page.class);

    }

    @Override
    @Transactional
    public boolean updatePatients(Patients patients) {
        if (patients.getId() != StpUtil.getLoginIdAsInt()) {
            throw new MyException(601, "只能修改自己的信息");
        }
        if (patientsMapper.updateById(patients) > 0) {
            PatientsDto patientsDto = new PatientsDto();
            patientsDto.setPageNum(1);
            patientsDto.setPageSize(20);
            //删除数据
            stringRedisTemplate.delete("patients:" + JSONUtil.toJsonStr(patientsDto));
            return true;
        } else {
            throw new MyException(602, "修改失败");
        }
    }


    @Override
    @Transactional
    public boolean addPatients(Patients patients) {
        System.out.println(patients);
        //加密身份证
        try {
            String s = iDCardEncryptor.encryptIDCard(patients.getPeopleNum());
            patients.setPeopleNum(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (patientsMapper.insert(patients) > 0) {
            PatientsDto patientsDto = new PatientsDto();
            patientsDto.setPageNum(1);
            patientsDto.setPageSize(20);
            //再次写入覆盖掉原来的数据
            getPatients(patientsDto);
            return true;
        } else {
            throw new MyException(603, "添加失败");
        }
    }

    @Override
    public Boolean getADoctor(Patients patients) {
        //
        return null;
    }


    private Page<PatientsVo> getPatients(PatientsDto patientsDto) {
        Page<Patients> patientsPage = patientsMapper.selectPage(page, new LambdaQueryWrapper<Patients>()
                .like(patientsDto.getPatientName() != null, Patients::getName, patientsDto.getPatientName())
                .eq(patientsDto.getPhone() != null, Patients::getPhone, patientsDto.getPhone())
                .eq(patientsDto.getPatientId() != null, Patients::getUserId, patientsDto.getPatientId())
                .orderByDesc(Patients::getUpdatedAt));
        List<PatientsVo> patientsVos = BeanUtil.copyToList(patientsPage.getRecords(), PatientsVo.class);
        List<PatientsVo> list = patientsVos.stream().parallel().peek(x -> {
            String s = "";
            try {
                s= iDCardEncryptor.decryptIDCard(x.getPeopleNum());
                s=extractInfo(s);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
            String[] split = s.split(",");
            x.setBirthDate(split[0]);
            x.setSex(split[1]);
            try {
                x.setPeopleNum(DesensitizedUtil.idCardNum(iDCardEncryptor.decryptIDCard(x.getPeopleNum()), 1, 2));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toList();
        if(patientsDto.getGetBirthDate()!=null){
            list=list.stream().filter(x->x.getBirthDate().equals(patientsDto.getGetBirthDate())).toList();
        }
        if(patientsDto.getPatientSex()!=null){
            list=list.stream().filter(x->x.getSex().equals(patientsDto.getPatientSex())).toList();
        }
        Page<PatientsVo> patientsVoPage = new Page<>(patientsDto.getPageNum(), patientsDto.getPageSize());
        patientsVoPage.setRecords(list);
        patientsVoPage.setSize(patientsPage.getSize());
        patientsVoPage.setTotal(patientsPage.getTotal());
        patientsVoPage.setCurrent(patientsPage.getCurrent());
        patientsVoPage.setPages(patientsPage.getPages());
        redisTemplate.opsForValue().set("patients:" + JSONUtil.toJsonStr(patientsDto), JSONUtil.toJsonStr(patientsVoPage), 30, TimeUnit.SECONDS);
        return patientsVoPage;
    }

    private String extractInfo(String idCard) {
        if(idCard==null){
            return "0,未知";
        }
        // 提取出生日期
        String birthYear = idCard.substring(6, 10);
        String birthMonth = idCard.substring(10, 12);
        String birthDay = idCard.substring(12, 14);

        String birthDate = birthYear + "-" + birthMonth + "-" + birthDay;

        // 确定性别
        char genderDigit = idCard.charAt(16);
        String gender = (genderDigit % 2 == 0) ? "女" : "男";
        return birthDate + "," + gender;
    }
}




