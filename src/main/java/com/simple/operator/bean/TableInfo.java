package com.simple.operator.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 表信息
 *
 * @author Simple
 */
@Data
public class TableInfo {

    /**
     * schema(模式)，pg数据库使用
     */
    private String schema;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String comment;

    /**
     * 字段信息
     */
    private transient List<FieldInfo> fieldInfoList = new ArrayList<>();
}
