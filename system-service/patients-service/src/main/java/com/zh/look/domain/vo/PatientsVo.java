package com.zh.look.domain.vo;

import com.zh.look.bean.Patients;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientsVo extends Patients {
    private String sex;
    private String birthDate;
}
