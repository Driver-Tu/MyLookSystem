package com.zh.look.mapper;

import com.zh.look.dao.UserRoles;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author admin
* @description 针对表【User_Roles(用户角色关联表)】的数据库操作Mapper
* @createDate 2024-11-06 11:12:52
* @Entity com.zh.look.bean.dao.UserRoles
*/
@Mapper
public interface UserRolesMapper extends BaseMapper<UserRoles> {

}




