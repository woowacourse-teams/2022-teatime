package com.woowacourse.teatime.teatime.support;

import com.google.common.base.CaseFormat;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Component
@ActiveProfiles("test")
public class DatabaseCleaner implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames;

    @Override
    public void afterPropertiesSet() {
        this.tableNames = entityManager.getMetamodel().getEntities().stream()
                .filter(e -> e.getJavaType().getAnnotation(Entity.class) != null)
                .map(e -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void execute() {
        entityManager.unwrap(Session.class)
                .doWork(this::cleanUpDatabase);
    }

    private void cleanUpDatabase(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        statement.executeUpdate("SET REFERENTIAL_INTEGRITY FALSE");

        for (String tableName : tableNames) {
            statement.executeUpdate("TRUNCATE TABLE " + tableName);
            statement.executeUpdate("ALTER TABLE " + tableName + " ALTER COLUMN id RESTART WITH 1");
        }

        statement.executeUpdate("SET REFERENTIAL_INTEGRITY TRUE");
    }
}
