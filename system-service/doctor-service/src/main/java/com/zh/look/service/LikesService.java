package com.zh.look.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.look.bean.Likes;

/**
* @author admin
* @description 针对表【likes(评价点赞表)】的数据库操作Service
* @createDate 2024-11-12 13:23:22
*/
public interface LikesService extends IService<Likes> {

    Integer countDoctorLike(Integer doctorId);
}
