package com.woowacourse.teatime.teatime.acceptance;

import static com.woowacourse.teatime.teatime.acceptance.ReservationAcceptanceTest.예약을_승인한다;
import static com.woowacourse.teatime.teatime.acceptance.ReservationAcceptanceTest.예약을_한다;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.DATE_TIME;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.COACH_BROWN_SAVE_REQUEST;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.COACH_JUNE_SAVE_REQUEST;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.CREW_SAVE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.teatime.teatime.controller.dto.request.CoachUpdateProfileRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.ReservationApproveRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.ReservationReserveRequest;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachFindOwnHistoryResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachFindResponse;
import com.woowacourse.teatime.teatime.service.CoachService;
import com.woowacourse.teatime.teatime.service.CrewService;
import com.woowacourse.teatime.teatime.service.ScheduleService;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class CoachAcceptanceTest extends AcceptanceTestSupporter {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private CoachService coachService;

    @Autowired
    private CrewService crewService;

    private Long coachId;
    private String coachToken;
    private Long crewId;
    private String crewToken;

    @BeforeEach
    void setUp() {
        crewId = crewService.save(CREW_SAVE_REQUEST);
        crewToken = 크루의_토큰을_발급한다(crewId);
        coachId = coachService.save(COACH_BROWN_SAVE_REQUEST);
        coachToken = 코치의_토큰을_발급한다(coachId);
    }

    @DisplayName("코치 목록을 조회한다.")
    @Test
    void findAll() {
        coachService.save(COACH_JUNE_SAVE_REQUEST);

        Long crewId = crewService.save(CREW_SAVE_REQUEST);
        String crewToken = 크루의_토큰을_발급한다(crewId);
        ExtractableResponse<Response> response = RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + crewToken)
                .filter(document("find-all-coaches"))
                .when().get("/api/v2/coaches")
                .then().log().all()
                .extract();

        List<CoachFindResponse> result = response.jsonPath().getList(".", CoachFindResponse.class);

        assertAll(
                () -> assertThat(result).hasSize(2),
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        );
    }

    @DisplayName("코치의 면담 목록을 불러온다.")
    @Test
    void findReservations() {
        Long coachId = coachService.save(COACH_BROWN_SAVE_REQUEST);

        Long scheduleId = scheduleService.save(coachId, LocalDateTime.now());
        Long scheduleId2 = scheduleService.save(coachId, LocalDateTime.now().plusDays(1));
        Long scheduleId3 = scheduleService.save(coachId, LocalDateTime.now().plusDays(2));
        Long scheduleId4 = scheduleService.save(coachId, LocalDateTime.now().plusDays(3));

        예약을_한다(new ReservationReserveRequest(scheduleId3), crewToken);
        예약을_한다(new ReservationReserveRequest(scheduleId), crewToken);

        Long reservationId = 예약을_한다(
                new ReservationReserveRequest(scheduleId4), crewToken);
        Long reservationId2 = 예약을_한다(
                new ReservationReserveRequest(scheduleId2), crewToken);

        예약을_승인한다(reservationId, new ReservationApproveRequest(true), coachToken);
        예약을_승인한다(reservationId2, new ReservationApproveRequest(true), coachToken);

        String coachToken = 코치의_토큰을_발급한다(coachId);
        RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + coachToken)
                .filter(document("find-coach-reservations", responseFields(
                        subsectionWithPath("beforeApproved").description("승인 전"),
                        subsectionWithPath("approved").description("승인 후"),
                        subsectionWithPath("inProgress").description("면담 중")
                )))
                .when().get("/api/v2/coaches/me/reservations")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("코치가 자신의 히스토리를 조회한다.")
    @Test
    void findOwnReservations() {
        Long schedule1Id = scheduleService.save(coachId, DATE_TIME);
        Long reservation1Id = 예약을_한다(new ReservationReserveRequest(schedule1Id), crewToken);
        예약을_승인한다(reservation1Id, new ReservationApproveRequest(true), coachToken);

        Long schedule2Id = scheduleService.save(coachId, DATE_TIME.minusMinutes(1));
        Long reservation2Id = 예약을_한다(new ReservationReserveRequest(schedule2Id), crewToken);
        예약을_승인한다(reservation2Id, new ReservationApproveRequest(false), coachToken);

        ExtractableResponse<Response> response = RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + coachToken)
                .filter(document("find-own-history"))
                .when().get("/api/v2/coaches/me/history")
                .then().log().all()
                .extract();

        List<CoachFindOwnHistoryResponse> result = response.jsonPath()
                .getList(".", CoachFindOwnHistoryResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(result).hasSize(1)
        );
    }

    @DisplayName("코치가 자신의 프로필을 수정한다.")
    @Test
    void updateProfile() {
        //given, when
        ExtractableResponse<Response> response = RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + coachToken)
                .body(new CoachUpdateProfileRequest("제이슨", "안녕하세요", true))
                .filter(document("coach-update-profile"))
                .when().put("/api/v2/coaches/me/profile")
                .then().log().all()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("코치가 자신의 프로필을 조회한다.")
    @Test
    void getProfile() {
        ExtractableResponse<Response> response = RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + coachToken)
                .filter(document("coach-get-profile"))
                .when().get("/api/v2/coaches/me/profile")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
