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

        // 打印堆栈，以供调试
        nle.printStackTrace();

        // 判断场景值，定制化异常信息
        String message = "";
        if(nle.getType().equals(NotLoginException.NOT_TOKEN)) {
            message = "未提供token";
        }
        else if(nle.getType().equals(NotLoginException.INVALID_TOKEN)) {
            message = "token无效";
        }
        else if(nle.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            message = "token已过期";
        }
        else if(nle.getType().equals(NotLoginException.BE_REPLACED)) {
            message = "token已被顶下线";
        }
        else if(nle.getType().equals(NotLoginException.KICK_OUT)) {
            message = "token已被踢下线";
        }
        else {
            message = "当前会话未登录";
        }

        // 返回给前端
        return ResponseEntity.ok(message);
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
