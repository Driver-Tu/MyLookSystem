package com.zh.look.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.look.dao.UserRoles;
import com.zh.look.service.UserRolesService;
import com.zh.look.mapper.UserRolesMapper;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【User_Roles(用户角色关联表)】的数据库操作Service实现
* @createDate 2024-11-06 11:12:52
*/
@Service
public class UserRolesServiceImpl extends ServiceImpl<UserRolesMapper, UserRoles>
    implements UserRolesService{

}




