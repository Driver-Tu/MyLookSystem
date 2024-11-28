package com.zh.look.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.look.bean.dao.Disease;
import com.zh.look.service.DiseaseService;
import com.zh.look.mapper.DiseaseMapper;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【Disease(具体疾病表（所有者：系统管理员）)】的数据库操作Service实现
* @createDate 2024-11-27 11:24:39
*/
@Service
public class DiseaseServiceImpl extends ServiceImpl<DiseaseMapper, Disease>
    implements DiseaseService{

}




