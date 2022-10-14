package com.woowacourse.teatime.auth.acceptance;

import static com.woowacourse.teatime.teatime.domain.Role.CREW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.teatime.auth.domain.UserAuthInfo;
import com.woowacourse.teatime.auth.repository.UserAuthInfoRepository;
import com.woowacourse.teatime.auth.service.UserAuthService;
import com.woowacourse.teatime.teatime.acceptance.AcceptanceTestSupporter;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@ExtendWith({MockitoExtension.class})
public class AuthAcceptanceTest extends AcceptanceTestSupporter {

    @Mock
    private UserAuthInfoRepository userAuthInfoRepository;

    @Autowired
    @InjectMocks
    private UserAuthService userAuthService;

    @BeforeEach
    void setUp() {
        UserAuthInfo userAuthInfo = new UserAuthInfo("refreshToken", "accessToken", 1L, CREW.name());
        userAuthService.save(userAuthInfo);
    }

    @DisplayName("토큰을 재발급한다.")
    @Test
    void generateToken() {
        ExtractableResponse<Response> response = RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + "accessToken")
                .cookies("refreshToken", "refreshToken")
                .filter(document("generate-new-token"))
                .when().get("/api/auth/refresh-token")
                .then().log().all()
                .extract();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        );
    }
}
