package com.zh.look.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * 修改用户密码
     */
    @RequestMapping("/updatePassword")
    public String updatePassword(){
        return "修改密码";
    }
}
