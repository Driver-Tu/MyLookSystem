package com.zh.look.satoken;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.json.JSONUtil;
import com.zh.look.mapper.UserRolesMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private UserRolesMapper userRolesMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<String> getPermissionList(Object o, String s) {
        return List.of();
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        List<String> roles;
        if(Objects.equals(stringRedisTemplate.opsForList().range("role:" + o,0,-1).size(), 0)){
            roles = userRolesMapper
                    .selectUserRole(Integer.parseInt(o.toString()))
                    .stream().parallel().map(UserRoles -> UserRoles.getRoleId().toString()).toList();
            for (String role : roles) {
                stringRedisTemplate.opsForList().rightPush("role:" + o, role);
            }
        }
       else {
           roles = stringRedisTemplate.opsForList().range("role:" + o, 0, -1);
        }
        return roles;
    }
}
