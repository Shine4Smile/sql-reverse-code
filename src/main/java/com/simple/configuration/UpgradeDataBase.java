package com.simple.configuration;

import cn.hutool.core.io.FileUtil;
import com.sun.xml.internal.ws.util.VersionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 初始化数据库
 * 实现ApplicationRunner接口，重写run方法，是为了在springboot启动完成后自动执行初始化数据库方法
 */
@Service
public class UpgradeDataBase implements ApplicationRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpgradeDataBase.class);

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 最新数据库版本号
     */
    private static final String LATEST_VERSION = "1.0.0";

    /**
     * jdbcTemplate和mybatis都只支持单条sql的执行，所以这里暂时这样实现，后续有好的实现方法再优化
     */
    public void initDataBase() {
        LOGGER.info("开始初始化数据库");
        List<File> sqlFile = getSqlFile();
        for (File file : sqlFile) {
            String sqlString = FileUtil.readUtf8String(file);
            String[] sqlArray = sqlString.split(";");
            for (String sql : sqlArray) {
                jdbcTemplate.execute(sql);
            }
        }
        LOGGER.info("完成初始化数据库");
    }

    /**
     * 根据本地已安装数据库版本号和当前最新版本号进行对比，只执行两个版本号之间的sql脚本文件升级数据库，而不是全部重新安装
     * 这里已安装数据库的版本号本来想写到数据库中，但考虑到这个工具很小，所以就不搞这么复杂，将版本号写在本地文件
     */
    public List<File> getSqlFile() {
        // 获取项目根路径
        String projectPath = System.getProperty("user.dir");
        // 获取sqlite目录
        File sqliteFile = new File(projectPath, "src/main/resources/sql/sqlite");
        List<File> files = FileUtil.loopFiles(sqliteFile);

        String versionTxt = projectPath + File.separator + "src/main/resources/sql/version.txt";
        // 如果本地版本记录文件不存在，说明是第一次安装，则执行所有sql文件即可
        if (!FileUtil.exist(versionTxt)) {
            FileUtil.writeUtf8String(LATEST_VERSION, versionTxt);
            LOGGER.info("首次安装数据库，执行全量sql，数据库版本号：{}", LATEST_VERSION);
            return files;
        }
        // 获取本地已安装数据库版本
        String currentVersion = FileUtil.readUtf8String(projectPath + File.separator + "src/main/resources/sql/version.txt");
        if (VersionUtil.compare(currentVersion, LATEST_VERSION) == 0) {
            LOGGER.info("本地数据库已是最新版本：{}，无需更新", LATEST_VERSION);
            return new ArrayList<>();
        }
        // 根据已安装数据库版本号和最新版本号对需要执行的sql文件进行简单过滤
        files = files.stream().filter(file -> {
            String version = file.getName().substring(1).split("_")[0];
            return VersionUtil.compare(version, currentVersion) > 0 && VersionUtil.compare(version, LATEST_VERSION) <= 0;
        }).collect(Collectors.toList());
        // 将最新版本号写入本地文件
        FileUtil.writeUtf8String(LATEST_VERSION, versionTxt);
        LOGGER.info("本地原数据库版本号：{}，最新版本号：{}，执行增量安装", currentVersion, LATEST_VERSION);
        return files;
    }

    @Override
    public void run(ApplicationArguments args) {
        this.initDataBase();
    }
}
