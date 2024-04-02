package com.simple.operator.db.mysql;

import cn.hutool.core.map.MapUtil;
import com.simple.bean.DataSourceConfig;
import com.simple.operator.FieldSelector;
import com.simple.operator.TableSelector;
import com.simple.operator.bean.TableInfo;

import java.util.Map;

/**
 * mysql 表信息查询器
 *
 * @author Simple
 */
public class MySqlTableSelector extends TableSelector {

    private static final String SHOW_TABLE_INFO = "SHOW TABLE STATUS FROM %s";

    public MySqlTableSelector(DataSourceConfig dataSourceConfig, FieldSelector fieldSelector) {
        super(dataSourceConfig, fieldSelector);
    }

    /**
     * 不同数据库获取表信息的sql语句不同，所以定义为抽象类
     *
     * @return SQL语句
     */
    @Override
    public String getShowTablesSql() {
        // todo 数据库名称包含特殊字符问题
        String dbName = dataSourceConfig.getDbName();
        return String.format(SHOW_TABLE_INFO, dbName);
    }

    /**
     * 不同数据库中获取的表信息字段不同，所以也将该方法抽象出来
     *
     * @param tableMap 数据库中查询出的表信息Map
     */
    @Override
    public TableInfo buildTableInfo(Map<String, Object> tableMap) {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableName(MapUtil.getStr(tableMap, "name"));
        tableInfo.setComment(MapUtil.getStr(tableMap, "comment"));
        return tableInfo;
    }
}
