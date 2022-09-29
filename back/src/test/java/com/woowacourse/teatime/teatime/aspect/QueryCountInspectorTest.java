package com.woowacourse.teatime.teatime.aspect;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.teatime.teatime.aspect.QueryCountInspector.Counter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QueryCountInspectorTest {

    @DisplayName("객체가 생성되었는지 확인한다.")
    @Test
    void startCounter() {
        // given
        final QueryCountInspector queryCountInspector = new QueryCountInspector();

        // when
        queryCountInspector.startCounter();

        // then
        assertThat(queryCountInspector.getQueryCount()).isNotNull();
    }

    @DisplayName("초기 count는 0이다.")
    @Test
    void initCountIsZero() {
        // given
        final QueryCountInspector queryCountInspector = new QueryCountInspector();
        queryCountInspector.startCounter();

        // when
        Counter counter = queryCountInspector.getQueryCount();
        Long queryCount = counter.getCount();

        // then
        assertThat(queryCount).isZero();
    }

    @DisplayName("쿼리를 확인할 때마다 쿼리수가 1씩 증가한다.")
    @Test
    void inspect() {
        // given
        final QueryCountInspector queryCountInspector = new QueryCountInspector();
        queryCountInspector.startCounter();

        // when
        queryCountInspector.inspect("INSERT INTO crew VALUES('value1', 'value2', 'value3')");

        // then
        Counter counter = queryCountInspector.getQueryCount();
        Long queryCount = counter.getCount();
        assertThat(queryCount).isOne();
    }

    @DisplayName("쿼리수를 제거한다.")
    @Test
    void remove() {
        // given
        final QueryCountInspector queryCountInspector = new QueryCountInspector();
        queryCountInspector.startCounter();

        // when
        queryCountInspector.clearCounter();

        // then
        Counter counter = queryCountInspector.getQueryCount();
        assertThat(counter).isNull();
    }
}
