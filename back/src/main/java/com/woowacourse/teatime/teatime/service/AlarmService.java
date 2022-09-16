package com.woowacourse.teatime.teatime.service;

import static com.woowacourse.teatime.teatime.service.AlarmTitle.REMIND_COACH;
import static com.woowacourse.teatime.teatime.service.AlarmTitle.REMIND_CREW;
import static com.woowacourse.teatime.util.Date.findLastTime;

import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.infrastructure.Alarm;
import com.woowacourse.teatime.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.teatime.service.dto.AlarmDto;
import com.woowacourse.teatime.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public void send(AlarmDto alarmDto, AlarmTitle alarmTitle) {
        String message = getMessage(alarmDto.getCrewName(), alarmDto.getCoachName(), alarmDto.getTime());
        alarm.sendGroupMessage(List.of(alarmDto.getCrewSlackId(), alarmDto.getCoachSlackId()), alarmTitle.getTitle(),
                message, alarmTitle.getBarColor());
    }

    public void remindReservation() {
        LocalDate date = LocalDate.now().plusDays(1);
        List<Reservation> reservations = reservationRepository.findAllApprovedReservationsBefore(findLastTime(date));

        for (Reservation reservation : reservations) {
            Coach coach = reservation.getCoach();
            Crew crew = reservation.getCrew();

            String message = getMessage(crew.getName(), coach.getName(), reservation.getScheduleDateTime());
            alarm.sendMessage(coach.getSlackId(), REMIND_COACH.getTitle(), message, REMIND_COACH.getBarColor());
            alarm.sendMessage(crew.getSlackId(), REMIND_CREW.getTitle(), message, REMIND_CREW.getBarColor());
        }
    }

    private String getMessage(String crewName, String coachName, LocalDateTime dateTime) {
        return String.join("\r\n",
                "크루: " + crewName,
                "코치: " + coachName,
                "티타임: " + Date.formatDate("yyyy-MM-dd HH:mm", dateTime)
        );
    }
}