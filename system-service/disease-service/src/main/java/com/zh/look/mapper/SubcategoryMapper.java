package com.zh.look.mapper;

import com.zh.look.bean.dao.Subcategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author admin
* @description 针对表【SubCategory(疾病子分类表（所有者：系统管理员）)】的数据库操作Mapper
* @createDate 2024-11-27 11:24:40
* @Entity com.zh.look.bean.dao.Subcategory
*/
@Mapper
public interface SubcategoryMapper extends BaseMapper<Subcategory> {

}




