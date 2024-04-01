package com.simple.service.impl;

import cn.hutool.core.util.IdUtil;
import com.simple.bean.DataSourceConfig;
import com.simple.mapper.DataSourceMapper;
import com.simple.operator.SqlService;
import com.simple.operator.SqlServiceFactory;
import com.simple.operator.TableSelector;
import com.simple.operator.bean.TableInfo;
import com.simple.service.DataSourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Simple
 */
@Service
public class DataSourceServiceImpl implements DataSourceService {
    @Resource
    private DataSourceMapper dataSourceMapper;

    /**
     * 查询数据源列表
     */
    @Override
    public List<DataSourceConfig> getDataSourceList() {
        return dataSourceMapper.selectList(null);
    }

    /**
     * 根据id获取数据源
     */
    @Override
    public DataSourceConfig getDataSourceById(Long id) {
        return dataSourceMapper.selectById(id);
    }

    /**
     * 增加数据源
     *
     * @param dataSourceConfig 数据源配置
     * @return
     */
    @Override
    public boolean addDataSource(DataSourceConfig dataSourceConfig) {
        // 雪花id
        dataSourceConfig.setId(IdUtil.getSnowflakeNextId());
        // 默认删除状态，0：未删除
        dataSourceConfig.setIsDelete(0);
        return dataSourceMapper.insert(dataSourceConfig) > 0;
    }

    /**
     * 更新数据源
     *
     * @param dataSourceConfig 数据源配置
     */
    @Override
    public boolean updateDataSource(DataSourceConfig dataSourceConfig) {
        return dataSourceMapper.updateById(dataSourceConfig) > 0;
    }

    /**
     * 删除数据源（逻辑删除）
     *
     * @param id 数据源配置
     */
    @Override
    public boolean delDataSource(Long id) {
        return dataSourceMapper.deleteById(id) > 0;
    }

    /**
     * 根据数据源id获取该数据源表信息
     *
     * @param id 数据源id
     */
    @Override
    public List<TableInfo> getTableInfoListById(Long id) {
        // 根据数据源id从数据库中获取该数据源配置信息
        DataSourceConfig dataSourceConfig = dataSourceMapper.selectById(id);
        // 根据数据库类型获取对应service
        SqlService service = SqlServiceFactory.getService(dataSourceConfig.getDbType());
        // 获取到对应数据库的表信息查询器
        TableSelector tableSelector = service.getTableSelector(dataSourceConfig);
        tableSelector.setGenerate(true);
        List<TableInfo> tableInfos = tableSelector.getTableInfos();
        return tableInfos;
    }
}
