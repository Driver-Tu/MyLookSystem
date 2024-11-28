package com.zh.look.controller;

import com.zh.look.bean.dao.DiseaseCategory;
import com.zh.look.resultTool.Result;
import com.zh.look.service.DiseaseCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/diseaseCategory")
@Tag(name = "疾病控制器")
public class DiseaseCategoryController {
    private final DiseaseCategoryService diseaseCategoryService;

    public DiseaseCategoryController(DiseaseCategoryService diseaseCategoryService) {
        this.diseaseCategoryService = diseaseCategoryService;
    }

    @PostMapping("/getAllDiseaseCategory")
    @Operation(summary = "查询所有疾病类别", responses = {@ApiResponse(responseCode = "200", description = "查询成功")})
    public Result<List<DiseaseCategory>> getAllDiseaseCategory(){
        return new Result<>(200, "查询成功", diseaseCategoryService.selectAllDiseaseCategory());
    }
}
