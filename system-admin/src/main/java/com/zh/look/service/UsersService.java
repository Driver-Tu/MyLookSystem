package com.zh.look.service;

import com.zh.look.dao.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.look.domain.vo.UserVo;


/**
* @author admin
* @description 针对表【Users(用户表)】的数据库操作Service
* @createDate 2024-11-06 11:12:52
*/
public interface UsersService extends IService<Users> {
    UserVo Login(String userName, String password,String device);
}
