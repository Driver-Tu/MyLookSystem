package com.zh.look.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {
    private String name;
    private String specialty;
    private String hospital;
    private String bio;
    private Integer pageNum;
    private Integer pageSize;
}
