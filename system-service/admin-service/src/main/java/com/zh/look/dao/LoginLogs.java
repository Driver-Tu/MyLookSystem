package com.zh.look.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录日志表
 * @TableName Login_Logs
 */
@TableName(value ="Login_Logs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginLogs implements Serializable {
    /**
     * 日志ID
     */
    @TableId(type = IdType.AUTO)
    private Integer logId;

    /**
     * 用户ID，外键，引用用户表的用户ID
     */
    private Integer userId;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 登录IP地址
     */
    private String ipAddress;

    /**
     * 登录状态（成功/失败）
     */
    private String status;

    /**
     * 登录设备
     */
    private String device;

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
        LoginLogs other = (LoginLogs) that;
        return (this.getLogId() == null ? other.getLogId() == null : this.getLogId().equals(other.getLogId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getLoginTime() == null ? other.getLoginTime() == null : this.getLoginTime().equals(other.getLoginTime()))
            && (this.getIpAddress() == null ? other.getIpAddress() == null : this.getIpAddress().equals(other.getIpAddress()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getDevice() == null ? other.getDevice() == null : this.getDevice().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLogId() == null) ? 0 : getLogId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getLoginTime() == null) ? 0 : getLoginTime().hashCode());
        result = prime * result + ((getIpAddress() == null) ? 0 : getIpAddress().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getDevice() == null) ? 0 : getDevice().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", logId=").append(logId);
        sb.append(", userId=").append(userId);
        sb.append(", loginTime=").append(loginTime);
        sb.append(", ipAddress=").append(ipAddress);
        sb.append(", status=").append(status);
        sb.append(", device=").append(device);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}