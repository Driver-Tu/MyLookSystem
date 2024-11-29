package com.zh.look.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.look.bean.Appointments;
import com.zh.look.bean.dto.AppointmentsDto;
import com.zh.look.resultTool.Result;
import com.zh.look.service.AppointmentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointmentsController")
@Tag(name = "预约服务")
public class AppointmentsController {

    private final AppointmentsService appointmentsService;

    public AppointmentsController(AppointmentsService appointmentsService) {
        this.appointmentsService = appointmentsService;
    }

    /**
     * 患者获取个人预约信息
     */
    @SaCheckRole("4")
    @PostMapping("/selectPatientAppointments")
    @Operation(summary = "患者查询预约信息", responses = {@ApiResponse(responseCode = "200", description = "查询成功")})
    public Result<Page<Appointments>> selectAppointments(@RequestBody @Parameter(ref = "预约状态和预约时间") AppointmentsDto appointmentsDto){
        appointmentsDto.setPatientId(StpUtil.getLoginIdAsInt());
        return  new Result<>(200, "查询成功", appointmentsService.selectAppointments(appointmentsDto));
    }


    /*
      医生获取自己的预约信息
     */
    @SaCheckRole("3")
    @PostMapping("/selectDoctorAppointments")
    @Operation(summary = "医生查询预约信息", responses = {@ApiResponse(responseCode = "200", description = "查询成功")})
    public Result<Page<Appointments>> selectDoctorAppointments(@RequestBody @Parameter(ref = "预约状态和预约时间") AppointmentsDto appointmentsDto){
        appointmentsDto.setDoctorId(StpUtil.getLoginIdAsInt());
        return  new Result<>(200, "查询成功", appointmentsService.selectAppointments(appointmentsDto));
    }
}
