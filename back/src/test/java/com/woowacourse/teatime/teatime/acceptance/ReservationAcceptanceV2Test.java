package com.woowacourse.teatime.teatime.acceptance;

import static com.woowacourse.teatime.teatime.acceptance.CoachAcceptanceTest.코치를_저장한다;
import static com.woowacourse.teatime.teatime.acceptance.CoachAcceptanceTest.코치의_면담목록을_불러온다;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.DATE_TIME;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.COACH_BROWN_SAVE_REQUEST;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.CREW_SAVE_REQUEST;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.teatime.teatime.controller.dto.request.ReservationApproveRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.ReservationApproveRequestV2;
import com.woowacourse.teatime.teatime.controller.dto.request.ReservationReserveRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.ReservationReserveRequestV2;
import com.woowacourse.teatime.teatime.service.CrewService;
import com.woowacourse.teatime.teatime.service.ScheduleService;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ReservationAcceptanceV2Test extends AcceptanceTest {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private CrewService crewService;

    private Long coachId;
    private Long scheduleId;
    private Long crewId;
    private String crewToken;
    private String coachToken;

    private static Long 예약을_한다(Object object, String crewToken) {
        ExtractableResponse<Response> response = postV2("/api/v2/reservations", object, crewToken);
        return Long.parseLong(response.header("Location").split("/reservations/")[1]);
    }

    private static void 예약을_승인한다(Long reservationId, Object object, String coachToken) {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("reservationId", reservationId)
                .header("Authorization", "Bearer " + coachToken)
                .body(object)
                .when().post("/api/v2/reservations/{reservationId}")
                .then().log().all()
                .extract();
    }

    private static void 예약을_완료한다(Long reservationId, String coachToken) {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("reservationId", reservationId)
                .header("Authorization", "Bearer " + coachToken)
                .when().put("/api/v2/reservations/{reservationId}")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @BeforeEach
    void setUp() {
        coachId = 코치를_저장한다(COACH_BROWN_SAVE_REQUEST);
        scheduleId = scheduleService.save(coachId, DATE_TIME);
        crewId = crewService.save(CREW_SAVE_REQUEST);
        crewToken = 크루의_토큰을_발급한다(crewId);
        coachToken = 코치의_토큰을_발급한다(coachId);
    }

    @DisplayName("예약한다.")
    @Test
    void reserve() {
        RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + crewToken)
                .body(new ReservationReserveRequestV2(scheduleId))
                .filter(document("reserve", requestFields(
                        fieldWithPath("scheduleId").description("스케줄 아이디")
                )))
                .when().post("/api/v2/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("코치가 예약을 승인 및 거부한다.")
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void approve(boolean isApprove) {
        Long reservationId = 예약을_한다(new ReservationReserveRequestV2(scheduleId), crewToken);

        RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("reservationId", reservationId)
                .header("Authorization", "Bearer " + coachToken)
                .body(new ReservationApproveRequestV2(isApprove))
                .filter(document("reserve-approve", requestFields(
                        fieldWithPath("isApproved").description("승인 여부")
                )))
                .when().post("/api/v2/reservations/{reservationId}")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("코치가 예약을 취소할 수 있다.")
    @Test
    void cancel_by_coach() {
        Long reservationId = 예약을_한다(new ReservationReserveRequestV2(scheduleId), crewToken);
        예약을_승인한다(reservationId, new ReservationApproveRequest(coachId, true), coachToken);

        RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("reservationId", reservationId)
                .header("Authorization", "Bearer " + coachToken)
                .filter(document("reserve-cancel"))
                .when().delete("/api/v2/reservations/{reservationId}")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("크루가 예약을 취소할 수 있다.")
    @Test
    void cancel_by_crew() {
        Long reservationId = 예약을_한다(new ReservationReserveRequest(crewId, coachId, scheduleId), crewToken);
        예약을_승인한다(reservationId, new ReservationApproveRequest(coachId, true), coachToken);

        RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("reservationId", reservationId)
                .header("Authorization", "Bearer " + crewToken)
                .filter(document("reserve-cancel"))
                .when().delete("/api/v2/reservations/{reservationId}")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("진행중인 일정을 완료한다.")
    @Test
    void finish() {
        Long reservationId = 예약을_한다(new ReservationReserveRequest(crewId, coachId, scheduleId), crewToken);
        예약을_승인한다(reservationId, new ReservationApproveRequest(coachId, true), coachToken);
        코치의_면담목록을_불러온다(coachId);

        RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("reservationId", reservationId)
                .header("Authorization", "Bearer " + coachToken)
                .filter(document("reserve-finish"))
                .when().put("/api/v2/reservations/{reservationId}")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

}
