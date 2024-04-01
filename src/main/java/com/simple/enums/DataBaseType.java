package com.simple.enums;


/**
 * 当前程序支持的数据源类型枚举
 */
public enum DataBaseType {
    MYSQL5(1,
            "MySQL5",
            "com.mysql.jdbc.Driver",
            "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai"
    ),
    MYSQL8(2,
            "MySQL8",
            "com.mysql.cj.jdbc.Driver",
            "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai"
    ),
    ORACLE(3,
            "Oracle",
            "oracle.jdbc.driver.OracleDriver",
            "jdbc:oracle:thin:@%s:%s%s"),

    SQL_SERVER(4,
            "SQL Server",
            "com.microsoft.sqlserver.jdbc.SQLServerDriver",
            "jdbc:sqlserver://%s:%s;DatabaseName=%s"),

    POSTGRE_SQL(5,
            "PostgreSQL",
            "org.postgresql.Driver",
            "jdbc:postgresql://%s:%s/%s");

    private final int type;
    private final String displayName;
    private final String driverClass;
    private final String jdbcUrl;

    DataBaseType(int type, String displayName, String driverClass, String jdbcUrl) {
        this.type = type;
        this.displayName = displayName;
        this.driverClass = driverClass;
        this.jdbcUrl = jdbcUrl;
    }

    /**
     * 判断用户选择的数据库类型是否支持
     *
     * @param type
     * @return
     */
    public static DataBaseType getDBInfoByType(int type) {
        DataBaseType[] dataBaseTypes = DataBaseType.values();
        for (DataBaseType info : dataBaseTypes) {
            if (info.type == type) {
                return info;
            }
        }
        return null;
    }

    public int getType() {
        return type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }
}
