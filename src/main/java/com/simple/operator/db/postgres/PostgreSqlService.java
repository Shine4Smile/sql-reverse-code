package com.simple.operator.db.postgres;

import com.simple.bean.DataSourceConfig;
import com.simple.operator.SqlService;
import com.simple.operator.TableSelector;

public class PostgreSqlService extends SqlService {
    @Override
    public TableSelector getTableSelector(DataSourceConfig config) {
        return null;
    }
}
