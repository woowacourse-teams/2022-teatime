package com.woowacourse.teatime.repository;

import static com.woowacourse.teatime.fixture.DomainFixture.COACH_BROWN;
import static com.woowacourse.teatime.fixture.DomainFixture.CREW;
import static com.woowacourse.teatime.fixture.DomainFixture.DATE_TIME;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.teatime.domain.Coach;
import com.woowacourse.teatime.domain.Crew;
import com.woowacourse.teatime.domain.Reservation;
import com.woowacourse.teatime.domain.Schedule;
import com.woowacourse.teatime.domain.Sheet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class SheetRepositoryTest {

    private Reservation reservation;

    @Autowired
    private SheetRepository sheetRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private CrewRepository crewRepository;
    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @BeforeEach
    void setUp() {
        Crew crew = crewRepository.save(CREW);
        Coach coach = coachRepository.save(COACH_BROWN);
        Schedule schedule = scheduleRepository.save(new Schedule(coach, DATE_TIME));
        reservation = reservationRepository.save(new Reservation(schedule, crew));
    }

    @DisplayName("면담 id에 해당하는 sheet들을 조회한다.")
    @Test
    void findByReservationId() {
        sheetRepository.save(new Sheet(reservation, 1, "이름이 뭔가요?"));
        sheetRepository.save(new Sheet(reservation, 2, "별자리는 뭔가요?"));
        sheetRepository.save(new Sheet(reservation, 3, "핸드폰 기종은요?"));

        List<Sheet> sheets = sheetRepository.findByReservationIdOrderByNumber(reservation.getId());

        assertThat(sheets).hasSize(3);
    }

}
