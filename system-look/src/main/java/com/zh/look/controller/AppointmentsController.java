package com.zh.look.controller;

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
    @Autowired
    private AppointmentsService appointmentsService;

    /**
     * 换着获取个人预约信息
     */
    @PostMapping("/selectAppointments")
    @Operation(summary = "查询预约信息", responses = {@ApiResponse(responseCode = "200", description = "查询成功")})
    public Result<Page<Appointments>> selectAppointments(@RequestBody @Parameter(ref = "预约状态和预约时间") AppointmentsDto appointmentsDto){
        return  new Result<>(200, "查询成功", appointmentsService.selectAppointments(appointmentsDto));
    }
}
