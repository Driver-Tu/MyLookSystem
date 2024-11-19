package com.zh.look.controller;


import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.look.bean.Patients;
import com.zh.look.domain.dto.PatientsDto;
import com.zh.look.resultTool.Result;
import com.zh.look.service.PatientsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 患者控制器
 */
@RestController
@RequestMapping("/patients")
@Tag(name = "患者信息控制器")
public class PatientsController {

    @Autowired
    private PatientsService patientsService;

    /**
     * 查询患者信息
     */
    @SaCheckRole("2")
    @PostMapping("getPatients")
    @Operation(summary = "查询信息", responses = {@ApiResponse(responseCode = "200", description = "查询成功")})
    public Result<Page<Patients>> selectPatients(@RequestBody PatientsDto patientsDto){
        return new Result<>(200, "查询成功", patientsService.selectPatients(patientsDto));
    }

    @SaCheckRole("4")
    @PostMapping("/addPatients")
    @Operation(summary = "添加患者信息", responses = {@ApiResponse(responseCode = "200", description = "添加成功"),
            @ApiResponse(responseCode = "601", description = "请修改个人信息，不要篡改他人信息"),
            @ApiResponse(responseCode = "603", description = "添加失败")})
    public Result<Boolean> addPatients(@RequestBody Patients patients){
        return new Result<>(200, "添加成功", patientsService.addPatients(patients));
    }
    @SaCheckRole("4")
    @PostMapping("/updatePatients")
    @Operation(summary = "更新患者信息", responses = {@ApiResponse(responseCode = "200", description = "更新成功"),
            @ApiResponse(responseCode = "602", description = "无法修改")})
    public Result<Boolean> updatePatients(@RequestBody Patients patients){
        return new Result<>(200, "更新成功", patientsService.updatePatients(patients));
    }

    /**
     * 患者表述病，推荐医生
     */
    @SaCheckRole("4")
    @PostMapping("/getADoctor")
    @Operation(summary = "添加患者表述病，推荐医生", responses = {@ApiResponse(responseCode = "200", description = "添加成功"),
            @ApiResponse(responseCode = "603", description = "添加失败")})
    public Result<Boolean> addReviews(@RequestBody Patients patients){
        return new Result<>(200, "添加成功", patientsService.getADoctor(patients));
    }
}
