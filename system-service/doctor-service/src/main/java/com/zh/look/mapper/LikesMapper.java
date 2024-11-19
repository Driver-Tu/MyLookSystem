package com.zh.look.mapper;

import com.zh.look.bean.Likes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author admin
* @description 针对表【likes(评价点赞表)】的数据库操作Mapper
* @createDate 2024-11-12 13:23:22
* @Entity com.zh.look.bean.Likes
*/
@Mapper
public interface LikesMapper extends BaseMapper<Likes> {
    Integer countDoctorLike(Integer doctorId);
}




