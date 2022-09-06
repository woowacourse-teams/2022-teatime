package com.woowacourse.teatime.teatime.acceptance;

import static com.woowacourse.teatime.teatime.acceptance.ReservationAcceptanceTest.예약을_승인한다;
import static com.woowacourse.teatime.teatime.acceptance.ReservationAcceptanceTest.예약을_한다;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.COACH_BROWN_SAVE_REQUEST;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.COACH_JUNE_SAVE_REQUEST;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.CREW_SAVE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.teatime.teatime.controller.dto.request.ReservationApproveRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.ReservationReserveRequest;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachFindResponse;
import com.woowacourse.teatime.teatime.service.CoachService;
import com.woowacourse.teatime.teatime.service.CrewService;
import com.woowacourse.teatime.teatime.service.ScheduleService;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

class CoachAcceptanceTest extends AcceptanceTestSupporter {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private CoachService coachService;

    @Autowired
    private CrewService crewService;

    @DisplayName("코치 목록을 조회한다.")
    @Test
    void findAll() {
        //given
        coachService.save(COACH_BROWN_SAVE_REQUEST);
        coachService.save(COACH_JUNE_SAVE_REQUEST);
        Long crewId = crewService.save(CREW_SAVE_REQUEST);
        String crewToken = 크루의_토큰을_발급한다(crewId);

        //when
//        ExtractableResponse<Response> response = RestAssured.given(super.spec).log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .header("Authorization", "Bearer " + crewToken)
//                .filter(document("find-all-coaches", responseFields(
//                        fieldWithPath("[].id").description("id"),
//                        fieldWithPath("[].name").description("이름"),
//                        fieldWithPath("[].description").description("소개"),
//                        fieldWithPath("[].image").description("이미지")
//                )))
//                .when().get("/api/v2/coaches")
//                .then().log().all()
//                .extract();

        ExtractableResponse<Response> response = get("/api/v2/coaches", crewToken);

        //then
        List<CoachFindResponse> result = response.jsonPath().getList(".", CoachFindResponse.class);
        assertAll(
                () -> assertThat(result).hasSize(2),
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        );
    }

    @DisplayName("코치의 면담 목록을 불러온다.")
    @Test
    void findReservations() {
        //given
        Long coachId = coachService.save(COACH_BROWN_SAVE_REQUEST);
        String coachToken = 코치의_토큰을_발급한다(coachId);

        Long scheduleId = scheduleService.save(coachId, LocalDateTime.now());
        Long scheduleId2 = scheduleService.save(coachId, LocalDateTime.now().plusDays(1));
        Long scheduleId3 = scheduleService.save(coachId, LocalDateTime.now().plusDays(2));
        Long scheduleId4 = scheduleService.save(coachId, LocalDateTime.now().plusDays(3));

        Long crewId = crewService.save(CREW_SAVE_REQUEST);
        String crewToken = 크루의_토큰을_발급한다(crewId);

        Long reservationId = 예약을_한다(new ReservationReserveRequest(scheduleId4), crewToken);
        Long reservationId2 = 예약을_한다(new ReservationReserveRequest(scheduleId2), crewToken);
        예약을_한다(new ReservationReserveRequest(scheduleId3), crewToken);
        예약을_한다(new ReservationReserveRequest(scheduleId), crewToken);
        예약을_승인한다(reservationId, new ReservationApproveRequest(true), coachToken);
        예약을_승인한다(reservationId2, new ReservationApproveRequest(true), coachToken);

//        RestAssured.given(super.spec).log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .header("Authorization", "Bearer " + coachToken)
//                .filter(document("find-coach-reservations", responseFields(
//                        subsectionWithPath("beforeApproved").description("승인 전"),
//                        subsectionWithPath("approved").description("승인 후"),
//                        subsectionWithPath("inProgress").description("면담 중")
//                )))
//                .when().get("/api/v2/coaches/me/reservations")
//                .then().log().all()
//                .statusCode(HttpStatus.OK.value());
        //when
        ExtractableResponse<Response> response = get("/api/v2/coaches/me/reservations", coachToken);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 코치의_면담목록을_불러온다(String token) {
        get("/api/v2/coaches/me/reservations", token);
    }
}
