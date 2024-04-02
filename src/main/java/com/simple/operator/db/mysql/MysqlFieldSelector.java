package com.simple.operator.db.mysql;

import cn.hutool.core.util.StrUtil;
import com.simple.bean.DataSourceConfig;
import com.simple.operator.FieldSelector;
import com.simple.operator.bean.FieldInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import java.util.ArrayList;
import java.util.List;

/**
 * mysql 字段信息查询器
 *
 * @author Simple
 */
public class MysqlFieldSelector extends FieldSelector {

    /**
     * 查询表字段信息SQL
     * 这里定义两个SQL来获取字段信息（包括：字段名、模式名、是否主键、是否为空、是否自增、字段类型、对应java类型、长度等等）
     * <p>
     * 定义两个SQL的原因是使用单条SQL的方式无法获取全所需信息，
     * 比如：单用SQL1可能无法获取到对应的java类型（主要是不想在java代码层面来处理这个步骤，不同数据库类型可能都需要编写一个转换器）
     * 单用SQL2可能无法获取到字段注释信息等
     * 所以这里就先采用两个SQL互补的方式完成。后续若有更好的解决方式再优化
     */
    private static final String FIELD_INFO_SQL_ONE = " SELECT COLUMN_NAME,EXTRA " +
            " COLUMN_KEY,COLUMN_COMMENT,DATA_TYPE,NUMERIC_SCALE,IS_NULLABLE " +
            " FROM information_schema.`COLUMNS` " +
            " WHERE 1=1 AND TABLE_SCHEMA = '%s' AND TABLE_NAME = '%s' " +
            " ORDER BY ORDINAL_POSITION";

    private static final String FIELD_INFO_SQL_TWO = " SELECT *  FROM %s LIMIT 1";

    public MysqlFieldSelector(DataSourceConfig config) {
        super(config);
    }

    /**
     * 获取表字段相关信息
     *
     * @param jdbcTemplate 数据库模板
     * @param tableName    表名
     */
    @Override
    public List<FieldInfo> buildFieldInfo(JdbcTemplate jdbcTemplate, String tableName) {
        String formatSqlOne = String.format(FIELD_INFO_SQL_ONE, config.getDbName(), tableName);
        String formatSqlTwo = String.format(FIELD_INFO_SQL_TWO, tableName);
        // 执行上述两条SQL，获取对应的SqlRowSet
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(formatSqlOne);
        SqlRowSet sqlRowSetTwo = jdbcTemplate.queryForRowSet(formatSqlTwo);
        SqlRowSetMetaData metaData = sqlRowSetTwo.getMetaData();
        List<FieldInfo> fieldInfoList = new ArrayList<>();
        while (sqlRowSet.next()) {
            FieldInfo fieldInfo = new FieldInfo();
            String columnName = sqlRowSet.getString("COLUMN_NAME");
            boolean isAutoIncrement = sqlRowSet.getString("EXTRA").contains("auto_increment");
            boolean isPrimaryKey = sqlRowSet.getString("COLUMN_KEY").equalsIgnoreCase("PRI");
            String columnComment = sqlRowSet.getString("COLUMN_COMMENT");
            String columnTypeName = sqlRowSet.getString("DATA_TYPE");
            int scale = sqlRowSet.getInt("NUMERIC_SCALE");
            boolean isNullable = sqlRowSet.getString("IS_NULLABLE").equalsIgnoreCase("YES");
            String columnClassName = metaData.getColumnClassName(sqlRowSet.getRow());
            // 将java.lang.String形式改为String
            columnClassName = StrUtil.subAfter(columnClassName, ".", true);
            int columnDisplaySize = metaData.getColumnDisplaySize(sqlRowSet.getRow());
            fieldInfo.setFieldName(columnName);
            fieldInfo.setIsIncrement(isAutoIncrement);
            fieldInfo.setIsPk(isPrimaryKey);
            fieldInfo.setComment(columnComment);
            fieldInfo.setColumnType(columnTypeName);
            // 数据库中字段类型无法与Java类型对应的默认采用String类型处理
            fieldInfo.setColumnJavaType(StrUtil.isBlank(columnClassName) ? "String" : columnClassName);
            fieldInfo.setMaxLength(columnDisplaySize);
            fieldInfo.setIsNullable(isNullable);
            fieldInfo.setScale(scale);
            fieldInfoList.add(fieldInfo);
        }
        return fieldInfoList;
    }
}
