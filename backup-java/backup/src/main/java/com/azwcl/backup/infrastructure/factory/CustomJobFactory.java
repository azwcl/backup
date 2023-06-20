package com.azwcl.backup.infrastructure.factory;

import lombok.NonNull;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

/**
 * 自定义 job 工厂
 *
 * @author azwcl
 * @date 2023/06/20
 * @since v0.0.1
 */
@Configuration
public class CustomJobFactory extends AdaptableJobFactory {
    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    @Override
    protected Object createJobInstance(@NonNull TriggerFiredBundle bundle) throws Exception {
        Object jobInstance = super.createJobInstance(bundle);
        autowireCapableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }

}
