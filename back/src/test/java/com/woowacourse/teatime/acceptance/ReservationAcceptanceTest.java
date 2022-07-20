package com.woowacourse.teatime.acceptance;

import static com.woowacourse.teatime.fixture.DomainFixture.*;
import static com.woowacourse.teatime.fixture.DtoFixture.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.woowacourse.teatime.controller.dto.CrewIdRequest;
import com.woowacourse.teatime.service.CoachService;
import com.woowacourse.teatime.service.CrewService;
import com.woowacourse.teatime.service.ReservationService;
import com.woowacourse.teatime.service.ScheduleService;

import io.restassured.RestAssured;

public class ReservationAcceptanceTest extends AcceptanceTest {

    @Autowired
    private CoachService coachService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private CrewService crewService;
    @Autowired
    private ReservationService reservationService;

    @DisplayName("예약한다.")
    @Test
    void reserve() {
        Long coachId = coachService.save(COACH_BROWN_SAVE_REQUEST);
        Long scheduleId = scheduleService.save(coachId, DATE_TIME);
        Long crewId = crewService.save();

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", coachId)
                .pathParam("scheduleId", scheduleId)
                .body(new CrewIdRequest(crewId))
                .when().post("/api/coaches/{id}/schedules/{scheduleId}")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("예약을 승인한다.")
    @Test
    void approve() {
        Long coachId = coachService.save(COACH_BROWN_SAVE_REQUEST);
        Long scheduleId = scheduleService.save(coachId, DATE_TIME);
        Long crewId = crewService.save();
        Long reservationId = reservationService.save(crewId, coachId, scheduleId);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", coachId)
                .pathParam("reservationId", reservationId)
                .when().post("/api/coaches/{id}/reservations/{reservationId}")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }
}