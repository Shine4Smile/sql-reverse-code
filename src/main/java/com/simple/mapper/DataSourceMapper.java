package com.simple.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simple.bean.DataSourceConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源mapper
 *
 * @author Simple
 */
@Mapper
public interface DataSourceMapper extends BaseMapper<DataSourceConfig> {
}
