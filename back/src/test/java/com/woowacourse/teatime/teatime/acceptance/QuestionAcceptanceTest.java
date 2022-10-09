package com.woowacourse.teatime.teatime.acceptance;

import static com.woowacourse.teatime.teatime.fixture.DtoFixture.COACH_BROWN_SAVE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.teatime.teatime.controller.dto.response.SheetQuestionsResponse;
import com.woowacourse.teatime.teatime.service.CoachService;
import com.woowacourse.teatime.teatime.service.QuestionService;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
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
        //given, when
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
                () -> assertThat(result.getQuestions()).isEmpty()
        );
    }
}
