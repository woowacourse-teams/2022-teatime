package com.woowacourse.teatime.teatime.acceptance;

import static com.woowacourse.teatime.teatime.domain.Role.COACH;
import static com.woowacourse.teatime.teatime.domain.Role.CREW;
import static org.springframework.restdocs.http.HttpDocumentation.httpRequest;
import static org.springframework.restdocs.http.HttpDocumentation.httpResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.removeHeaders;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

import com.woowacourse.teatime.auth.infrastructure.JwtTokenProvider;
import com.woowacourse.teatime.teatime.support.AcceptanceTest;
import com.woowacourse.teatime.teatime.support.DatabaseSupporter;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;

@AcceptanceTest
public class AcceptanceTestSupporter extends DatabaseSupporter {

    protected RequestSpecification spec;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Value("${local.server.port}")
    int port;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider contextProvider) {
        RestAssured.port = port;
        setRestDocsSpec(contextProvider);
    }

    private void setRestDocsSpec(final RestDocumentationContextProvider contextProvider) {
        this.spec = new RequestSpecBuilder()
                .addFilter(
                        documentationConfiguration(contextProvider)
                                .snippets()
                                .withDefaults(httpRequest(), httpResponse())
                                .and()
                                .operationPreprocessors()
                                .withRequestDefaults(
                                        modifyUris()
                                                .scheme("https")
                                                .host("api.teatime.pe.kr")
                                                .removePort(),
                                        prettyPrint()
                                ).withResponseDefaults(
                                removeHeaders(
                                        "Transfer-Encoding",
                                        "Date",
                                        "Keep-Alive",
                                        "Connection"),
                                prettyPrint()
                        )
                ).build();
    }

    protected static ExtractableResponse<Response> get(String uri, String token) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(uri)
                .then().log().all()
                .extract();
    }

    protected static ExtractableResponse<Response> get(String uri, String token, Map<String, Object> pathParams, Map<String, Object> queryParams) {
        return RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .pathParams(pathParams)
                .queryParams(queryParams)
                .header("Authorization", "Bearer " + token)
                .when()
                .get(uri)
                .then().log().all()
                .extract();
    }

    protected static ExtractableResponse<Response> post(String uri, Object body, String token) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + token)
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(uri)
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
