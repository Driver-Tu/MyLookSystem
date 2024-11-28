package com.zh.look.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zh.look.bean.dao.DiseaseCategory;
import org.apache.ibatis.annotations.Mapper;

/**
* @author admin
* @description 针对表【DiseaseCategory(疾病分类表（所有者：系统管理员）)】的数据库操作Mapper
* @createDate 2024-11-27 11:24:39
* @Entity com.zh.look.bean.dao.DiseaseCategory
*/
@Mapper
public interface DiseaseCategoryMapper extends BaseMapper<DiseaseCategory> {

}




