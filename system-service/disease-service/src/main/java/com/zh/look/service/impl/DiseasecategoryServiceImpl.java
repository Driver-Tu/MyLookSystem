package com.zh.look.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.look.bean.dao.DiseaseCategory;
import com.zh.look.mapper.DiseaseCategoryMapper;
import com.zh.look.service.DiseaseCategoryService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
* @author admin
* @description 针对表【DiseaseCategory(疾病分类表（所有者：系统管理员）)】的数据库操作Service实现
* @createDate 2024-11-27 11:24:39
*/
@Service
public class DiseasecategoryServiceImpl extends ServiceImpl<DiseaseCategoryMapper, DiseaseCategory>
    implements DiseaseCategoryService {

    private final DiseaseCategoryMapper diseaseCategoryMapper;
    private final StringRedisTemplate stringRedisTemplate;

    public DiseasecategoryServiceImpl(DiseaseCategoryMapper diseaseCategoryMapper, StringRedisTemplate stringRedisTemplate) {
        this.diseaseCategoryMapper = diseaseCategoryMapper;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public List<DiseaseCategory> selectAllDiseaseCategory() {
        if(!Objects.requireNonNull(stringRedisTemplate.opsForList().range("DiseaseCategory", 0, -1)).isEmpty()){
            List<String> range = stringRedisTemplate.opsForList().range("DiseaseCategory", 0, -1);
            if (range != null) {
                return range.stream().parallel().map(x -> JSONUtil.toBean(x, DiseaseCategory.class)).toList();
            }
        }
        List<DiseaseCategory> diseaseCategories = diseaseCategoryMapper.selectList(new LambdaQueryWrapper<DiseaseCategory>());
        for (DiseaseCategory diseaseCategory : diseaseCategories) {
            stringRedisTemplate.opsForList().rightPush("DiseaseCategory", JSONUtil.toJsonStr(diseaseCategory));
        }
        return diseaseCategories;
    }
}




