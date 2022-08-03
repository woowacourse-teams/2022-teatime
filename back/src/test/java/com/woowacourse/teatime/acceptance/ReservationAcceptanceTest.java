package com.woowacourse.teatime.acceptance;

import static com.woowacourse.teatime.fixture.DomainFixture.DATE_TIME;
import static com.woowacourse.teatime.fixture.DtoFixture.COACH_BROWN_SAVE_REQUEST;
import static com.woowacourse.teatime.fixture.DtoFixture.CREW_SAVE_REQUEST;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.teatime.controller.dto.ReservationCancelRequest;
import com.woowacourse.teatime.controller.dto.request.CoachReservationsRequest;
import com.woowacourse.teatime.controller.dto.request.ReservationApproveRequest;
import com.woowacourse.teatime.controller.dto.request.ReservationReserveRequest;
import com.woowacourse.teatime.service.CoachService;
import com.woowacourse.teatime.service.CrewService;
import com.woowacourse.teatime.service.ScheduleService;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ReservationAcceptanceTest extends AcceptanceTest {

    @Autowired
    private CoachService coachService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private CrewService crewService;

    private Long coachId;
    private Long scheduleId;
    private Long crewId;

    @BeforeEach
    void setUp() {
        coachId = coachService.save(COACH_BROWN_SAVE_REQUEST);
        scheduleId = scheduleService.save(coachId, DATE_TIME);
        crewId = crewService.save(CREW_SAVE_REQUEST);
    }

    @DisplayName("예약한다.")
    @Test
    void reserve() {
        RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ReservationReserveRequest(crewId, coachId, scheduleId))
                .filter(document("reserve", requestFields(
                        fieldWithPath("crewId").description("크루 아이디"),
                        fieldWithPath("coachId").description("코치 아이디"),
                        fieldWithPath("scheduleId").description("스케줄 아이디")
                )))
                .when().post("/api/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("코치가 예약을 승인 및 거부한다.")
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void approve(boolean isApprove) {
        예약을_한다();
        ExtractableResponse<Response> response1 = 코치의_면담_목록을_조회한다();
        List<Long> reservationIds_beforeApproved
                = response1.jsonPath().getList("beforeApproved.reservationId", Long.class);

        RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("reservationId", reservationIds_beforeApproved.get(0))
                .body(new ReservationApproveRequest(coachId, isApprove))
                .filter(document("reserve-approve", requestFields(
                        fieldWithPath("coachId").description("코치 아이디"),
                        fieldWithPath("isApproved").description("승인 여부")
                )))
                .when().post("/api/reservations/{reservationId}")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("코치 및 크루가 예약을 취소할 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"COACH", "CREW"})
    void cancel(String role) {
        예약을_한다();
        ExtractableResponse<Response> response1 = 코치의_면담_목록을_조회한다();
        List<Long> reservationIds_beforeApproved
                = response1.jsonPath().getList("beforeApproved.reservationId", Long.class);
        승인을_한다(reservationIds_beforeApproved.get(0));

        RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("reservationId", reservationIds_beforeApproved.get(0))
                .body(new ReservationCancelRequest(coachId, role))
                .filter(document("reserve-cancel", requestFields(
                        fieldWithPath("applicantId").description("취소 신청자 아이디"),
                        fieldWithPath("role").description("신청자의 역할(코치, 크루)")
                )))
                .when().delete("/api/reservations/{reservationId}")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("진행중인 일정을 완료한다.")
    @Test
    void finish() {
        예약을_한다();
        ExtractableResponse<Response> response1 = 코치의_면담_목록을_조회한다();
        List<Long> reservationIds_beforeApproved
                = response1.jsonPath().getList("beforeApproved.reservationId", Long.class);
        승인을_한다(reservationIds_beforeApproved.get(0));
        ExtractableResponse<Response> response2 = 코치의_면담_목록을_조회한다();
        List<Long> reservationIds_inProgress
                = response2.jsonPath().getList("inProgress.reservationId", Long.class);

        RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("reservationId", reservationIds_inProgress.get(0))
                .filter(document("reserve-finish"))
                .when().put("/api/reservations/{reservationId}")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    private void 예약을_한다() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ReservationReserveRequest(crewId, coachId, scheduleId))
                .when().post("/api/reservations")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 승인을_한다(Long reservationId) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("reservationId", reservationId)
                .body(new ReservationApproveRequest(coachId, true))
                .when().post("/api/reservations/{reservationId}")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 코치의_면담_목록을_조회한다() {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CoachReservationsRequest(coachId))
                .when().get("/api/coaches/me/reservations")
                .then().log().all()
                .extract();
    }
}
