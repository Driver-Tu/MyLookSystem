package com.zh.look.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.zh.look.domain.dto.UserLoginDto;
import com.zh.look.domain.vo.UserVo;
import com.zh.look.resultTool.Result;
import com.zh.look.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/login")
@Tag(name = "用户登录控制器")
@RequiredArgsConstructor
public class LoginController {
    /**
     * 登录模块控制器
     */
    private final UsersService usersService;

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
            return new Result<>(507, "该设备未登录",null);
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

    /**
     * 获取权限role
     */
    @GetMapping("/getRole")
    @Operation(summary = "获取权限role", responses = {@ApiResponse(responseCode = "200", description = "获取成功"),
            @ApiResponse(responseCode = "509", description = "获取失败")})
    public Result<List<String>> getRole() {
        return new Result<>(200, "获取成功", StpUtil.getRoleList());
    }
}
