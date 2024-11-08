package com.zh.look.satoken;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.zh.look.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StpInterfaceImpl implements StpInterface {
    @Autowired
    private UsersMapper userMapper;
    @Override
    public List<String> getPermissionList(Object o, String s) {
        return List.of();
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        List<String> roles;
        roles=userMapper.getUserRoles(StpUtil.getLoginIdAsInt()).stream().parallel().map(userRole->userRole.getRoleId().toString()).toList();
        return roles;
    }
}
