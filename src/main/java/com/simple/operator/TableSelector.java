package com.simple.operator;

import com.simple.bean.DataSourceConfig;
import com.simple.configuration.DataBaseConfig;
import com.simple.operator.bean.FieldInfo;
import com.simple.operator.bean.TableInfo;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据库表信息查询
 *
 * @author Simple
 */
public abstract class TableSelector {

    /**
     * 数据源配置
     */
    protected DataSourceConfig dataSourceConfig;

    /**
     * fieldInfo实例，在获取对应数据库service时指定
     */
    private FieldSelector fieldSelector;

    private boolean isGenerate;

    public TableSelector(DataSourceConfig dataSourceConfig, FieldSelector fieldSelector) {
        this.dataSourceConfig = dataSourceConfig;
        this.fieldSelector = fieldSelector;
    }

    /**
     * 不同数据库获取表信息的sql语句不同，所以定义为抽象类
     *
     * @return SQL语句
     */
    public abstract String getShowTablesSql();

    /**
     * 不同数据库中获取的表信息字段不同，所以也将该方法抽象出来
     *
     * @param tableMap 数据库中查询出的表信息Map
     */
    public abstract TableInfo buildTableInfo(Map<String, Object> tableMap);

    /**
     * 获取数据库中的表信息（不包含字段信息，用于展示数据库下的表名）
     * 这里将获取表信息分成两个方法，主要是为了避免切换数据源时不要的性能消耗。
     * 切换数据源实际上只需要展示出选中数据源下的表名，如果表很多，表中字段也很多，那么每次查询表名信息时再去查询字段信息可能会导致相应变慢，影响体验
     */
    public List<TableInfo> getTableInfos() {
        // 因为不同数据库获取表信息sql语句可能不同所以，将getShowTablesSql方法抽象出来，由具体子类实现
        String sql = getShowTablesSql();
        JdbcTemplate jdbcTemplate = DataBaseConfig.otherJdbcTemplate(dataSourceConfig);
        List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sql);
        List<TableInfo> tableInfoList = new ArrayList<>();
        if (!isGenerate) {
            for (Map<String, Object> tableMap : queryForList) {
                TableInfo tableInfo = buildTableInfo(tableMap);
                tableInfoList.add(tableInfo);
            }
        } else {
            for (Map<String, Object> tableMap : queryForList) {
                TableInfo tableInfo = buildTableInfo(tableMap);
                List<FieldInfo> fieldInfoList = fieldSelector.buildFieldInfo(jdbcTemplate, tableInfo.getTableName());
                tableInfo.setFieldInfoList(fieldInfoList);
                tableInfoList.add(tableInfo);
            }
        }

        return tableInfoList;
    }

    public void setGenerate(boolean isGenerate) {
        this.isGenerate = isGenerate;
    }
}
