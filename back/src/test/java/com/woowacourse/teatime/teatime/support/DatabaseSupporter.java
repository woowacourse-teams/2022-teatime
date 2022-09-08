package com.woowacourse.teatime.teatime.support;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSupporter {

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @AfterEach
    public void tearDown() {
        databaseCleaner.execute();
    }
}
