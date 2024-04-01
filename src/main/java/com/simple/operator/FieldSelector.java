package com.simple.operator;

import com.simple.bean.DataSourceConfig;
import com.simple.operator.bean.FieldInfo;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 表字段信息查询
 *
 * @author Simple
 */
public abstract class FieldSelector {

    private DataSourceConfig dataSourceConfig;

    public FieldSelector(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    /**
     * 获取表字段信息查询sql，同样的因为不同数据库使用sql不同所以将该方法抽象
     *
     * @param tableName        查询表名称
     * @param dataSourceConfig 数据源配置
     * @return SQL语句
     */
    public abstract String getFieldInfoSql(String tableName, DataSourceConfig dataSourceConfig);

    /**
     * 将从数据库中获取到的字段信息使用实体类接收
     *
     * @param fieldMap 字段信息map
     */
    public abstract FieldInfo buildFieldInfo(Map<String, Object> fieldMap);

    public List<FieldInfo> getFieldInfo(JdbcTemplate jdbcTemplate, String tableName) {
        String fieldInfoSql = getFieldInfoSql(tableName, dataSourceConfig);
        List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(fieldInfoSql);
        List<FieldInfo> fieldInfoList = new ArrayList<>();
        for (Map<String, Object> fieldMap : queryForList) {
            FieldInfo fieldInfo = buildFieldInfo(fieldMap);
            fieldInfoList.add(fieldInfo);
        }
        return fieldInfoList;
    }

}
