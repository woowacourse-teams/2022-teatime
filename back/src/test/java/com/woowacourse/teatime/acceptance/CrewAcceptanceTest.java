package com.woowacourse.teatime.acceptance;

import static com.woowacourse.teatime.acceptance.CoachAcceptanceTest.코치를_저장한다;
import static com.woowacourse.teatime.acceptance.CoachAcceptanceTest.코치의_면담목록을_불러온다;
import static com.woowacourse.teatime.acceptance.ReservationAcceptanceTest.예약을_승인한다;
import static com.woowacourse.teatime.acceptance.ReservationAcceptanceTest.예약을_완료한다;
import static com.woowacourse.teatime.acceptance.ReservationAcceptanceTest.예약을_한다;
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

    private Long coachId;
    private Long scheduleId;
    private Long crewId;

    @BeforeEach
    void setUp() {
        coachId = 코치를_저장한다(COACH_BROWN_SAVE_REQUEST);
        scheduleId = scheduleService.save(coachId, DATE_TIME);
        crewId = crewService.save(CREW_SAVE_REQUEST);
    }

    @DisplayName("크루가 자신의 히스토리를 조회한다.")
    @Test
    void findOwnReservations() {
        예약을_한다(new ReservationReserveRequest(crewId, coachId, scheduleId));

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
        Long reservationId = 예약을_한다(new ReservationReserveRequest(crewId, coachId, scheduleId));
        예약을_승인한다(reservationId, new ReservationApproveRequest(coachId, true));
        코치의_면담목록을_불러온다(coachId);
        예약을_완료한다(reservationId);

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
    void findOwnSheet() {
        Coach coach = coachRepository.findById(coachId).get();
        questionRepository.save(new Question(coach, 1, "이름이 뭔가요?"));
        questionRepository.save(new Question(coach, 2, "별자리가 뭔가요?"));
        questionRepository.save(new Question(coach, 3, "mbti는 뭔가요?"));
        Long reservationId = 예약을_한다(new ReservationReserveRequest(crewId, coachId, scheduleId));
        예약을_승인한다(reservationId, new ReservationApproveRequest(coachId, true));

        ExtractableResponse<Response> response = RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("reservationId", reservationId)
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
        Coach coach = coachRepository.findById(coachId).get();
        questionRepository.save(new Question(coach, 1, "이름이 뭔가요?"));
        questionRepository.save(new Question(coach, 2, "별자리가 뭔가요?"));
        questionRepository.save(new Question(coach, 3, "mbti는 뭔가요?"));
        Long reservationId = 예약을_한다(new ReservationReserveRequest(crewId, coachId, scheduleId));
        예약을_승인한다(reservationId, new ReservationApproveRequest(coachId, true));

        ExtractableResponse<Response> response = RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("crewId", crewId)
                .pathParam("reservationId", reservationId)
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
}
