package com.woowacourse.teatime.teatime.config;

import com.woowacourse.teatime.teatime.aspect.QueryCountInspector;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class HibernateConfig {

    private final QueryCountInspector queryCountInspector;

    @Bean
    public HibernatePropertiesCustomizer configureStatementInspector() {
        return hibernateProperties ->
                hibernateProperties.put(AvailableSettings.STATEMENT_INSPECTOR, queryCountInspector);
    }
}
