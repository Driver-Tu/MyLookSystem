package com.zh.look.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.look.bean.Likes;
import com.zh.look.service.LikesService;
import com.zh.look.mapper.LikesMapper;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【likes(评价点赞表)】的数据库操作Service实现
* @createDate 2024-11-12 13:23:22
*/
@Service
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Likes>
    implements LikesService{

}




