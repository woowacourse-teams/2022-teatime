package com.woowacourse.teatime.teatime.acceptance;

import static com.woowacourse.teatime.teatime.fixture.DtoFixture.COACH_BROWN_SAVE_REQUEST;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.CREW_SAVE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.teatime.teatime.controller.dto.request.ScheduleUpdateRequest;
import com.woowacourse.teatime.teatime.controller.dto.response.ScheduleFindResponse;
import com.woowacourse.teatime.teatime.service.CoachService;
import com.woowacourse.teatime.teatime.service.CrewService;
import com.woowacourse.teatime.teatime.service.ScheduleService;
import com.woowacourse.teatime.util.Date;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class ScheduleAcceptanceTest extends AcceptanceTestSupporter {

    private static final LocalDate NOW = LocalDate.now();
    private static final LocalDate LAST_DATE_OF_MONTH = NOW.withDayOfMonth(NOW.lengthOfMonth());
    private static final boolean IS_LAST_DAY_OF_MONTH = NOW.isEqual(LAST_DATE_OF_MONTH);
    private static final int YEAR = NOW.getYear();
    private static final int MONTH = NOW.getMonthValue();

    @Autowired
    private CoachService coachService;

    @Autowired
    private CrewService crewService;

    @Autowired
    private ScheduleService scheduleService;

    private Long coachId;
    private String coachToken;

    @BeforeEach
    void setUp() {
        coachId = coachService.save(COACH_BROWN_SAVE_REQUEST);
        coachToken = 코치의_토큰을_발급한다(coachId);
    }

    @DisplayName("크루가 코치의 스케줄을 조회한다.")
    @Test
    void findByCoachIdAndDate() {
        Long crewId = crewService.save(CREW_SAVE_REQUEST);
        String crewToken = 크루의_토큰을_발급한다(crewId);
        scheduleService.save(coachId, LocalDateTime.of(LAST_DATE_OF_MONTH, LocalTime.of(23, 59)));

        ExtractableResponse<Response> response = RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("coachId", coachId)
                .queryParam("year", YEAR)
                .queryParam("month", MONTH)
                .header("Authorization", "Bearer " + crewToken)
                .filter(document("find-coach-schedules"))
                .when().get("/api/v2/coaches/{coachId}/schedules")
                .then().log().all()
                .extract();
        List<ScheduleFindResponse> result = response.jsonPath().getList(".", ScheduleFindResponse.class);

        if (IS_LAST_DAY_OF_MONTH) {
            assertAll(
                    () -> assertThat(result).hasSize(1),
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
            );
        }

        if (!IS_LAST_DAY_OF_MONTH) {
            assertAll(
                    () -> assertThat(result).hasSize(1),
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
            );
        }
    }

    @DisplayName("코치가 자신의 스케줄을 조회한다.")
    @Test
    void findOwnSchedules() {
        scheduleService.save(coachId, Date.findFirstDateTime(YEAR, MONTH).plusHours(1));
        scheduleService.save(coachId, LocalDateTime.of(LAST_DATE_OF_MONTH, LocalTime.of(23, 59)));

        ExtractableResponse<Response> response = 코치가_자신의_스케쥴_조회_요청됨(YEAR, MONTH, coachToken);
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
        //given
        scheduleService.save(coachId, Date.findFirstDateTime(YEAR, MONTH).plusHours(1));

        //when
        LocalDateTime localDateTime = LocalDateTime.of(LAST_DATE_OF_MONTH, LocalTime.of(23, 59));
        ScheduleUpdateRequest request = new ScheduleUpdateRequest(LAST_DATE_OF_MONTH, List.of(localDateTime));

        ExtractableResponse<Response> updateResponse = RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + coachToken)
                .body(List.of(request))
                .filter(document("update-schedule", requestFields(
                        fieldWithPath("[].date").description("날짜"),
                        fieldWithPath("[].schedules").description("스케줄")
                )))
                .when().put("/api/v2/coaches/me/schedules")
                .then().log().all()
                .extract();

        //then
        ExtractableResponse<Response> findResponse = 코치가_자신의_스케쥴_조회_요청됨(YEAR, MONTH, coachToken);
        List<ScheduleFindResponse> result = findResponse.jsonPath().getList(".", ScheduleFindResponse.class);

        if (IS_LAST_DAY_OF_MONTH) {
            assertAll(
                    () -> assertThat(updateResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                    () -> assertThat(result).hasSize(1)
            );
        }

        if (!IS_LAST_DAY_OF_MONTH) {
            assertAll(
                    () -> assertThat(updateResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                    () -> assertThat(result).hasSize(2)
            );
        }
    }

    public static void 스케쥴_수정_요청됨(ScheduleUpdateRequest request, String coachToken) {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + coachToken)
                .body(request)
                .when().put("/api/v2/coaches/me/schedules")
                .then().log().all()
                .extract();
    }

    public static List<ScheduleFindResponse> 크루가_코치의_스케줄_조회_요청됨(Long coachId, int year, int month, String crewToken) {
        String url = "/api/v2/coaches/{coachId}/schedules";
        Map<String, Object> pathParams = Map.of("coachId", coachId);
        Map<String, Object> queryParams = Map.of("year", year, "month", month);
        ExtractableResponse<Response> response = get(url, crewToken, pathParams, queryParams);
        return response.jsonPath().getList(".", ScheduleFindResponse.class);
    }

    private ExtractableResponse<Response> 코치가_자신의_스케쥴_조회_요청됨(int year, int month, String coachToken) {
        return RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("year", year)
                .queryParam("month", month)
                .header("Authorization", "Bearer " + coachToken)
                .filter(document("find-own-schedules", responseFields(
                        subsectionWithPath("[].day").description("날짜"),
                        subsectionWithPath("[].schedules").description("스케줄")
                )))
                .when().get("/api/v2/coaches/me/schedules")
                .then().log().all()
                .extract();
    }
}
