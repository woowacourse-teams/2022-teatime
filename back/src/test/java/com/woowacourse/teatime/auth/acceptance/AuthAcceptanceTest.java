package com.woowacourse.teatime.auth.acceptance;

import static com.woowacourse.teatime.teatime.domain.Role.CREW;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.CREW_SAVE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.teatime.auth.domain.UserAuthInfo;
import com.woowacourse.teatime.auth.service.UserAuthService;
import com.woowacourse.teatime.teatime.acceptance.AcceptanceTestSupporter;
import com.woowacourse.teatime.teatime.service.CrewService;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class AuthAcceptanceTest extends AcceptanceTestSupporter {

    @Autowired
    private CrewService crewService;
    @Autowired
    private UserAuthService userAuthService;

    private String accessToken;
    private String refreshToken;

    @BeforeEach
    void setUp() {
        Long crewId = crewService.save(CREW_SAVE_REQUEST);
        accessToken = 크루의_토큰을_발급한다(crewId);
        refreshToken = UUID.randomUUID().toString();
        userAuthService.save(new UserAuthInfo(refreshToken, accessToken, 1L, CREW.name()));
    }

    @DisplayName("토큰을 재발급한다.")
    @Test
    void generateToken() {
        ExtractableResponse<Response> response = RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .cookie("refreshToken", refreshToken)
                .filter(document("generate-new-token"))
                .when().get("/api/auth/refresh-token")
                .then().log().all()
                .extract();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        );
    }
}
