package com.woowacourse.teatime.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.teatime.controller.dto.CrewIdRequest;
import com.woowacourse.teatime.domain.Coach;
import com.woowacourse.teatime.domain.Crew;
import com.woowacourse.teatime.domain.Schedule;
import com.woowacourse.teatime.repository.CoachRepository;
import com.woowacourse.teatime.repository.CrewRepository;
import com.woowacourse.teatime.repository.ScheduleRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ReservationAcceptanceTest extends AcceptanceTest {

    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private CrewRepository crewRepository;

    @DisplayName("예약한다.")
    @Test
    void reserve() {
        Coach coach = coachRepository.save(new Coach("brown", "i am legend", "image"));
        Schedule schedule = scheduleRepository.save(new Schedule(coach, LocalDateTime.of(2022, 7, 1, 1, 30, 0)));
        Crew crew = crewRepository.save(new Crew());

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", coach.getId())
                .pathParam("scheduleId", schedule.getId())
                .body(new CrewIdRequest(crew.getId()))
                .when().post("/api/coaches/{id}/schedules/{scheduleId}")
                .then().log().all()
                .extract();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
        );
    }
}
