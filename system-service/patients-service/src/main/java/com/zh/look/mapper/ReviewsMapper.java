package com.zh.look.mapper;

import com.zh.look.bean.Reviews;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author admin
* @description 针对表【reviews(医生评价表)】的数据库操作Mapper
* @createDate 2024-11-12 13:23:22
* @Entity com.zh.look.bean.Reviews
*/
@Mapper
public interface ReviewsMapper extends BaseMapper<Reviews> {

}




