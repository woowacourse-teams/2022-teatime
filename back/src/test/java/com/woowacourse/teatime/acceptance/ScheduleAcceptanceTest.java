package com.woowacourse.teatime.acceptance;

import static com.woowacourse.teatime.acceptance.CoachAcceptanceTest.코치를_저장한다;
import static com.woowacourse.teatime.fixture.DtoFixture.COACH_BROWN_SAVE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.teatime.controller.dto.request.ScheduleUpdateRequest;
import com.woowacourse.teatime.controller.dto.response.ScheduleFindResponse;
import com.woowacourse.teatime.service.ScheduleService;
import com.woowacourse.teatime.util.Date;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ScheduleAcceptanceTest extends AcceptanceTest {

    private static final LocalDate NOW = LocalDate.now();
    private static final LocalDate LAST_DATE_OF_MONTH = NOW.withDayOfMonth(NOW.lengthOfMonth());
    private static final boolean IS_LAST_DAY_OF_MONTH = NOW.isEqual(LAST_DATE_OF_MONTH);
    private static final int YEAR = NOW.getYear();
    private static final int MONTH = NOW.getMonthValue();

    @Autowired
    private ScheduleService scheduleService;

    @DisplayName("코치에 해당하는 스케줄 목록을 조회한다.")
    @Test
    void findByCoachIdAndDate() {
        Long coachId = 코치를_저장한다(COACH_BROWN_SAVE_REQUEST);
        scheduleService.save(coachId, Date.findFirstDay(YEAR, MONTH));
        scheduleService.save(coachId, LocalDateTime.of(LAST_DATE_OF_MONTH, LocalTime.of(23, 59)));

        ExtractableResponse<Response> response = 스케쥴_조회_요청됨(coachId, YEAR, MONTH);
        List<ScheduleFindResponse> result = response.jsonPath().getList(".", ScheduleFindResponse.class);

        if (IS_LAST_DAY_OF_MONTH) {
            assertAll(
                    () -> assertThat(result).hasSize(1),
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
            );
        }

        if (!IS_LAST_DAY_OF_MONTH) {
            assertAll(
                    () -> assertThat(result).hasSize(2),
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
            );
        }
    }

    @DisplayName("코치의 날짜에 해당하는 하루 스케줄을 업데이트한다.")
    @Test
    void updateByCoachAndDate() {
        Long coachId = 코치를_저장한다(COACH_BROWN_SAVE_REQUEST);
        scheduleService.save(coachId, Date.findFirstDay(YEAR, MONTH));

        LocalDateTime localDateTime = LocalDateTime.of(LAST_DATE_OF_MONTH, LocalTime.of(23, 59));
        ScheduleUpdateRequest request = new ScheduleUpdateRequest(LAST_DATE_OF_MONTH, List.of(localDateTime));

        ExtractableResponse<Response> updateResponse = 스케쥴_수정_요청됨(coachId, request);

        ExtractableResponse<Response> findResponse = 스케쥴_조회_요청됨(coachId, YEAR, MONTH);
        List<ScheduleFindResponse> result = findResponse.jsonPath().getList(".", ScheduleFindResponse.class);

        if (IS_LAST_DAY_OF_MONTH) {
            assertAll(
                    () -> assertThat(updateResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
                    () -> assertThat(result).hasSize(1)
            );
        }

        if (!IS_LAST_DAY_OF_MONTH) {
            assertAll(
                    () -> assertThat(updateResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
                    () -> assertThat(result).hasSize(2)
            );
        }
    }

    private ExtractableResponse<Response> 스케쥴_수정_요청됨(Long coachId, ScheduleUpdateRequest request) {
        return RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("coachId", coachId)
                .body(request)
                .filter(document("update-schedule", requestFields(
                        fieldWithPath("date").description("날짜"),
                        fieldWithPath("schedules").description("스케줄")
                )))
                .when().put("/api/coaches/{coachId}/schedules")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 스케쥴_조회_요청됨(Long coachId, int year, int month) {
        return RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("coachId", coachId)
                .queryParam("year", year)
                .queryParam("month", month)
                .filter(document("find-schedules", responseFields(
                        subsectionWithPath("[].day").description("날짜"),
                        subsectionWithPath("[].schedules").description("스케줄")
                )))
                .when().get("/api/coaches/{coachId}/schedules")
                .then().log().all()
                .extract();
    }
}
