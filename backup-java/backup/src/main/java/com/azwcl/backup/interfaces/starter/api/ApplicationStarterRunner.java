package com.azwcl.backup.interfaces.starter.api;

import com.azwcl.backup.application.scheduler.service.SchedulerService;
import com.azwcl.backup.infrastructure.utils.FileUtil;
import com.azwcl.backup.interfaces.starter.dto.DatabaseBackUpDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 应用启动执行
 *
 * @author azwcl
 * @date 2023/06/19
 * @since v0.0.1
 */
@Component
@RequiredArgsConstructor
@Order(1)
@Slf4j

public class ApplicationStarterRunner implements ApplicationRunner {
    private final FileUtil fileUtil;
    private final ObjectMapper objectMapper;
    private final SchedulerService schedulerService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String s = this.fileUtil.readFile("backup.json");
        log.info("读取到文件配置：{}", s);
        DatabaseBackUpDTO[] databases = this.objectMapper.readValue(s, DatabaseBackUpDTO[].class);

        for (DatabaseBackUpDTO database : databases) {
            this.schedulerService.mysqlBackUpScheduler(database);
        }

    }
}
