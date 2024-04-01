package com.simple.operator;

import com.simple.bean.DataSourceConfig;

/**
 * @author Simple
 */
public abstract class SqlService {

    public abstract TableSelector getTableSelector(DataSourceConfig config);
}
