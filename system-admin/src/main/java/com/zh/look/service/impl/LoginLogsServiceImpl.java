package com.zh.look.service.impl;

import com.zh.look.dao.LoginLogs;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.look.iputil.IpUtil;
import jakarta.servlet.http.HttpServletRequest;
import com.zh.look.mapper.LoginLogsMapper;
import org.springframework.stereotype.Service;
import com.zh.look.service.LoginLogsService;

/**
* @author admin
* @description 针对表【Login_Logs(登录日志表)】的数据库操作Service实现
* @createDate 2024-11-06 11:12:52
*/
@Service
public class LoginLogsServiceImpl extends ServiceImpl<LoginLogsMapper, LoginLogs>
    implements LoginLogsService{

    @Override
    public void insertLoginLogs(LoginLogs loginLogs, HttpServletRequest request) {
        IpUtil.getIpAddr(request);
    }
}




