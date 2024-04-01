package com.simple.operator.db.mysql;

import cn.hutool.core.map.MapUtil;
import com.simple.bean.DataSourceConfig;
import com.simple.operator.FieldSelector;
import com.simple.operator.bean.FieldInfo;

import java.util.Map;

/**
 * mysql 字段信息查询器
 *
 * @author Simple
 */
public class MysqlFieldSelector extends FieldSelector {

    /**
     * 查询表字段信息SQL
     * todo MaxLength最大长度获取类型暂时不全
     */
    private static final String SHOW_FIELD_SQL = " SELECT " +
            " COLUMN_NAME AS 'field', " +
            " COLUMN_DEFAULT AS 'default', " +
            " IS_NULLABLE AS 'isNullAble', " +
            " DATA_TYPE AS 'dataType', " +
            " CASE DATA_TYPE " +
            "     WHEN 'int' THEN NUMERIC_PRECISION " +
            "     WHEN 'varchar' THEN CHARACTER_MAXIMUM_LENGTH " +
            " END AS 'maxLength', " +
            " IFNULL(NUMERIC_SCALE,0) AS 'scale', " +
            " COLUMN_TYPE AS 'type', " +
            " COLUMN_KEY AS 'key', " +
            " EXTRA AS 'extra', " +
            " COLUMN_COMMENT AS 'comment' " +
            " FROM information_schema.`COLUMNS` " +
            " WHERE 1=1 AND TABLE_SCHEMA = '%s' AND TABLE_NAME = '%s' " +
            " ORDER BY ORDINAL_POSITION";

    public MysqlFieldSelector(DataSourceConfig dataSourceConfig) {
        super(dataSourceConfig);
    }


    /**
     * 获取表字段信息查询sql，同样的因为不同数据库使用sql不同所以将该方法抽象
     *
     * @param tableName 查询表名称
     * @return SQL语句
     */
    @Override
    public String getFieldInfoSql(String tableName, DataSourceConfig dataSourceConfig) {
        return String.format(SHOW_FIELD_SQL, dataSourceConfig.getDbName(), tableName);
    }

    /**
     * 将从数据库中获取到的字段信息使用实体类接收
     *
     * @param fieldMap 字段信息map
     */
    @Override
    public FieldInfo buildFieldInfo(Map<String, Object> fieldMap) {
        FieldInfo fieldInfo = new FieldInfo();
        fieldInfo.setFieldName(MapUtil.getStr(fieldMap, "field"));
        fieldInfo.setType(MapUtil.getStr(fieldMap, "type"));
        fieldInfo.setIsIncrement(MapUtil.getStr(fieldMap, "extra").equalsIgnoreCase("auto_increment"));
        fieldInfo.setIsPk(MapUtil.getStr(fieldMap, "key").equalsIgnoreCase("PRI"));
        fieldInfo.setComment(MapUtil.getStr(fieldMap, "comment"));
        fieldInfo.setMaxLength(MapUtil.getInt(fieldMap, "maxLength", 0));
        fieldInfo.setScale(MapUtil.getInt(fieldMap, "scale", 0));
        fieldInfo.setIsNullable(MapUtil.getStr(fieldMap, "isNullable").equalsIgnoreCase("yes"));
        return fieldInfo;
    }
}
