package com.zh.look.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.look.bean.dao.DiseaseCategory;

import java.util.List;

/**
* @author admin
* @description 针对表【DiseaseCategory(疾病分类表（所有者：系统管理员）)】的数据库操作Service
* @createDate 2024-11-27 11:24:39
*/
public interface DiseaseCategoryService extends IService<DiseaseCategory> {
    List<DiseaseCategory> selectAllDiseaseCategory();
}
