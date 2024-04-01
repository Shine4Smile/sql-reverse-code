package com.simple.operator.db.mysql;

import com.simple.bean.DataSourceConfig;
import com.simple.operator.SqlService;
import com.simple.operator.TableSelector;

/**
 * mysql service，初始化对应表信息查询器及表字段信息查询器
 *
 * @author Simple
 */
public class MySqlService extends SqlService {
    @Override
    public TableSelector getTableSelector(DataSourceConfig config) {
        // 指定对应数据库的表信息查询器和表字段信息查询器
        return new MySqlTableSelector(config, new MysqlFieldSelector(config));
    }
}
