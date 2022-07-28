package com.woowacourse.teatime.acceptance;

import static com.woowacourse.teatime.fixture.DtoFixture.COACH_BROWN_SAVE_REQUEST;
import static com.woowacourse.teatime.fixture.DtoFixture.COACH_JUNE_SAVE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.teatime.controller.dto.CoachResponse;
import com.woowacourse.teatime.controller.dto.CoachSaveRequest;
import com.woowacourse.teatime.service.CoachService;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class CoachAcceptanceTest extends AcceptanceTest {

    @Autowired
    CoachService coachService;

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

        List<CoachResponse> result = new ArrayList<>(response.jsonPath().getList(".", CoachResponse.class));

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
}
