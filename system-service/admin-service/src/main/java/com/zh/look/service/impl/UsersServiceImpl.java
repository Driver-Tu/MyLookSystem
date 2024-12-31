package com.zh.look.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.look.ExceptionConfig.MyException;
import com.zh.look.domain.dao.Users;
import com.zh.look.domain.dto.UserLoginDto;
import com.zh.look.domain.vo.UserVo;
import com.zh.look.mapper.UsersMapper;
import com.zh.look.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author admin
 * @description 针对表【Users(用户表)】的数据库操作Service实现
 * @createDate 2024-11-06 11:12:52
 */
@Service
@RequiredArgsConstructor
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
        implements UsersService {
    private final UsersMapper usersMapper;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public UserVo LoginByUserName(UserLoginDto userLoginDto) {
        equalsCode(userLoginDto.getCode(), userLoginDto.getUuid());
        Users users = usersMapper.selectOne(new LambdaQueryWrapper<Users>()
                .eq(Users::getUsername, userLoginDto.getUserName())
                .eq(Users::getPasswordHash, DigestUtil.md5Hex(userLoginDto.getPassword() + userLoginDto.getUserName().substring(1, 3))));
        extracted(userLoginDto.getDevice(), users);
        StpUtil.login(users.getUserId(),userLoginDto.getDevice());
        String tokenValue = StpUtil.getTokenValue();
        /**
         * 登录成功，返回token，token保存在redis中，key为token，value为userId，过期时间为7天，
         */
        return new UserVo(tokenValue, users.getUserId());
    }

    private void equalsCode(String code,String uuid) {
        if(!Objects.requireNonNull(stringRedisTemplate.opsForValue().get(uuid)).isEmpty()){
            String s = Objects.requireNonNull(stringRedisTemplate.opsForValue().get(uuid));
            if(s.equals(code)){
                stringRedisTemplate.delete(uuid);
            }else {
                throw new MyException(803,"验证码错误");
            }
        }else {
            throw new MyException(802,"验证码已过期");
        }
    }

    @Override
    public UserVo LoginByTelephone(String telephone, String device) {
        //比对数据库中的数据，拿到用户
        Users users = usersMapper.selectOne(new LambdaQueryWrapper<Users>()
                .eq(Users::getPhone, telephone));
        /**
         * 获取ssm短信验证码，并且存储redis一分钟，并且比对
         */
        extracted(device, users);
        StpUtil.login(users.getUserId(),device);
        String tokenValue = StpUtil.getTokenValue();
        /**
         * 登录成功，返回token，token保存在redis中，key为token，value为userId，过期时间为7天，
         */
        return new UserVo(tokenValue, users.getUserId());
    }

    /**
     * 校验是否已经登录，或者没有该信息用户
     * @param device
     * @param users
     */
    private static void extracted(String device, Users users) {
        if (users == null) {
            throw new MyException(510, "没有该用户，请注册");
        }
        SaSession sessionByLoginId = StpUtil.getSessionByLoginId(users.getUserId());
        if(StpUtil.isLogin(users.getUserId())&& !sessionByLoginId.getTokenValueListByDevice(device).isEmpty()){
            throw new MyException(506,"账户已登录，请先退出登录");
        }
    }
}




