package com.woowacourse.teatime.teatime.acceptance;

import static com.woowacourse.teatime.teatime.domain.Role.COACH;
import static com.woowacourse.teatime.teatime.domain.Role.CREW;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

import com.woowacourse.teatime.auth.infrastructure.JwtTokenProvider;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ActiveProfiles("test")
public class AcceptanceTest {

    protected RequestSpecification spec;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Value("${local.server.port}")
    int port;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        RestAssured.port = port;
        this.spec = new RequestSpecBuilder().addFilter(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
    }

    @AfterEach
    public void tearDown() {
        databaseCleaner.execute();
    }

    protected static ExtractableResponse<Response> post(String uri, Object body) {
        return RestAssured.given().log().all()
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(uri)
                .then().log().all()
                .extract();
    }

    protected static ExtractableResponse<Response> postV2(String uri, Object body, String token) {
        return RestAssured.given().log().all()
                .body(body)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(uri)
                .then().log().all()
                .extract();
    }

    protected static ExtractableResponse<Response> get(String uri) {
        return RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(uri)
                .then().log().all()
                .extract();
    }

    protected String 크루의_토큰을_발급한다(Long crewId) {
        Map<String, Object> claims = Map.of("id", crewId, "role", CREW);
        return jwtTokenProvider.createToken(claims);
    }

    protected String 코치의_토큰을_발급한다(Long coachId) {
        Map<String, Object> claims = Map.of("id", coachId, "role", COACH);
        return jwtTokenProvider.createToken(claims);
    }
}
