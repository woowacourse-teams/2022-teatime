package com.woowacourse.teatime.acceptance;

import static com.woowacourse.teatime.acceptance.CoachAcceptanceTest.코치를_저장한다;
import static com.woowacourse.teatime.acceptance.CoachAcceptanceTest.코치의_면담목록을_불러온다;
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
import com.woowacourse.teatime.service.CrewService;
import com.woowacourse.teatime.service.ScheduleService;
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

public class ReservationAcceptanceTest extends AcceptanceTest {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private CrewService crewService;

    private Long coachId;
    private Long scheduleId;
    private Long crewId;

    @BeforeEach
    void setUp() {
        coachId = 코치를_저장한다(COACH_BROWN_SAVE_REQUEST);
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
        Long reservationId = ReservationAcceptanceTest.예약을_한다(
                new ReservationReserveRequest(crewId, coachId, scheduleId));

        RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("reservationId", reservationId)
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
        Long reservationId = 예약을_한다(new ReservationReserveRequest(crewId, coachId, scheduleId));
        예약을_승인한다(reservationId, new ReservationApproveRequest(coachId, true));

        RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("reservationId", reservationId)
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
        Long reservationId = 예약을_한다(new ReservationReserveRequest(crewId, coachId, scheduleId));
        예약을_승인한다(reservationId, new ReservationApproveRequest(coachId, true));
        코치의_면담목록을_불러온다(coachId);

        RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("reservationId", reservationId)
                .filter(document("reserve-finish"))
                .when().put("/api/reservations/{reservationId}")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    public static Long 예약을_한다(Object object) {
        ExtractableResponse<Response> response = post("/api/reservations", object);

        return Long.parseLong(response.header("Location").split("/")[5]);
    }

    public static void 예약을_승인한다(Long reservationId, Object object) {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("reservationId", reservationId)
                .body(object)
                .when().post("/api/reservations/{reservationId}")
                .then().log().all()
                .extract();
    }

    public static void 예약을_완료한다(Long reservationId) {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("reservationId", reservationId)
                .when().put("/api/reservations/{reservationId}")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }
}
