package com.woowacourse.teatime.acceptance;

import static com.woowacourse.teatime.fixture.DomainFixture.DATE_TIME;
import static com.woowacourse.teatime.fixture.DtoFixture.COACH_BROWN_SAVE_REQUEST;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.teatime.controller.dto.ReservationApproveRequest;
import com.woowacourse.teatime.controller.dto.ReservationCancelRequest;
import com.woowacourse.teatime.controller.dto.ReservationRequest;
import com.woowacourse.teatime.service.CoachService;
import com.woowacourse.teatime.service.CrewService;
import com.woowacourse.teatime.service.ReservationService;
import com.woowacourse.teatime.service.ScheduleService;
import io.restassured.RestAssured;
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
    @Autowired
    private ReservationService reservationService;

    private Long coachId;
    private Long scheduleId;
    private Long crewId;

    @BeforeEach
    void setUp() {
        coachId = coachService.save(COACH_BROWN_SAVE_REQUEST);
        scheduleId = scheduleService.save(coachId, DATE_TIME);
        crewId = crewService.save();
    }

    @DisplayName("예약한다.")
    @Test
    void reserve() {
        RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ReservationRequest(crewId, coachId, scheduleId))
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
        Long reservationId = 예약에_성공한다();

        RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ReservationApproveRequest(coachId, true))
                .filter(document("reserve-approve", requestFields(
                        fieldWithPath("coachId").description("코치 아이디"),
                        fieldWithPath("isApproved").description("승인 여부")
                )))
                .body(new ReservationApproveRequest(coachId, isApprove))
                .pathParam("reservationId", reservationId)
                .when().post("/api/reservations/{reservationId}")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("코치 및 크루가 예약을 취소할 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"COACH", "CREW"})
    void cancel_coach(String role) {
        Long reservationId = 예약에_성공한다();
        reservationService.approve(reservationId, new ReservationApproveRequest(coachId, true));

        RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(document("reserve-cancel", requestFields(
                        fieldWithPath("applicantId").description("취소 신청자 아이디"),
                        fieldWithPath("role").description("신청자의 역할(코치, 크루)")
                )))
                .body(new ReservationCancelRequest(coachId, role))
                .pathParam("reservationId", reservationId)
                .when().delete("/api/reservations/{reservationId}")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private Long 예약에_성공한다() {
        ReservationRequest reservationRequest = new ReservationRequest(crewId, coachId, scheduleId);
        return reservationService.save(reservationRequest);
    }
}
