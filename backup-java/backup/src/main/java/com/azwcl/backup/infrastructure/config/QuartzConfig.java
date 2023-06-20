package com.azwcl.backup.infrastructure.config;

import com.azwcl.backup.infrastructure.factory.CustomJobFactory;
import lombok.SneakyThrows;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 *
 * @author azwcl
 * @date 2023/06/20
 * @since v0.0.1
 */

@Configuration
public class QuartzConfig {
    @Autowired
    private CustomJobFactory customJobFactory;


    @SneakyThrows
    @Bean(name = "scheduler")
    public Scheduler scheduler() {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        // 自定义 JobFactory 使得在 Quartz Job 中可以使用 @Autowired
        scheduler.setJobFactory(customJobFactory);
        scheduler.start();
        return scheduler;
    }

}

