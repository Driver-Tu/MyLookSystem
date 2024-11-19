package com.zh.look.mapper;

import com.zh.look.bean.Doctors;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author admin
* @description 针对表【doctors(医生信息表)】的数据库操作Mapper
* @createDate 2024-11-11 11:35:57
* @Entity com.zh.look.bean.Doctors
*/
@Mapper
public interface DoctorsMapper extends BaseMapper<Doctors> {

}




