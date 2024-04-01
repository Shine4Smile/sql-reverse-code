package com.simple.operator;

import com.simple.enums.DataBaseType;
import com.simple.operator.db.mysql.MySqlService;
import com.simple.operator.db.oracle.OracleService;
import com.simple.operator.db.postgres.PostgreSqlService;
import com.simple.operator.db.sqlserver.SqlServerService;

import java.util.HashMap;
import java.util.Map;

/**
 * 简单工厂，根据数据库类型创建对应的service
 *
 * @author Simple
 */
public class SqlServiceFactory {
    private static final Map<Integer, SqlService> SERVICE_MAP = new HashMap<>();

    static {
        SERVICE_MAP.put(DataBaseType.MYSQL5.getType(), new MySqlService());
        SERVICE_MAP.put(DataBaseType.MYSQL8.getType(), new MySqlService());
        SERVICE_MAP.put(DataBaseType.ORACLE.getType(), new OracleService());
        SERVICE_MAP.put(DataBaseType.SQLSERVER.getType(), new SqlServerService());
        SERVICE_MAP.put(DataBaseType.POSTGRESQL.getType(), new PostgreSqlService());
    }

    public static SqlService getService(Integer dbType) {
        SqlService sqlService = SERVICE_MAP.get(dbType);
        if (sqlService == null) {
            throw new RuntimeException("暂不支持该数据源");
        }
        return sqlService;
    }

}
