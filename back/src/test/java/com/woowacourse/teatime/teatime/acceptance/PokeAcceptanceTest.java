package com.woowacourse.teatime.teatime.acceptance;

import static com.woowacourse.teatime.teatime.fixture.DtoFixture.COACH_BROWN_SAVE_REQUEST;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.CREW_SAVE_REQUEST;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.teatime.teatime.controller.dto.request.PokeSaveRequest;
import com.woowacourse.teatime.teatime.service.CoachService;
import com.woowacourse.teatime.teatime.service.CrewService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class PokeAcceptanceTest extends AcceptanceTestSupporter {

    @Autowired
    private CoachService coachService;

    @Autowired
    private CrewService crewService;

    private Long coachId;
    private Long crewId;
    private String crewToken;

    @BeforeEach
    void setUp() {
        coachId = coachService.save(COACH_BROWN_SAVE_REQUEST);
        crewId = crewService.save(CREW_SAVE_REQUEST);
        crewToken = 크루의_토큰을_발급한다(crewId);
    }

    @DisplayName("티타임을 요청한다.")
    @Test
    void save() {
        RestAssured.given(super.spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + crewToken)
                .body(new PokeSaveRequest(coachId))
                .filter(document("poke"))
                .when().post("/api/v2/pokes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }
}
