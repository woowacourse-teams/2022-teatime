package com.woowacourse.teatime.teatime.service;

import static com.woowacourse.teatime.teatime.fixture.DomainFixture.COACH_BROWN;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.DATE_TIME;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.teatime.teatime.controller.dto.request.ReservationReserveRequest;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.ReservationStatus;
import com.woowacourse.teatime.teatime.domain.Schedule;
import com.woowacourse.teatime.teatime.repository.CoachRepository;
import com.woowacourse.teatime.teatime.repository.CrewRepository;
import com.woowacourse.teatime.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.teatime.repository.ScheduleRepository;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReservationServiceSyncTest {
    private Coach coach;
    private Schedule schedule;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private CrewRepository crewRepository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @BeforeEach
    void setUp() {
        coach = coachRepository.save(COACH_BROWN);
        schedule = scheduleRepository.save(new Schedule(coach, DATE_TIME));
    }

    @Disabled
    @DisplayName("동시성 테스트: 다른 트랜잭션에 동시에 예약할 때, 예약이 하나만 생성되도록 한다.")
    @Test
    void reserve_checkSynchronization() throws InterruptedException {
        //given
        int testNumber = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(testNumber);
        CountDownLatch latch = new CountDownLatch(testNumber);

        //각기 다른 크루 20명 생성
        for (int i = 1; i < testNumber + 1; i++) {
            final String info = String.valueOf(i);
            crewRepository.save(new Crew(
                    "slackID" + info,
                    "name" + info,
                    "email" + info,
                    "image" + info)
            );
        }
        final List<Crew> crews = crewRepository.findAll();
        assertThat(crews).hasSize(testNumber);
        ReservationReserveRequest reservationReserveRequest = new ReservationReserveRequest(schedule.getId());

        //when
        for (int i = 0; i < testNumber; i++) {
            Crew crew = crews.get(i);
            executorService.execute(() -> {
                try {
                    reservationService.save(crew.getId(), reservationReserveRequest);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                latch.countDown();
            });
        }
        latch.await();

        //then
        assertThat(reservationRepository.findAllByCoachIdAndStatus(coach.getId(), ReservationStatus.BEFORE_APPROVED))
                .hasSize(1);
    }
}
