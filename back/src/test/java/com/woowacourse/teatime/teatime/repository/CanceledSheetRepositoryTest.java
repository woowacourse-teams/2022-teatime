package com.woowacourse.teatime.teatime.repository;

import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getCoachJason;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getCrew;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.teatime.teatime.domain.CanceledReservation;
import com.woowacourse.teatime.teatime.domain.CanceledSheet;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.domain.Schedule;
import com.woowacourse.teatime.teatime.domain.Sheet;
import com.woowacourse.teatime.teatime.fixture.DomainFixture;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CanceledSheetRepositoryTest {

    private Crew crew;
    private Coach coach;
    private Schedule schedule;
    private Reservation reservation;

    @Autowired
    private CanceledSheetRepository canceledSheetRepository;
    @Autowired
    private CanceledReservationRepository canceledReservationRepository;
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
        crew = crewRepository.save(getCrew());
        coach = coachRepository.save(getCoachJason());
        schedule = scheduleRepository.save(new Schedule(coach, DomainFixture.DATE_TIME));

        reservation = reservationRepository.save(new Reservation(schedule, crew));
        sheetRepository.save(new Sheet(reservation, 1, "이름이 뭔가요?"));
        sheetRepository.save(new Sheet(reservation, 2, "별자리는 뭔가요?"));
        sheetRepository.save(new Sheet(reservation, 3, "핸드폰 기종은요?"));
    }

    @Test
    void findByOriginId() {
        List<Sheet> sheets = sheetRepository.findAllByReservationIdOrderByNumber(reservation.getId());
        CanceledReservation canceledReservation = CanceledReservation.from(reservation);
        canceledReservationRepository.save(canceledReservation);

        for (Sheet sheet : sheets) {
            canceledSheetRepository.save(CanceledSheet.from(canceledReservation, sheet));
        }

        assertThat(canceledSheetRepository.findAllByOriginId(reservation.getId())).hasSize(3);
    }
}
