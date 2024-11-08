package com.zh.look.service;

import com.zh.look.dao.LoginLogs;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author admin
* @description 针对表【Login_Logs(登录日志表)】的数据库操作Service
* @createDate 2024-11-06 11:12:52
*/
public interface LoginLogsService extends IService<LoginLogs> {
    /**
     * 登录的时候，插入记录
     */
     void insertLoginLogs(LoginLogs loginLogs, HttpServletRequest request);
}
