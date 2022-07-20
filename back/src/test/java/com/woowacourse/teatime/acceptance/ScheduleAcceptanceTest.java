package com.woowacourse.teatime.acceptance;

import static com.woowacourse.teatime.fixture.DtoFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.woowacourse.teatime.controller.dto.ScheduleResponse;
import com.woowacourse.teatime.controller.dto.ScheduleUpdateRequest;
import com.woowacourse.teatime.service.CoachService;
import com.woowacourse.teatime.service.ScheduleService;
import com.woowacourse.teatime.util.Date;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class ScheduleAcceptanceTest extends AcceptanceTest {

    private static final int YEAR = 2022;
    private static final int MONTH = 7;

    @Autowired
    private CoachService coachService;
    @Autowired
    private ScheduleService scheduleService;

    @DisplayName("코치에 해당하는 스케줄 목록을 조회한다.")
    @Test
    void findByCoachIdAndDate() {
        Long coachId = coachService.save(COACH_BROWN_SAVE_REQUEST);
        scheduleService.save(coachId, Date.findFirstDay(YEAR, MONTH));
        scheduleService.save(coachId, LocalDateTime.of(YEAR, MONTH, 31, 23, 59));

        ExtractableResponse<Response> response = 스케쥴_조회_요청됨(coachId, YEAR, MONTH);
        List<ScheduleResponse> result = response.jsonPath().getList(".", ScheduleResponse.class);

        assertAll(
                () -> assertThat(result).hasSize(2),
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        );
    }

    @DisplayName("코치의 날짜에 해당하는 하루 스케줄을 업데이트한다.")
    @Test
    void updateByCoachAndDate() {
        Long coachId = coachService.save(COACH_BROWN_SAVE_REQUEST);
        scheduleService.save(coachId, Date.findFirstDay(YEAR, MONTH));

        LocalDate date = LocalDate.of(YEAR, MONTH, 31);
        LocalDateTime localDateTime = LocalDateTime.of(YEAR, MONTH, 31, 23, 59);
        ScheduleUpdateRequest request = new ScheduleUpdateRequest(date, List.of(localDateTime));

        ExtractableResponse<Response> updateResponse = 스케쥴_수정_요청됨(coachId, request);

        ExtractableResponse<Response> findResponse = 스케쥴_조회_요청됨(coachId, YEAR, MONTH);
        List<ScheduleResponse> result = findResponse.jsonPath().getList(".", ScheduleResponse.class);
        assertAll(
                () -> assertThat(updateResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(result).hasSize(2)
        );
    }

    private ExtractableResponse<Response> 스케쥴_수정_요청됨(Long id, ScheduleUpdateRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", id)
                .body(request)
                .when().put("/api/coaches/{id}/schedules")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 스케쥴_조회_요청됨(Long id, int year, int month) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", id)
                .queryParam("year", year)
                .queryParam("month", month)
                .when().get("/api/coaches/{id}/schedules")
                .then().log().all()
                .extract();
    }
}
