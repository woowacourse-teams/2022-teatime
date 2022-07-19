package com.woowacourse.teatime.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
        CoachSaveRequest request1 = new CoachSaveRequest("brown", "i am legend", "image");
        CoachSaveRequest request2 = new CoachSaveRequest("june", "i am legend", "image");
        coachService.save(request1);
        coachService.save(request2);
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/coaches")
                .then().log().all()
                .extract();

        List<CoachResponse> result = new ArrayList<>(response.jsonPath().getList(".", CoachResponse.class));

        assertAll(
                () -> assertThat(result).hasSize(2),
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        );
    }
}
