package com.simple.service;

import com.simple.bean.DataSourceConfig;
import com.simple.operator.bean.TableInfo;

import java.util.List;

/**
 * 数据源相关操作service
 *
 * @author Simple
 */
public interface DataSourceService {
    /**
     * 查询数据源列表
     */
    List<DataSourceConfig> getDataSourceList();

    /**
     * 根据id获取数据源
     */
    DataSourceConfig getDataSourceById(Long id);

    /**
     * 增加数据源
     *
     * @param dataSourceConfig 数据源配置
     */
    boolean addDataSource(DataSourceConfig dataSourceConfig);

    /**
     * 更新数据源
     *
     * @param dataSourceConfig 数据源配置
     */
    boolean updateDataSource(DataSourceConfig dataSourceConfig);

    /**
     * 删除数据源（逻辑删除）
     *
     * @param id 数据源id
     */
    boolean delDataSource(Long id);

    /**
     * 根据数据源id获取该数据源表信息
     *
     * @param id 数据源id
     */
    List<TableInfo> getTableInfoListById(Long id);
}
