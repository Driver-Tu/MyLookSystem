package com.zh.look.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.look.bean.Reviews;
import com.zh.look.domain.dto.DoctorDto;

import java.util.List;

/**
* @author admin
* @description 针对表【reviews(医生评价表)】的数据库操作Service
* @createDate 2024-11-12 13:23:22
*/
public interface ReviewsService extends IService<Reviews> {
    List<Reviews> getDoctorReviews(DoctorDto doctorDto);
    /**
     * 查看该医生的评分信息和评论
     */
}
