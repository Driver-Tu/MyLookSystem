package com.zh.look.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.look.ExceptionConfig.MyException;
import com.zh.look.dao.Users;
import com.zh.look.domain.vo.UserVo;
import com.zh.look.mapper.UsersMapper;
import com.zh.look.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author admin
 * @description 针对表【Users(用户表)】的数据库操作Service实现
 * @createDate 2024-11-06 11:12:52
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
        implements UsersService {

    @Autowired
    private UsersMapper usersMapper;


    @Override
    public UserVo Login(String userName, String password,String device) {
        Users users = usersMapper.selectOne(new LambdaQueryWrapper<Users>()
                .eq(Users::getUsername, userName)
                .eq(Users::getPasswordHash, DigestUtil.md5Hex(password + userName.substring(1, 3))));
        if (users == null) {
            throw new MyException(510, "没有该用户");
        }
        SaSession sessionByLoginId = StpUtil.getSessionByLoginId(users.getUserId());
        if(StpUtil.isLogin(users.getUserId())&& !sessionByLoginId.getTokenValueListByDevice(device).isEmpty()){
            throw new MyException(506,"账户已登录，请先退出登录");
        }
        StpUtil.login(users.getUserId(),device);
        String tokenValue = StpUtil.getTokenValue();
        /**
         * 登录成功，返回token，token保存在redis中，key为token，value为userId，过期时间为7天，
         */
        return new UserVo(tokenValue, users.getUserId());
    }
}




