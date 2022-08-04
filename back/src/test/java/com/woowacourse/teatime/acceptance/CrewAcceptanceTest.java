package com.woowacourse.teatime.acceptance;

import static com.woowacourse.teatime.domain.SheetStatus.WRITING;
import static com.woowacourse.teatime.fixture.DomainFixture.DATE_TIME;
import static com.woowacourse.teatime.fixture.DtoFixture.COACH_BROWN_SAVE_REQUEST;
import static com.woowacourse.teatime.fixture.DtoFixture.CREW_SAVE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.teatime.controller.dto.request.ReservationApproveRequest;
import com.woowacourse.teatime.controller.dto.request.ReservationReserveRequest;
import com.woowacourse.teatime.controller.dto.response.CoachFindCrewHistoryResponse;
import com.woowacourse.teatime.controller.dto.response.CrewFindOwnHistoryResponse;
import com.woowacourse.teatime.controller.dto.response.SheetDto;
import com.woowacourse.teatime.domain.Coach;
import com.woowacourse.teatime.domain.Question;
import com.woowacourse.teatime.domain.SheetStatus;
import com.woowacourse.teatime.repository.CoachRepository;
import com.woowacourse.teatime.repository.QuestionRepository;
import com.woowacourse.teatime.service.CrewService;
import com.woowacourse.teatime.service.ScheduleService;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class CrewAcceptanceTest extends AcceptanceTest {

    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private CrewService crewService;
    @Autowired
    private QuestionRepository questionRepository;

    private Coach coach;
    private Long scheduleId;
    private Long crewId;

    @BeforeEach
    void setUp() {
        코치를_저장한다();
        coach = coachRepository.findAll().get(0);
        scheduleId = scheduleService.save(coach.getId(), DATE_TIME);
        crewId = crewService.save(CREW_SAVE_REQUEST);
    }

    @DisplayName("크루가 자신의 히스토리를 조회한다.")
    @Test
    void findOwnReservations() {
        면담_예약_요청됨(coach.getId(), scheduleId, crewId);

        ExtractableResponse<Response> response = RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(crewId)
                .filter(document("find-own-reservations", responseFields(
                        fieldWithPath("[].reservationId").description("면담 아이디"),
                        fieldWithPath("[].coachName").description("코치 이름"),
                        fieldWithPath("[].coachImage").description("코치 이미지"),
                        fieldWithPath("[].dateTime").description("날짜"),
                        fieldWithPath("[].status").description("상태")
                )))
                .when().get("/api/crews/me/reservations")
                .then().log().all()
                .extract();

        List<CrewFindOwnHistoryResponse> result = response.jsonPath()
                .getList(".", CrewFindOwnHistoryResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(result).hasSize(1)
        );
    }

    @DisplayName("코치가 크루의 히스토리를 조회한다.")
    @Test
    void findCrewReservations() {
        면담_예약_요청됨(coach.getId(), scheduleId, crewId);
        ExtractableResponse<Response> coachReservationFindResponse = 코치의_면담_목록을_조회한다(coach.getId());
        List<Long> reservationIds_beforeApproved
                = coachReservationFindResponse.jsonPath().getList("beforeApproved.reservationId", Long.class);
        승인을_한다(coach.getId(), reservationIds_beforeApproved.get(0));
        코치의_면담_목록을_조회한다(coach.getId());
        완료를_한다(reservationIds_beforeApproved.get(0));

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

        List<CoachFindCrewHistoryResponse> result = response.jsonPath()
                .getList(".", CoachFindCrewHistoryResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(result).hasSize(1)
        );
    }

    @DisplayName("크루가 자신의 면담 시트 하나를 조회한다.")
    @Test
    void findOwnSheets() {
        questionRepository.save(new Question(coach, 1, "이름이 뭔가요?"));
        questionRepository.save(new Question(coach, 2, "별자리가 뭔가요?"));
        questionRepository.save(new Question(coach, 3, "mbti는 뭔가요?"));
        면담_예약_요청됨(coach.getId(), scheduleId, crewId);
        ExtractableResponse<Response> coachReservationFindResponse = 코치의_면담_목록을_조회한다(coach.getId());
        List<Long> reservationIds_beforeApproved
                = coachReservationFindResponse.jsonPath().getList("beforeApproved.reservationId", Long.class);
        승인을_한다(coach.getId(), reservationIds_beforeApproved.get(0));

        ExtractableResponse<Response> response = RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("reservationId", reservationIds_beforeApproved.get(0))
                .filter(document("find-own-sheets", responseFields(
                        fieldWithPath("dateTime").description("날짜"),
                        fieldWithPath("coachName").description("코치 이름"),
                        fieldWithPath("coachImage").description("코치 이미지"),
                        fieldWithPath("sheets[].questionNumber").description("질문 번호"),
                        fieldWithPath("sheets[].questionContent").description("질문 내용"),
                        fieldWithPath("sheets[].answerContent").description("답변 내용")
                )))
                .when().get("/api/crews/me/reservations/{reservationId}")
                .then().log().all()
                .extract();

        List<SheetDto> result = response.jsonPath().getList("sheets.", SheetDto.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(result).hasSize(3)
        );
    }

    @DisplayName("코치가 크루의 면담 시트 하나를 조회한다.")
    @Test
    void findCrewSheets() {
        questionRepository.save(new Question(coach, 1, "이름이 뭔가요?"));
        questionRepository.save(new Question(coach, 2, "별자리가 뭔가요?"));
        questionRepository.save(new Question(coach, 3, "mbti는 뭔가요?"));
        면담_예약_요청됨(coach.getId(), scheduleId, crewId);
        ExtractableResponse<Response> coachReservationFindResponse = 코치의_면담_목록을_조회한다(coach.getId());
        List<Long> reservationIds_beforeApproved
                = coachReservationFindResponse.jsonPath().getList("beforeApproved.reservationId", Long.class);
        승인을_한다(coach.getId(), reservationIds_beforeApproved.get(0));

        ExtractableResponse<Response> response = RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("crewId", crewId)
                .pathParam("reservationId", reservationIds_beforeApproved.get(0))
                .filter(document("find-crew-sheets", responseFields(
                        fieldWithPath("dateTime").description("날짜"),
                        fieldWithPath("coachName").description("코치 이름"),
                        fieldWithPath("coachImage").description("코치 이미지"),
                        fieldWithPath("status").description("시트 상태"),
                        fieldWithPath("sheets[].questionNumber").description("질문 번호"),
                        fieldWithPath("sheets[].questionContent").description("질문 내용"),
                        fieldWithPath("sheets[].answerContent").description("답변 내용")
                )))
                .when().get("/api/crews/{crewId}/reservations/{reservationId}")
                .then().log().all()
                .extract();

        String sheetStatus = response.jsonPath().getObject("status", String.class);
        List<SheetDto> sheets = response.jsonPath().getList("sheets.", SheetDto.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(sheetStatus).isEqualTo(WRITING.name()),
                () -> assertThat(sheets).hasSize(3)
        );
    }

    private ExtractableResponse<Response> 코치를_저장한다() {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(COACH_BROWN_SAVE_REQUEST)
                .when().post("/api/coaches")
                .then().log().all()
                .extract();
    }

    private void 완료를_한다(Long reservationId) {
        RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("reservationId", reservationId)
                .when().put("/api/reservations/{reservationId}")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    private void 면담_예약_요청됨(Long coachId, Long scheduleId, Long crewId) {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ReservationReserveRequest(crewId, coachId, scheduleId))
                .when().post("/api/reservations")
                .then().log().all();
    }

    private ExtractableResponse<Response> 승인을_한다(Long coachId, Long reservationId) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("reservationId", reservationId)
                .body(new ReservationApproveRequest(coachId, true))
                .when().post("/api/reservations/{reservationId}")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 코치의_면담_목록을_조회한다(Long coachId) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("coachId", coachId)
                .when().get("/api/coaches/me/reservations")
                .then().log().all()
                .extract();
    }
}
