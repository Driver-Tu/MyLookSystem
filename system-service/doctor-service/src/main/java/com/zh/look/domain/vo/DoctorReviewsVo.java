package com.zh.look.domain.vo;

import com.zh.look.bean.Doctors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorReviewsVo {
    private Doctors doctors;
    /**
     * 评论id记录
     */
    private List<Integer> reviewIds;
    /**
     * 点赞id记录
     */
    private List<Integer> likeIds;

}
