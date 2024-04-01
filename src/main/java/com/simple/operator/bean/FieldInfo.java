package com.simple.operator.bean;

import lombok.Data;

/**
 * 表字段信息
 *
 * @author Simple
 */
@Data
public class FieldInfo {

    /**
     * 数据库字段名
     */
    private String fieldName;
    /**
     * 字段类型
     */
    private String type;
    /**
     * 是否自增
     */
    private Boolean isIncrement;
    /**
     * 是否主键
     */
    private Boolean isPk;
    /**
     * 字段注释
     */
    private String comment;
    /**
     * 字段长度
     */
    private Integer maxLength;
    /**
     * 小数位长度
     */
    private Integer scale;

    /**
     * 字段是否允许为null
     */
    private Boolean isNullable = false;
}
