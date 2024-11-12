package com.zh.look.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.look.bean.Reviews;
import com.zh.look.service.ReviewsService;
import com.zh.look.mapper.ReviewsMapper;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【reviews(医生评价表)】的数据库操作Service实现
* @createDate 2024-11-12 13:23:22
*/
@Service
public class ReviewsServiceImpl extends ServiceImpl<ReviewsMapper, Reviews>
    implements ReviewsService{

}




