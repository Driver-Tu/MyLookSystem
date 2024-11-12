package com.zh.look.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.zh.look.ExceptionConfig.MyException;
import com.zh.look.domain.vo.UserVo;
import com.zh.look.domain.dto.UserLoginDto;
import com.zh.look.resultTool.Result;
import com.zh.look.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@Tag(name = "用户登录模块")
public class LoginController {
    /**
     * 登录模块控制器
     */
    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    @Operation(summary = "登录",
            responses = {@ApiResponse(responseCode = "200", description = "登录成功"),
                    @ApiResponse(responseCode = "506", description = "账户已登录，请先退出登录"),
                    @ApiResponse(responseCode = "510", description = "没有该用户")})
    public Result<UserVo> login(@RequestBody @Parameter(name = "userLoginDto", description = "用户登录信息") UserLoginDto userLoginDto) {
        UserVo userVo = usersService.Login(userLoginDto.getUserName(), userLoginDto.getPassword(), userLoginDto.getDevice());
        return new Result<>(200, "登录成功", userVo);
    }

    @GetMapping("/loginout")
    @Operation(summary = "退出登录", responses = {@ApiResponse(responseCode = "200", description = "退出成功"),
            @ApiResponse(responseCode = "507", description = "该设备未登录")})
    public Result<Boolean> loginOut(@RequestParam @Parameter(name = "device", description = "设备标识，用于区分设备") String device) {
        if (StpUtil.isLogin(StpUtil.getLoginId()) && StpUtil.getSessionByLoginId(StpUtil.getLoginId()).getTokenValueListByDevice(device).isEmpty()) {
            throw new MyException(507, "该设备未登录");
        }
        StpUtil.logout(StpUtil.getLoginId(), device);
        return new Result<>(200, "退出成功", true);
    }

    @GetMapping("/isLogin")
    @Operation(summary = "判断是否登录", responses = {@ApiResponse(responseCode = "200", description = "已登录"),
            @ApiResponse(responseCode = "508", description = "未登录")})
    public Result<Boolean> isLogin() {
        if (StpUtil.isLogin()) {
            return new Result<>(200, "已登录", true);
        }
        return new Result<>(508, "未登录", false);
    }
}
