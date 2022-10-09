package com.woowacourse.teatime.teatime.acceptance;

import static com.woowacourse.teatime.teatime.fixture.DtoFixture.COACH_BROWN_SAVE_REQUEST;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.CUSTOM_SHEET_QUESTION_1;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.CUSTOM_SHEET_QUESTION_3;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.CUSTOM_SHEET_QUESTION_4;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.CUSTOM_SHEET_QUESTION_5;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.DEFAULT_SHEET_QUESTION_1;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.DEFAULT_SHEET_QUESTION_2;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.DEFAULT_SHEET_QUESTION_3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.teatime.teatime.controller.dto.request.SheetQuestionUpdateRequest;
import com.woowacourse.teatime.teatime.controller.dto.response.SheetQuestionsResponse;
import com.woowacourse.teatime.teatime.service.CoachService;
import com.woowacourse.teatime.teatime.service.QuestionService;
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

public class QuestionAcceptanceTest extends AcceptanceTestSupporter {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CoachService coachService;

    private Long coachId;
    private String coachToken;

    @BeforeEach
    void setUp() {
        coachId = coachService.save(COACH_BROWN_SAVE_REQUEST);
        coachToken = 코치의_토큰을_발급한다(coachId);
    }

    @DisplayName("코치가 자신의 시트 질문을 조회한다.")
    @Test
    void get() {
        //given
        디폴트_질문을_생성한다();

        // when
        ExtractableResponse<Response> response = RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + coachToken)
                .filter(document("find-questions"))
                .when().get("/api/v2/questions")
                .then().log().all()
                .extract();
        //then
        SheetQuestionsResponse result = response.as(SheetQuestionsResponse.class);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(result.getQuestions()).hasSize(3)
        );
    }

    @DisplayName("코치가 자신의 시트 질문을 수정한다.")
    @Test
    void update() {
        //given
        디폴트_질문을_생성한다();

        //when
        List<SheetQuestionUpdateRequest> request =
                List.of(CUSTOM_SHEET_QUESTION_1, DEFAULT_SHEET_QUESTION_2, CUSTOM_SHEET_QUESTION_3,
                        CUSTOM_SHEET_QUESTION_4, CUSTOM_SHEET_QUESTION_5);
        ExtractableResponse<Response> response = RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + coachToken)
                .body(request)
                .filter(document("update-questions"))
                .when().put("/api/v2/questions")
                .then().log().all()
                .extract();

        //then
        SheetQuestionsResponse result = 코치의_질문을_조회한다();
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(result.getQuestions()).hasSize(5)
        );
    }

    private void 디폴트_질문을_생성한다() {
        List<SheetQuestionUpdateRequest> request =
                List.of(DEFAULT_SHEET_QUESTION_1, DEFAULT_SHEET_QUESTION_2, DEFAULT_SHEET_QUESTION_3);
        RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + coachToken)
                .body(request)
                .when().put("/api/v2/questions")
                .then().log().all()
                .extract();
    }

    private SheetQuestionsResponse 코치의_질문을_조회한다() {
        return RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + coachToken)
                .filter(document("find-questions"))
                .when().get("/api/v2/questions")
                .then().log().all()
                .extract()
                .as(SheetQuestionsResponse.class);
    }
}
