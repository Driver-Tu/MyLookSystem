package com.zh.look.bean.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 具体疾病表（所有者：系统管理员）
 * @TableName Disease
 */
@TableName(value ="Disease")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Disease implements Serializable {
    /**
     * 疾病ID（所有者：系统管理员）
     */
    @TableId
    private Integer diseaseId;

    /**
     * 所属子分类ID（所有者：系统管理员）
     */
    private Integer subcategoryId;

    /**
     * 疾病名称（所有者：系统管理员）
     */
    private String diseaseName;

    /**
     * 疾病描述（所有者：系统管理员）
     */
    private String description;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Disease other = (Disease) that;
        return (this.getDiseaseId() == null ? other.getDiseaseId() == null : this.getDiseaseId().equals(other.getDiseaseId()))
            && (this.getSubcategoryId() == null ? other.getSubcategoryId() == null : this.getSubcategoryId().equals(other.getSubcategoryId()))
            && (this.getDiseaseName() == null ? other.getDiseaseName() == null : this.getDiseaseName().equals(other.getDiseaseName()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDiseaseId() == null) ? 0 : getDiseaseId().hashCode());
        result = prime * result + ((getSubcategoryId() == null) ? 0 : getSubcategoryId().hashCode());
        result = prime * result + ((getDiseaseName() == null) ? 0 : getDiseaseName().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", diseaseId=").append(diseaseId);
        sb.append(", subcategoryId=").append(subcategoryId);
        sb.append(", diseaseName=").append(diseaseName);
        sb.append(", description=").append(description);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}