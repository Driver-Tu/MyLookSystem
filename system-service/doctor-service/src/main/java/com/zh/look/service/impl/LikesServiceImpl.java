package com.zh.look.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.look.bean.Likes;
import com.zh.look.mapper.LikesMapper;
import com.zh.look.service.LikesService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
* @author admin
* @description 针对表【likes(评价点赞表)】的数据库操作Service实现
* @createDate 2024-11-12 13:23:22
*/
@Service
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Likes>
    implements LikesService {

    private final LikesMapper likesMapper;
    private final StringRedisTemplate stringRedisTemplate;

    public LikesServiceImpl(LikesMapper likesMapper, StringRedisTemplate stringRedisTemplate) {
        this.likesMapper = likesMapper;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Integer countDoctorLike(Integer doctorId) {
        Integer i = likesMapper.countDoctorLike(doctorId);
        if(stringRedisTemplate.opsForValue().get("doctorLike:"+doctorId)!=null){
            return Integer.parseInt(Objects.requireNonNull(stringRedisTemplate.opsForValue().get("doctorLike:" + doctorId)));
        }
        //将获取到的存入redis，并且设置过期时间为半个小时
        stringRedisTemplate.opsForValue().set("doctorLike:"+doctorId,i.toString(),30, TimeUnit.MINUTES);
        return i;
    }
}




