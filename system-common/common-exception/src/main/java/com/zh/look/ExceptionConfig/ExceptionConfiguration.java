package com.zh.look.ExceptionConfig;


import cn.dev33.satoken.exception.NotLoginException;
import com.zh.look.resultTool.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ExceptionConfiguration {

    private HttpServletRequest request;
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<String> Exception(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("未知系统异常", e);
        return new Result<String>(400, e.getMessage(), null);
    }

    // 全局异常拦截（拦截项目中的NotLoginException异常）
    @ExceptionHandler(NotLoginException.class)
    public ResponseEntity handlerNotLoginException(NotLoginException nle, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // 判断场景值，定制化异常信息
        Result<String> result=new Result<>();
        if(nle.getType().equals(NotLoginException.NOT_TOKEN)) {
            result.setMsg("未提供token");
            result.setCode(500);
        }
        else if(nle.getType().equals(NotLoginException.INVALID_TOKEN)) {
            result.setMsg("token无效");
            result.setCode(501);
        }
        else if(nle.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            result.setMsg("token已过期");
            result.setCode(502);
        }
        else if(nle.getType().equals(NotLoginException.BE_REPLACED)) {
            result.setMsg("token已被顶下线");
            result.setCode(503);
        }
        else if(nle.getType().equals(NotLoginException.KICK_OUT)) {
            result.setMsg("token已被踢下线");
            result.setCode(504);
        }
        else {
            result.setMsg("当前会话未登录");
            result.setCode(505);
        }
        // 返回给前端
        return ResponseEntity.ok(result);
    }


    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public Result<String> NullPointerException(NullPointerException e,HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("发生空指针异常", e);
        return new Result<String>(300, e.getMessage(), null);
    }

    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public Result<String> MyException(MyException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("系统异常", e);
        return new Result<String>(e.getErrorCode(), e.getMessage(), null);
    }

}
