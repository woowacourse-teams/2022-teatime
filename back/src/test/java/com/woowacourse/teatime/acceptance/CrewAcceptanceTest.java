package com.woowacourse.teatime.acceptance;

import static com.woowacourse.teatime.fixture.DomainFixture.DATE_TIME;
import static com.woowacourse.teatime.fixture.DtoFixture.COACH_BROWN_SAVE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.teatime.controller.dto.request.ReservationReserveRequest;
import com.woowacourse.teatime.controller.dto.response.CrewHistoryFindResponse;
import com.woowacourse.teatime.service.CoachService;
import com.woowacourse.teatime.service.CrewService;
import com.woowacourse.teatime.service.ScheduleService;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class CrewAcceptanceTest extends AcceptanceTest {

    @Autowired
    private CoachService coachService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private CrewService crewService;

    @DisplayName("크루가 자신의 히스토리를 조회한다.")
    @Test
    void findOwnReservations() {
        Long coachId = coachService.save(COACH_BROWN_SAVE_REQUEST);
        Long scheduleId = scheduleService.save(coachId, DATE_TIME);
        Long crewId = crewService.save();

        면담_예약_요청됨(coachId, scheduleId, crewId);

        ExtractableResponse<Response> response = RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(crewId)
                .filter(document("find-own-reservations", responseFields(
                        fieldWithPath("[].reservationId").description("면담 아이디"),
                        fieldWithPath("[].coachName").description("코치 이름"),
                        fieldWithPath("[].coachImage").description("코치 이미지"),
                        fieldWithPath("[].dateTime").description("날짜")
                )))
                .when().get("/api/crews/me/reservations")
                .then().log().all()
                .extract();

        List<CrewHistoryFindResponse> result = response.jsonPath()
                .getList(".", CrewHistoryFindResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(result).hasSize(1)
        );
    }

    @DisplayName("코치가 크루의 히스토리를 조회한다.")
    @Test
    void findCrewReservations() {
        Long coachId = coachService.save(COACH_BROWN_SAVE_REQUEST);
        Long scheduleId = scheduleService.save(coachId, DATE_TIME);
        Long crewId = crewService.save();

        면담_예약_요청됨(coachId, scheduleId, crewId);

        ExtractableResponse<Response> response = RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("crewId", crewId)
                .filter(document("find-crew-reservations", responseFields(
                        fieldWithPath("[].reservationId").description("면담 아이디"),
                        fieldWithPath("[].coachName").description("코치 이름"),
                        fieldWithPath("[].coachImage").description("코치 이미지"),
                        fieldWithPath("[].dateTime").description("날짜")
                )))
                .when().get("/api/crews/{crewId}/reservations")
                .then().log().all()
                .extract();

        List<CrewHistoryFindResponse> result = response.jsonPath().getList(".", CrewHistoryFindResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(result).hasSize(1)
        );
    }

    private void 면담_예약_요청됨(Long coachId, Long scheduleId, Long crewId) {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ReservationReserveRequest(crewId, coachId, scheduleId))
                .when().post("/api/reservations")
                .then().log().all();
    }
}
