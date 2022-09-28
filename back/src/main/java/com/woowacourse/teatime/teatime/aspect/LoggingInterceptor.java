package com.woowacourse.teatime.teatime.aspect;

import com.woowacourse.teatime.teatime.aspect.QueryCountInspector.Counter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final String QUERY_COUNT_LOG_FORMAT = "STATUS_CODE: {}, METHOD: {}, URL: {}, TIME: {}초, QUERY_COUNT: {}";
    private static final String QUERY_COUNT_WARNING_LOG_FORMAT = "하나의 요청에 쿼리가 10번 이상 날라갔습니다.  쿼리 횟수 : {} ";
    private static final int QUERY_COUNT_WARNING_STANDARD = 10;

    private final QueryCountInspector queryCountInspector;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        queryCountInspector.startCounter();
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        Counter counter = queryCountInspector.getQueryCount();
        double duration = (System.currentTimeMillis() - counter.getTime()) / 1000.0;
        long queryCount = counter.getCount();

        log.info(QUERY_COUNT_LOG_FORMAT, response.getStatus(), request.getMethod(), request.getRequestURI(),
                duration, queryCount);
        if (queryCount >= QUERY_COUNT_WARNING_STANDARD) {
            log.warn(QUERY_COUNT_WARNING_LOG_FORMAT, queryCount);
        }
        queryCountInspector.clearCounter();
    }
}
