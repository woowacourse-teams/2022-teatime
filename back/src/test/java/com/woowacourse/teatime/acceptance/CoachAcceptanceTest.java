package com.woowacourse.teatime.acceptance;

import static com.woowacourse.teatime.fixture.DtoFixture.COACH_BROWN_SAVE_REQUEST;
import static com.woowacourse.teatime.fixture.DtoFixture.COACH_JUNE_SAVE_REQUEST;
import static com.woowacourse.teatime.fixture.DtoFixture.CREW_SAVE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.teatime.controller.dto.request.CoachReservationsRequest;
import com.woowacourse.teatime.controller.dto.request.CoachSaveRequest;
import com.woowacourse.teatime.controller.dto.request.ReservationApproveRequest;
import com.woowacourse.teatime.controller.dto.request.ReservationReserveRequest;
import com.woowacourse.teatime.controller.dto.response.CoachFindResponse;
import com.woowacourse.teatime.service.CoachService;
import com.woowacourse.teatime.service.CrewService;
import com.woowacourse.teatime.service.ReservationService;
import com.woowacourse.teatime.service.ScheduleService;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class CoachAcceptanceTest extends AcceptanceTest {

    @Autowired
    CoachService coachService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private CrewService crewService;
    @Autowired
    private ReservationService reservationService;

    @DisplayName("코치 목록을 조회한다.")
    @Test
    void findAll() {
        coachService.save(COACH_BROWN_SAVE_REQUEST);
        coachService.save(COACH_JUNE_SAVE_REQUEST);
        ExtractableResponse<Response> response = RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(document("find-all-coaches", responseFields(
                        fieldWithPath("[].id").description("id"),
                        fieldWithPath("[].name").description("이름"),
                        fieldWithPath("[].description").description("소개"),
                        fieldWithPath("[].image").description("이미지")
                )))
                .when().get("/api/coaches")
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
        ExtractableResponse<Response> response = RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CoachSaveRequest("brown", "I am a legend", "image"))
                .filter(document("create-coach", requestFields(
                        fieldWithPath("name").description("이름"),
                        fieldWithPath("description").description("소개"),
                        fieldWithPath("image").description("이미지")
                )))
                .when().post("/api/coaches")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("코치의 한 달 면담 목록을 불러온다.")
    @Test
    void findReservations() {
        Long coachId = coachService.save(COACH_BROWN_SAVE_REQUEST);

        Long scheduleId = scheduleService.save(coachId, LocalDateTime.now());
        Long scheduleId2 = scheduleService.save(coachId, LocalDateTime.now().plusDays(1));
        Long scheduleId3 = scheduleService.save(coachId, LocalDateTime.now().plusDays(2));
        Long scheduleId4 = scheduleService.save(coachId, LocalDateTime.now().plusDays(3));

        Long crewId = crewService.save(CREW_SAVE_REQUEST);

        reservationService.save(new ReservationReserveRequest(crewId, coachId, scheduleId3));
        reservationService.save(new ReservationReserveRequest(crewId, coachId, scheduleId));

        Long reservationId = reservationService.save(new ReservationReserveRequest(crewId, coachId, scheduleId4));
        Long reservationId2 = reservationService.save(new ReservationReserveRequest(crewId, coachId, scheduleId2));

        reservationService.approve(reservationId, new ReservationApproveRequest(coachId, true));
        reservationService.approve(reservationId2, new ReservationApproveRequest(coachId, true));

        ExtractableResponse<Response> response = RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CoachReservationsRequest(coachId))
                .filter(document("find-coach-reservations", responseFields(
                        subsectionWithPath("beforeApproved").description("승인 전"),
                        subsectionWithPath("approved").description("승인 후"),
                        subsectionWithPath("inProgress").description("면담 중")
                )))
                .when().get("/api/coaches/me/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
