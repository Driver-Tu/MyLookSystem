package com.zh.look.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zh.look.bean.UserRoles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author admin
* @description 针对表【User_Roles(用户角色关联表)】的数据库操作Mapper
* @createDate 2024-11-06 11:12:52
* @Entity com.zh.look.bean.dao.com.zh.look.bean.UserRoles
*/
@Mapper
public interface UserRolesMapper extends BaseMapper<UserRoles> {
    @Select("select * from User_Roles where user_id = #{userId}")
    List<UserRoles> selectUserRole(Integer userId);
}




