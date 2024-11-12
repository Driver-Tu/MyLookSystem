package com.zh.look.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.look.bean.Doctors;
import com.zh.look.domain.dto.DoctorDto;
import com.zh.look.resultTool.Result;
import com.zh.look.service.DoctorsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctor")
@Tag(name = "医生信息模块")
public class DoctorController {
    /**
     * 医生模块控制器
     */
    @Autowired
    private DoctorsService doctorsService;

    /**
     * 只要是用户就可以查询医生信息
     * @param doctorDto
     * @return
     */
    @SaCheckRole("2")
    @PostMapping("/getDoctor")
    @Operation(summary = "获取医生信息", responses = {@ApiResponse(responseCode = "200", description = "查询成功")})
    public Result<Page<Doctors>> getDoctor(@RequestBody @Parameter(name = "doctorDto", description = "医生信息") DoctorDto doctorDto){
        Page<Doctors> doctorsPage = doctorsService.selectDoctors(doctorDto);
        return new Result<>(200, "查询成功", doctorsPage);
    }

    /**
     * 必须得是医生才可以修改医生自己的信息
     * @param doctors
     * @return
     */
    @SaCheckRole("3")
    @PostMapping("/updateDoctor")
    @Operation(summary = "更新医生信息", responses = {@ApiResponse(responseCode = "200", description = "更新成功"),
            @ApiResponse(responseCode = "520", description = "无法修改")})
    public Result<Boolean> updateDoctor(@RequestBody @Parameter(name = "doctors", description = "医生信息") Doctors doctors){
        return new Result<>(200, "更新成功", doctorsService.updateDoctor(doctors));
    }
}
