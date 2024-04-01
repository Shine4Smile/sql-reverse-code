package com.simple.controller;

import com.simple.bean.DataSourceConfig;
import com.simple.common.Response;
import com.simple.configuration.DBConnect;
import com.simple.service.DataSourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 数据源控制器，用于数据源的测试、新增、编辑等
 */
@RestController
@RequestMapping("/datasource")
public class DataSourceController {

    @Resource
    private DataSourceService dataSourceService;

    /**
     * 测试连接数据源
     *
     * @param dataSourceConfig 数据源配置
     */
    @PostMapping("/testDataSource")
    public Response testDataSource(@RequestBody DataSourceConfig dataSourceConfig) {
        String res = DBConnect.testConnect(dataSourceConfig);
        if (StringUtils.isNotBlank(res)) {
            return Response.error(res);
        } else {
            return Response.ok();
        }
    }

    /**
     * 查询数据源列表
     */
    @GetMapping("/getDataSourceList")
    public Response getDataSourceList() {
        List<DataSourceConfig> dataSourceList = dataSourceService.getDataSourceList();
        return Response.ok(dataSourceList);
    }

    /**
     * 根据id获取数据源
     */
    @GetMapping("/getDataSourceById")
    public Response getDataSourceById(Long id) {
        DataSourceConfig dataSourceById = dataSourceService.getDataSourceById(id);
        return Response.ok(dataSourceById);
    }

    /**
     * 新增数据源
     *
     * @param dataSourceConfig 数据源配置
     */
    @PostMapping("/addDataSource")
    public Response addDataSource(@RequestBody DataSourceConfig dataSourceConfig) {
        boolean flag = dataSourceService.addDataSource(dataSourceConfig);
        if (!flag) {
            return Response.error();
        }
        return Response.ok();
    }

    /**
     * 更新数据源
     *
     * @param dataSourceConfig 数据源配置
     */
    @PostMapping("/updateDataSource")
    public Response updateDataSource(@RequestBody DataSourceConfig dataSourceConfig) {
        boolean flag = dataSourceService.updateDataSource(dataSourceConfig);
        if (!flag) {
            return Response.error();
        }
        return Response.ok();
    }

    /**
     * 删除数据源（逻辑删除）
     *
     * @param id 数据源id
     */
    @GetMapping("/delDataSource")
    public Response delDataSource(Long id) {
        boolean flag = dataSourceService.delDataSource(id);
        if (!flag) {
            return Response.error();
        }
        return Response.ok();
    }

    /**
     * 根据选择的数据源，获取该数据源中的表名
     * @return
     */
    @GetMapping("/getTableNameListById")
    public Response getTableNameListById(){
        return Response.ok();
    }
}
