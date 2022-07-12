package com.woowacourse.teatime.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.teatime.controller.dto.ScheduleResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ScheduleAcceptanceTest extends AcceptanceTest {

    @DisplayName("코치에 해당하는 스케쥴 목록을 조회한다.")
    @Test
    void findByCoachIdAndDate() {
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("year", 2022)
                .queryParam("month", 7)
                .pathParam("id", 1)
                .when().get("/api/coaches/{id}/schedules")
                .then().log().all()
                .extract();

        List<ScheduleResponse> result = response.jsonPath().getList(".", ScheduleResponse.class);

        assertAll(
                () -> assertThat(result).hasSize(2),
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        );
    }

}
