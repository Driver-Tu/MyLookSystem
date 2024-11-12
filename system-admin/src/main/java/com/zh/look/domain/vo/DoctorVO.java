package com.zh.look.domain.vo;

import com.zh.look.bean.Doctors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorVO extends Doctors {
    private Integer countLike;

    /**
     * 实现父类的构造方法
     */
    public DoctorVO(Doctors doctors) {
        super(doctors);
    }
}
