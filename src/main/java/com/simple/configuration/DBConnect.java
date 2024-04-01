package com.simple.configuration;

import cn.hutool.core.util.ObjectUtil;
import com.simple.bean.DataSourceConfig;
import com.simple.enums.DataBaseType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 用于数据库连接测试
 */
public class DBConnect {
    /**
     * 测试数据源的可连接性
     * 返回null表示连接成功
     *
     * @param config 数据源配置
     * @return
     */
    public static String testConnect(DataSourceConfig config) {
        String res = null;
        Connection connection = null;
        try {
            connection = getConnection(config);
            if (connection == null) {
                res = config.getDbName() + "连接失败";
            }
        } catch (ClassNotFoundException e) {
            res = "找不到驱动" + DBConnect.getDriverClass(config.getDbType());
        } catch (SQLException e) {
            res = config.getDbName() + "连接失败，失败原因：" + e.getMessage();
        } finally {
            // 注意这里需要关闭创建的测试连接
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }

    /**
     * 创建一个数据库连接
     *
     * @param config 数据源配置
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection(DataSourceConfig config) throws ClassNotFoundException, SQLException {
        Class.forName(getDriverClass(config.getDbType()));
        String jdbcUrl = formatJdbcUrl(config);
        return DriverManager.getConnection(jdbcUrl, config.getUserName(), config.getPassword());
    }

    /**
     * 获取对应数据源信息，格式化数据库连接
     *
     * @param config 数据源配置
     * @return
     */
    public static String formatJdbcUrl(DataSourceConfig config) {
        DataBaseType info = DataBaseType.getDBInfoByType(config.getDbType());
        if (ObjectUtil.isNull(info)) {
            throw new RuntimeException("暂不支持该类型数据源");
        }
        String jdbcUrl = info.getJdbcUrl();
        return String.format(jdbcUrl, config.getHost(), config.getPort(), config.getDbName());
    }

    /**
     * 根据数据源类型获取驱动类信息
     *
     * @param type 数据源类型
     * @return
     */
    public static String getDriverClass(Integer type) {
        DataBaseType info = DataBaseType.getDBInfoByType(type);
        if (ObjectUtil.isNull(info)) {
            throw new RuntimeException("暂不支持该类型数据源");
        }
        return info.getDriverClass();
    }
}
