package com.zh.look.bean.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentsDto {
    private Integer doctorId;
    private Integer patientId;
    private String status;
    private LocalDateTime appointmentDate;
}
