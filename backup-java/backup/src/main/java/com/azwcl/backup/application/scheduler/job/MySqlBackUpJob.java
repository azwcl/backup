package com.azwcl.backup.application.scheduler.job;

import com.azwcl.backup.domain.mysql.model.domainobject.DatabaseConnectionDO;
import com.azwcl.backup.domain.mysql.service.MySqlService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.sql.SQLException;

/**
 * 数据库备份 job
 *
 * @author azwcl
 * @date 2023/06/19
 * @since v0.0.1
 */
@Configurable
@Slf4j
@RequiredArgsConstructor
public class MySqlBackUpJob implements Job {
    @Autowired
    private MySqlService mySqlService;
    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            log.info("开始备份");
            long time = System.currentTimeMillis();
            Object database = jobExecutionContext.getJobDetail().getJobDataMap().get("database");
            DatabaseConnectionDO connectionDo = objectMapper.readValue(database.toString(), DatabaseConnectionDO.class);
            this.mySqlService.fullBackUp(connectionDo);
            log.info("备份完成；耗时{} ms", System.currentTimeMillis() - time);
        } catch (SQLException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
