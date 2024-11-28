package com.zh.look.bean.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 疾病子分类表（所有者：系统管理员）
 * @TableName SubCategory
 */
@TableName(value ="SubCategory")
@Data
public class Subcategory implements Serializable {
    /**
     * 子分类ID（所有者：系统管理员）
     */
    @TableId
    private Integer subcategoryId;

    /**
     * 所属分类ID（所有者：系统管理员）
     */
    private Integer categoryId;

    /**
     * 子分类名称（所有者：系统管理员）
     */
    private String subcategoryName;

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
        Subcategory other = (Subcategory) that;
        return (this.getSubcategoryId() == null ? other.getSubcategoryId() == null : this.getSubcategoryId().equals(other.getSubcategoryId()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getSubcategoryName() == null ? other.getSubcategoryName() == null : this.getSubcategoryName().equals(other.getSubcategoryName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSubcategoryId() == null) ? 0 : getSubcategoryId().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getSubcategoryName() == null) ? 0 : getSubcategoryName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", subcategoryId=").append(subcategoryId);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", subcategoryName=").append(subcategoryName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}