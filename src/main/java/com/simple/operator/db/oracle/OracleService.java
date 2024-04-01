package com.simple.operator.db.oracle;

import com.simple.bean.DataSourceConfig;
import com.simple.operator.SqlService;
import com.simple.operator.TableSelector;

public class OracleService extends SqlService {
    @Override
    public TableSelector getTableSelector(DataSourceConfig config) {
        return null;
    }
}
