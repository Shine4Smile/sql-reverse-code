package com.simple.bean;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import javax.persistence.*;


/**
 * 数据源配置信息
 */
@Data
public class DataSourceConfig {
    /**
     * 主键id
     */
    @Id
    private Long id;
    /**
     * 数据库类型
     */
    private Integer dbType;
    /**
     * 数据库名称
     */
    private String dbName;
    /**
     * 模式名称
     */
    private String schemaName;
    /**
     * 数据库ip
     */
    private String host;
    /**
     * 数据库端口号
     */
    private String port;
    /**
     * 登录用户名
     */
    private String userName;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 分组名称
     */
    private String groupName;
    /**
     * 数据库描述
     */
    private String desc;
    /**
     * 删除状态
     * 使用@TableLogic注解，yml文件中需要增加对应配置
     */
    @TableLogic
    private Integer isDelete;
}
