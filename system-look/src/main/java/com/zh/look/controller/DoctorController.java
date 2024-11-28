package com.zh.look.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.look.bean.Doctors;
import com.zh.look.bean.Reviews;
import com.zh.look.domain.dto.DoctorDto;
import com.zh.look.resultTool.Result;
import com.zh.look.service.DoctorsService;
import com.zh.look.service.LikesService;
import com.zh.look.service.ReviewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/doctor")
@Tag(name = "医生信息控制器")
@Slf4j
public class DoctorController {
    /**
     * 医生模块控制器
     */
    @Autowired
    private DoctorsService doctorsService;
    @Autowired
    private LikesService likeService;
    @Autowired
    private ReviewsService reviewsService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 只要是用户就可以查询医生信息
     * @param doctorDto
     * @return
     */
    @SaCheckRole("2")
    @PostMapping("/getDoctor")
    @Operation(summary = "获取医生信息", responses = {@ApiResponse(responseCode = "200", description = "查询成功")})
    public Result<Page<Doctors>> getDoctor(@RequestBody @Parameter(name = "doctorDto", description = "医生信息") DoctorDto doctorDto){
        //打印详细操作记录
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
            @ApiResponse(responseCode = "602", description = "无法修改")})
    public Result<Boolean> updateDoctor(@RequestBody @Parameter(name = "doctors", description = "医生信息") Doctors doctors){
        return new Result<>(200, "更新成功", doctorsService.updateDoctor(doctors));
    }

    /**
     * 为确保不正规医生添加信息，只有超级管理员才能添加医生信息
     * @param doctors
     * @return
     */
    @SaCheckRole("1")
    @PostMapping
    @Operation(summary = "添加医生信息", responses = {@ApiResponse(responseCode = "200", description = "添加成功"),
            @ApiResponse(responseCode = "603", description = "添加失败")})
    public Result<Boolean> addDoctor(@RequestBody @Parameter(name = "doctors", description = "医生信息") Doctors doctors){
        return new Result<>(200, "添加成功", doctorsService.addDoctor(doctors));
    }

    /**
     * 为确保医生删除信息，只有辞职之后，超级管理员才能删除医生信息
     */
    @SaCheckRole("1")
    @DeleteMapping("/deleteDoctor")
    @Operation(summary = "删除医生信息", responses = {@ApiResponse(responseCode = "200", description = "删除成功"),
            @ApiResponse(responseCode = "604", description = "删除失败")})
    public Result<Boolean> deleteDoctor(@RequestParam("doctorId") @Parameter(name = "doctorId", description = "医生id") Integer doctorId){
        return new Result<>(200, "删除成功", doctorsService.deleteDoctor(doctorId));
    }

    /**
     * 统计医生的电赞数量
     */
    @SaCheckRole("2")
    @GetMapping("/countDoctorLike")
    @Operation(summary = "统计医生的电赞数量和评论数量", responses = {@ApiResponse(responseCode = "200", description = "统计成功")})
    public Result<Object> countDoctorLike(@RequestParam("doctorId") @Parameter(name = "doctorId", description = "医生id") Integer doctorId){
        return new Result<>(200, "统计成功", likeService.countDoctorLike(doctorId));
    }

    /**
     * 获取医生的评论和评分
     */
    @SaCheckRole("2")
    @PostMapping("/getDoctorReviews")
    @Operation(summary = "获取医生的评论和评分", responses = {@ApiResponse(responseCode = "200", description = "获取成功")})
    public Result<Object> getDoctorReviews(@RequestBody @Parameter(name = "doctorDto", description = "医生信息") DoctorDto doctorDto){
        return new Result<>(200, "获取成功", reviewsService.getDoctorReviews(doctorDto));
    }


    @SaCheckRole("2")
    @PostMapping("/getDoctorRate")
    @Operation(summary = "获取医生详细评分", responses = {@ApiResponse(responseCode = "200", description = "获取成功")})
    public Result<Object> getDoctorInfo(@RequestBody @Parameter(name = "doctorDto", description = "医生信息") DoctorDto doctorDto){
        if (stringRedisTemplate.opsForValue().get("doctorRate:"+doctorDto.getDoctorId())!=null){
            return new Result<>(200, "获取成功", stringRedisTemplate.opsForValue().get("doctorRate:"+doctorDto.getDoctorId()));
        }
        List<Reviews> doctorReviews = reviewsService.getDoctorReviews(doctorDto);
        AtomicReference<Long> count = new AtomicReference<>(0L);
        BigDecimal totalRating =BigDecimal.ZERO;
        for (Reviews reviews : doctorReviews) {
            count.getAndSet(count.get() + 1);
            totalRating = totalRating.add(BigDecimal.valueOf(reviews.getRating()));
        }
        BigDecimal averageRating = count.get() > 0 ? totalRating.divide(BigDecimal.valueOf(count.get()), 2, RoundingMode.HALF_UP) : BigDecimal.valueOf(5);
        //redis
        stringRedisTemplate.opsForValue().set("doctorRate:"+doctorDto.getDoctorId(), averageRating.toString());
        return new Result<>(200, "获取成功", averageRating);
    }
}
