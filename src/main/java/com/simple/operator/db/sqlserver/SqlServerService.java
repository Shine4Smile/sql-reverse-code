package com.simple.operator.db.sqlserver;

import com.simple.bean.DataSourceConfig;
import com.simple.operator.SqlService;
import com.simple.operator.TableSelector;

public class SqlServerService extends SqlService {
    @Override
    public TableSelector getTableSelector(DataSourceConfig config) {
        return null;
    }
}
