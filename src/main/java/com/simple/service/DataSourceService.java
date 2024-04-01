package com.simple.service;

import com.simple.bean.DataSourceConfig;

import java.util.List;

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

    List  getTableNameListById(Long id);
}
