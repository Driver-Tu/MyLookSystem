package com.zh.look.listener;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;
import com.zh.look.domain.dao.LoginLogs;
import com.zh.look.iputil.IpUtil;
import com.zh.look.service.LoginLogsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;


@Component
@Slf4j
@RequiredArgsConstructor
/**
 * 监听器,监听用户对账户的登录状态
 */
public class UserActionListener implements SaTokenListener {
    private final LoginLogsService loginLogsService;
    /**
     * 每次登录时触发
     * @param s 登录类型
     * @param o 登录id
     * @param s1 登录token值
     * @param saLoginModel
     */
    @Override
    public void doLogin(String s, Object o, String s1, SaLoginModel saLoginModel) {
        LoginLogs loginLogs=new LoginLogs();
        loginLogs.setUserId(Integer.parseInt(o.toString()));
        loginLogs.setLoginTime(new java.util.Date());
        //获取登录ip
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        loginLogs.setIpAddress(IpUtil.getIpAddr(request));
        loginLogs.setStatus("登录成功");
        //获取登录设备
        loginLogs.setDevice(saLoginModel.getDeviceOrDefault());
        loginLogsService.save(loginLogs);
        log.info("用户登录成功，userId:{}，userToken:{}", o,s1);
    }

    @Override
    public void doLogout(String s, Object o, String s1) {
        //打印退出人id和token
        log.info("用户退出登录,userId：{}，userTokenValue：{}", o,s1);
    }

    @Override
    public void doKickout(String s, Object o, String s1) {
        log.info("用户{}被踢下线", o);
    }

    @Override
    public void doReplaced(String s, Object o, String s1) {
        log.info("用户{}被顶下线", o);
    }

    @Override
    public void doDisable(String s, Object o, String s1, int i, long l) {
        log.info("用户{}被封禁", o);
    }

    @Override
    public void doUntieDisable(String s, Object o, String s1) {
        log.info("用户{}被解封", o);
    }

    @Override
    public void doOpenSafe(String s, String s1, String s2, long l) {
        log.info("用户{}开启安全模式", s);
    }

    @Override
    public void doCloseSafe(String s, String s1, String s2) {
        log.info("用户{}关闭安全模式", s);
    }

    @Override
    public void doCreateSession(String s) {
        log.info("用户{}创建Session", s);
    }

    @Override
    public void doLogoutSession(String s) {
        log.info("用户{}Session过期", s);
    }

    @Override
    public void doRenewTimeout(String s, Object o, long l) {
        log.info("用户{}Session刷新", o);
    }
}
