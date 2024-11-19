package com.zh.look.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientsDto {
    private Integer pageNum;
    private Integer pageSize;
    private String patientId;
    private String patientName;
    private String patientSex;
    private String patientAge;
    private String phone;
}
