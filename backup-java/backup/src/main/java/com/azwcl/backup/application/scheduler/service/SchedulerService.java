package com.azwcl.backup.application.scheduler.service;

import com.azwcl.backup.application.scheduler.converter.ToDatabaseConnectionDoConverter;
import com.azwcl.backup.application.scheduler.job.MySqlBackUpJob;
import com.azwcl.backup.domain.mysql.model.domainobject.DatabaseConnectionDO;
import com.azwcl.backup.interfaces.starter.dto.DatabaseBackUpDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

/**
 * 调度器 service
 *
 * @author azwcl
 * @date 2023/06/19
 * @since v0.0.1
 */

@Service
@RequiredArgsConstructor
public class SchedulerService {
    private final ObjectMapper objectMapper;

    public void mysqlBackUpScheduler(DatabaseBackUpDTO databaseBackUpDTO) throws SchedulerException, JsonProcessingException {
        // 创建调度器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 启动调度器
        scheduler.start();

        // 创建任务
        DatabaseConnectionDO databaseConnectionDo = ToDatabaseConnectionDoConverter.CONVERTER.toDatabaseConnectionDo(databaseBackUpDTO);
        JobDetail job = JobBuilder.newJob(MySqlBackUpJob.class)
                .usingJobData("database", this.objectMapper.writeValueAsString(databaseConnectionDo))
                .storeDurably()
                .build();

        // 创建触发器，定义任务的执行规则
        Trigger trigger = TriggerBuilder.newTrigger().
                withSchedule(CronScheduleBuilder.cronSchedule(databaseBackUpDTO.getTime())).build();

        // 将任务和触发器添加到调度器中
        scheduler.scheduleJob(job, trigger);

    }
}
