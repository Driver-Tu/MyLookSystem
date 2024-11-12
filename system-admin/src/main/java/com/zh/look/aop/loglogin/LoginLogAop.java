package com.zh.look.aop.loglogin;

import com.zh.look.dao.LoginLogs;
import com.zh.look.domain.vo.UserVo;
import com.zh.look.iputil.IpUtil;
import com.zh.look.resultTool.Result;
import com.zh.look.service.LoginLogsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Aspect
@Component
@Slf4j
public class LoginLogAop {
    @Autowired
    private LoginLogsService loginLogsService;

    @AfterReturning(pointcut = "execution(* com.zh.look.controller.LoginController.login(..))", returning = "result")
    public void logLogin(JoinPoint joinPoint,Object result) {
        // 获取请求参数
        Object[] args = joinPoint.getArgs();
        log.info("用户名：{}", args[0]);
        //拿返回值查询用户id
        Result<UserVo> userVo = (Result<UserVo>) result;
        // 获取请求的IP地址
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String ipAddr = IpUtil.getIpAddr(request);
        System.out.println("IP地址为："+ipAddr);
        // 创建登录记录
        LoginLogs loginLogs = new LoginLogs();
        loginLogs.setUserId(userVo.getData().getUserId());
        loginLogs.setLoginTime(new java.util.Date());
        loginLogs.setIpAddress(ipAddr);
        loginLogs.setStatus("成功登录");
        // 保存登录记录到数据库
        loginLogsService.save(loginLogs);
    }

    @AfterThrowing(pointcut = "execution(* com.zh.look.controller.LoginController.login(..))", throwing = "e")
    public void logFailedLogin(JoinPoint joinPoint,Throwable e) {
        log.error("登录时异常", e);
        // 获取请求参数
        Object[] args = joinPoint.getArgs();
        log.info("用户名：{}", args);
        // 获取请求的IP地址
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String ipAddr = IpUtil.getIpAddr(request);
        System.out.println("IP地址为：" + ipAddr);
        // 创建登录记录
        LoginLogs loginLogs = new LoginLogs();
        //失败无法获取用户id，则记录账户名
        loginLogs.setLoginTime(new java.util.Date());
        loginLogs.setIpAddress(ipAddr);
        loginLogs.setStatus("登录失败");
        // 保存登录记录到数据库
        loginLogsService.save(loginLogs);
    }
}
