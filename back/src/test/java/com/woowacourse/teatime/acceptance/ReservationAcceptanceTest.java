package com.woowacourse.teatime.acceptance;

import static com.woowacourse.teatime.fixture.DomainFixture.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.woowacourse.teatime.controller.dto.CrewIdRequest;
import com.woowacourse.teatime.domain.Coach;
import com.woowacourse.teatime.domain.Crew;
import com.woowacourse.teatime.domain.Reservation;
import com.woowacourse.teatime.domain.Schedule;
import com.woowacourse.teatime.repository.CoachRepository;
import com.woowacourse.teatime.repository.CrewRepository;
import com.woowacourse.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.repository.ScheduleRepository;

import io.restassured.RestAssured;

public class ReservationAcceptanceTest extends AcceptanceTest {

    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private CrewRepository crewRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @DisplayName("예약한다.")
    @Test
    void reserve() {
        Coach coach = coachRepository.save(COACH_BROWN);
        Schedule schedule = scheduleRepository.save(new Schedule(coach, DATE_TIME));
        Crew crew = crewRepository.save(CREW);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", coach.getId())
                .pathParam("scheduleId", schedule.getId())
                .body(new CrewIdRequest(crew.getId()))
                .when().post("/api/coaches/{id}/schedules/{scheduleId}")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("예약을 승인한다.")
    @Test
    void approve() {
        Coach coach = coachRepository.save(COACH_BROWN);
        Schedule schedule = scheduleRepository.save(new Schedule(coach, DATE_TIME));
        Crew crew = crewRepository.save(CREW);
        Reservation reservation = reservationRepository.save(new Reservation(schedule, crew));

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", coach.getId())
                .pathParam("reservationId", reservation.getId())
                .when().post("/api/coaches/{id}/reservations/{reservationId}")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }
}
