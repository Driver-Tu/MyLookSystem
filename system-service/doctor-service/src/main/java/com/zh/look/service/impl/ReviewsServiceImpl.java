package com.zh.look.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.look.bean.Reviews;
import com.zh.look.domain.dto.DoctorDto;
import com.zh.look.mapper.ReviewsMapper;
import com.zh.look.service.ReviewsService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
* @author admin
* @description 针对表【reviews(医生评价表)】的数据库操作Service实现
* @createDate 2024-11-12 13:23:22
*/
@Service
public class ReviewsServiceImpl extends ServiceImpl<ReviewsMapper, Reviews>
    implements ReviewsService {

    private final ReviewsMapper reviewsMapper;
    private final StringRedisTemplate stringRedisTemplate;

    public ReviewsServiceImpl(ReviewsMapper reviewsMapper, StringRedisTemplate stringRedisTemplate) {
        this.reviewsMapper = reviewsMapper;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public List<Reviews> getDoctorReviews(DoctorDto doctorDto) {
        Page<Reviews> page = new Page<>(doctorDto.getPageNum(),doctorDto.getPageSize());
        if(stringRedisTemplate.opsForValue().get("doctorReviews:"+doctorDto)!=null){
            String s = stringRedisTemplate.opsForValue().get("doctorReviews:" + doctorDto);
            return JSONUtil.toList(s, Reviews.class);
        }
        Page<Reviews> reviewsPage = reviewsMapper.selectPage(page, new LambdaQueryWrapper<Reviews>().eq(Reviews::getDoctorId, doctorDto.getDoctorId()));
        stringRedisTemplate.opsForValue().set("doctorReviews:"+doctorDto, JSONUtil.toJsonStr(reviewsPage.getRecords()),30, TimeUnit.MINUTES);
        return reviewsPage.getRecords();
    }
}




