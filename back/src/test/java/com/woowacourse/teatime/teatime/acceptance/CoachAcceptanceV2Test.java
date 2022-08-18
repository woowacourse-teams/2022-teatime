package com.woowacourse.teatime.teatime.acceptance;

import static com.woowacourse.teatime.teatime.fixture.DomainFixture.COACH_BROWN;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.COACH_BROWN_SAVE_REQUEST;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.COACH_JUNE_SAVE_REQUEST;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.CREW_SAVE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.teatime.teatime.controller.dto.request.CoachSaveRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.ReservationApproveRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.ReservationReserveRequest;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachFindResponse;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.repository.CoachRepository;
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

class CoachAcceptanceV2Test extends AcceptanceTest {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private CrewService crewService;

    private String coachToken;

    @BeforeEach
    void setUp() {
        Coach coach = coachRepository.save(COACH_BROWN);
        coachToken = 코치의_토큰을_발급한다(coach.getId());
        coachRepository.delete(coach);
    }

    @DisplayName("코치 목록을 조회한다.")
    @Test
    void findAll() {
        코치를_저장한다(COACH_BROWN_SAVE_REQUEST, coachToken);
        코치를_저장한다(COACH_JUNE_SAVE_REQUEST, coachToken);

        Long crewId = crewService.save(CREW_SAVE_REQUEST);
        String crewToken = 크루의_토큰을_발급한다(crewId);
        ExtractableResponse<Response> response = RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + crewToken)
                .filter(document("find-all-coaches", responseFields(
                        fieldWithPath("[].id").description("id"),
                        fieldWithPath("[].name").description("이름"),
                        fieldWithPath("[].description").description("소개"),
                        fieldWithPath("[].image").description("이미지")
                )))
                .when().get("/api/v2/coaches")
                .then().log().all()
                .extract();

        List<CoachFindResponse> result = response.jsonPath().getList(".", CoachFindResponse.class);

        assertAll(
                () -> assertThat(result).hasSize(2),
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        );
    }

    @DisplayName("코치를 생성한다.")
    @Test
    void save() {
        Long crewId = crewService.save(CREW_SAVE_REQUEST);
        String crewToken = 크루의_토큰을_발급한다(crewId);
        ExtractableResponse<Response> response = RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + crewToken)
                .body(COACH_BROWN_SAVE_REQUEST)
                .filter(document("create-coach", requestFields(
                        fieldWithPath("name").description("이름"),
                        fieldWithPath("email").description("이메일"),
                        fieldWithPath("description").description("소개"),
                        fieldWithPath("image").description("이미지")
                )))
                .when().post("/api/v2/coaches")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("코치의 면담 목록을 불러온다.")
    @Test
    void findReservations() {
        Long coachId = 코치를_저장한다(COACH_BROWN_SAVE_REQUEST, coachToken);

        Long scheduleId = scheduleService.save(coachId, LocalDateTime.now());
        Long scheduleId2 = scheduleService.save(coachId, LocalDateTime.now().plusDays(1));
        Long scheduleId3 = scheduleService.save(coachId, LocalDateTime.now().plusDays(2));
        Long scheduleId4 = scheduleService.save(coachId, LocalDateTime.now().plusDays(3));

        Long crewId = crewService.save(CREW_SAVE_REQUEST);

        ReservationAcceptanceTest.예약을_한다(new ReservationReserveRequest(crewId, coachId, scheduleId3));
        ReservationAcceptanceTest.예약을_한다(new ReservationReserveRequest(crewId, coachId, scheduleId));

        Long reservationId = ReservationAcceptanceTest.예약을_한다(
                new ReservationReserveRequest(crewId, coachId, scheduleId4));
        Long reservationId2 = ReservationAcceptanceTest.예약을_한다(
                new ReservationReserveRequest(crewId, coachId, scheduleId2));

        ReservationAcceptanceTest.예약을_승인한다(reservationId, new ReservationApproveRequest(coachId, true));
        ReservationAcceptanceTest.예약을_승인한다(reservationId2, new ReservationApproveRequest(coachId, true));

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

    public static Long 코치를_저장한다(CoachSaveRequest request, String token) {
        ExtractableResponse<Response> response = postV2("/api/v2/coaches", request, token);
        return Long.parseLong(response.header("Location").split("/coaches/")[1]);
    }
}