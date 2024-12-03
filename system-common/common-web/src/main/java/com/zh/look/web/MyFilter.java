package com.zh.look.web;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.io.IOException;

@Slf4j
@Configuration
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("MyFilter来啦！！！！");
    }

    @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            long l = System.currentTimeMillis();
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Headers", "satoken, Content-Type, Authorization");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,HEAD,PUT");
            ServletRequest requestWrapper = null;
            if (StringUtils.startsWithIgnoreCase(request.getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
                requestWrapper = new MyRequestWrapper(request,response);
            }
            //让预制请求通过
            if ("OPTIONS".equals(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK); // 返回200
                return;
            }
            if (null == requestWrapper) {
                log.info("请求体为空");
                /**
                 * 执行请求，拦截器生效
                 */
                filterChain.doFilter(servletRequest, servletResponse);
            }else {
                filterChain.doFilter(requestWrapper, servletResponse);
            }
        log.info("请求结束时间:{}ms",System.currentTimeMillis()-l);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
