package com.zh.look.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zh.look.bean.UserRoles;
import com.zh.look.domain.dao.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author admin
* @description 针对表【Users(用户表)】的数据库操作Mapper
* @createDate 2024-11-06 11:12:52
* @Entity com.zh.look.bean.dao.Users
*/
@Mapper
public interface UsersMapper extends BaseMapper<Users> {
    /**
     * 根据关联表UserRoles查询用户角色id
     */
     List<UserRoles> getUserRoles(Integer userId);
}




