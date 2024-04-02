package com.simple.operator;

import com.simple.bean.DataSourceConfig;
import com.simple.operator.bean.FieldInfo;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * 表字段信息查询
 *
 * @author Simple
 */
public abstract class FieldSelector {

    protected DataSourceConfig config;


    public FieldSelector(DataSourceConfig config) {
        this.config = config;
    }

    /**
     * 将从数据库中获取到的字段信息使用实体类接收
     */
    public abstract List<FieldInfo> buildFieldInfo(JdbcTemplate jdbcTemplate, String tableName);


}
