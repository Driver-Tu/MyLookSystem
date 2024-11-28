package com.zh.look.mapper;

import com.zh.look.bean.dao.Disease;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author admin
* @description 针对表【Disease(具体疾病表（所有者：系统管理员）)】的数据库操作Mapper
* @createDate 2024-11-27 11:24:39
* @Entity com.zh.look.bean.dao.Disease
*/
@Mapper
public interface DiseaseMapper extends BaseMapper<Disease> {

}




