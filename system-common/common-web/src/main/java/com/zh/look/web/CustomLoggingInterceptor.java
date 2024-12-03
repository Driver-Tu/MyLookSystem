package com.zh.look.web;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.BufferedReader;
import java.util.Map;

public class CustomLoggingInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(CustomLoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求URL
        String requestUrl = request.getRequestURI();
        // 获取用户ID
        String userId = StpUtil.getLoginIdDefaultNull() != null ? StpUtil.getLoginIdDefaultNull().toString() : "未登录";
        // 读取请求体
        String read = null;
        if (request.getReader()!=null) {
            BufferedReader reader = request.getReader();
            read = IoUtil.read(reader);
            log.info("Request URL: {}, User ID: {}, Request: {}", requestUrl, userId, read);
        }else {
            //获取请求参数
            Map<String, String[]> parameterMap = request.getParameterMap();
            if(MapUtil.isNotEmpty(parameterMap)){
                read = JSONUtil.toJsonStr(parameterMap);
                log.info("Request URL: {}, User ID: {}, Request: {}", requestUrl, userId, read);
            }else {
                read="无请求参数";
                log.info("Request URL: {}, User ID: {}, Request: {}", requestUrl, userId, read);
            }
        }
        return true;
    }
}
