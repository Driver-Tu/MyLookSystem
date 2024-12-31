package com.zh.look.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.lang.UUID;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/code")
@Slf4j
public class CodeController {
    private final StringRedisTemplate stringRedisTemplate;

    @GetMapping("getCode")
    public String getCode(HttpServletResponse response){
        //生成随机验证码
        RandomGenerator randomGenerator=new RandomGenerator("1234567890",4);
        CircleCaptcha lineCaptcha = CaptchaUtil.createCircleCaptcha(100, 30);
        response.setContentType("image/jpeg");
        response.setHeader("Cache-Control", "no-cache");
        try {
            lineCaptcha.setGenerator(randomGenerator);
            //输出到页面
            lineCaptcha.write(response.getOutputStream());
            UUID uuid = UUID.randomUUID();
            //将验证码存入redis60s
            stringRedisTemplate.opsForValue().set(uuid.toString(),lineCaptcha.getCode(),60, TimeUnit.SECONDS);
            //打印日志
            log.info("验证码为：{}",lineCaptcha.getCode());
            response.getOutputStream().close();
            return uuid.toString();
        } catch (IOException e) {
            log.error("验证码生成失败",e);
            return "验证码生成失败";
        }
    }
}
