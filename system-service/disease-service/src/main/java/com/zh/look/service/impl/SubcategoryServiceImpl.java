package com.zh.look.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.look.bean.dao.Subcategory;
import com.zh.look.service.SubcategoryService;
import com.zh.look.mapper.SubcategoryMapper;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【SubCategory(疾病子分类表（所有者：系统管理员）)】的数据库操作Service实现
* @createDate 2024-11-27 11:24:40
*/
@Service
public class SubcategoryServiceImpl extends ServiceImpl<SubcategoryMapper, Subcategory>
    implements SubcategoryService{

}




