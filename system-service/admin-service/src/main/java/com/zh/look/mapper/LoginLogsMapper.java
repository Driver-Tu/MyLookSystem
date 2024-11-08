package com.zh.look.mapper;

import com.zh.look.dao.LoginLogs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author admin
* @description 针对表【Login_Logs(登录日志表)】的数据库操作Mapper
* @createDate 2024-11-06 11:12:52
* @Entity com.zh.look.bean.dao.LoginLogs
*/
@Mapper
public interface LoginLogsMapper extends BaseMapper<LoginLogs> {

}




