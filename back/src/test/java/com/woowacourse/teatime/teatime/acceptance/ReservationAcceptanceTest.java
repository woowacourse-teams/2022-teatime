package com.woowacourse.teatime.teatime.acceptance;

import static com.woowacourse.teatime.teatime.fixture.DomainFixture.DATE_TIME;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.COACH_BROWN_SAVE_REQUEST;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.CREW_SAVE_REQUEST;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.teatime.teatime.controller.dto.request.ReservationApproveRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.ReservationReserveRequest;
import com.woowacourse.teatime.teatime.service.CoachService;
import com.woowacourse.teatime.teatime.service.CrewService;
import com.woowacourse.teatime.teatime.service.ReservationService;
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

class ReservationAcceptanceTest extends AcceptanceTestSupporter {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private CrewService crewService;

    @Autowired
    private CoachService coachService;

    private Long coachId;
    private String coachToken;
    private Long crewId;
    private String crewToken;
    private Long scheduleId;

    public static Long 예약을_한다(ReservationReserveRequest request, String crewToken) {
        ExtractableResponse<Response> response = post("/api/v2/reservations", request, crewToken);
        return Long.parseLong(response.header("Location").split("/reservations/")[1]);
    }

    public static void 예약을_승인한다(Long reservationId, ReservationApproveRequest request, String coachToken) {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("reservationId", reservationId)
                .header("Authorization", "Bearer " + coachToken)
                .body(request)
                .when().post("/api/v2/reservations/{reservationId}")
                .then().log().all()
                .extract();
    }

    public static void 예약을_완료한다(Long reservationId, String coachToken) {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("reservationId", reservationId)
                .header("Authorization", "Bearer " + coachToken)
                .when().put("/api/v2/reservations/{reservationId}")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @BeforeEach
    void setUp() {
        crewId = crewService.save(CREW_SAVE_REQUEST);
        coachId = coachService.save(COACH_BROWN_SAVE_REQUEST);
        crewToken = 크루의_토큰을_발급한다(crewId);
        coachToken = 코치의_토큰을_발급한다(coachId);
        scheduleId = scheduleService.save(coachId, DATE_TIME);
    }

    @DisplayName("예약한다.")
    @Test
    void reserve() {
        RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + crewToken)
                .body(new ReservationReserveRequest(scheduleId))
                .filter(document("reserve"))
                .when().post("/api/v2/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("코치가 예약을 승인 및 거부한다.")
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void approve(boolean isApprove) {
        Long reservationId = 예약을_한다(new ReservationReserveRequest(scheduleId), crewToken);

        RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("reservationId", reservationId)
                .header("Authorization", "Bearer " + coachToken)
                .body(new ReservationApproveRequest(isApprove))
                .filter(document("reserve-approve"))
                .when().post("/api/v2/reservations/{reservationId}")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("코치가 예약을 취소할 수 있다.")
    @Test
    void cancel_by_coach() {
        Long reservationId = 예약을_한다(new ReservationReserveRequest(scheduleId), crewToken);
        예약을_승인한다(reservationId, new ReservationApproveRequest(true), coachToken);

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
        Long reservationId = 예약을_한다(new ReservationReserveRequest(scheduleId), crewToken);
        예약을_승인한다(reservationId, new ReservationApproveRequest(true), coachToken);

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
        Long reservationId = 예약을_한다(new ReservationReserveRequest(scheduleId), crewToken);
        예약을_승인한다(reservationId, new ReservationApproveRequest(true), coachToken);
        승인된_예약을_진행중인_예약으로_변경한다();

        RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("reservationId", reservationId)
                .header("Authorization", "Bearer " + coachToken)
                .filter(document("reserve-finish"))
                .when().put("/api/v2/reservations/{reservationId}")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private void 승인된_예약을_진행중인_예약으로_변경한다() {
        reservationService.updateReservationStatusToInProgress();
    }
}
