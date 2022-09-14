package com.woowacourse.teatime.teatime.service;

import static com.woowacourse.teatime.teatime.service.AlarmMessage.REMIND_COACH;
import static com.woowacourse.teatime.teatime.service.AlarmMessage.REMIND_CREW;
import static com.woowacourse.teatime.util.Date.findFirstTime;

import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.infrastructure.Alarm;
import com.woowacourse.teatime.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.util.Date;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AlarmService {

    private final Alarm alarm;
    private final ReservationRepository reservationRepository;

    public void remindReservation() {
        LocalDate date = LocalDate.now().plusDays(1);
        List<Reservation> reservations = reservationRepository.findAllApprovedReservationsBetween(
                findFirstTime(date), Date.findLastTime(date));

        for (Reservation reservation : reservations) {
            Coach coach = reservation.getCoach();
            Crew crew = reservation.getCrew();

            String coachMessage = REMIND_COACH.getMessage(crew.getName(), coach.getName(),
                    reservation.getScheduleDateTime());
            alarm.sendMessage(coach.getSlackId(), coachMessage);

            String crewMessage = REMIND_CREW.getMessage(crew.getName(), coach.getName(),
                    reservation.getScheduleDateTime());
            alarm.sendMessage(crew.getSlackId(), crewMessage);
        }
    }
}
