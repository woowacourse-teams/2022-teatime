package com.woowacourse.teatime.teatime.service.dto;

import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.Reservation;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AlarmTargetDto {

    private String crewName;
    private String coachName;
    private String slackId;
    private LocalDateTime scheduleDateTime;

    public static AlarmTargetDto alarmToCoach(Reservation reservation) {
        Crew crew = reservation.getCrew();
        Coach coach = reservation.getCoach();
        return new AlarmTargetDto(crew.getName(), coach.getName(), coach.getSlackId(),
                reservation.getScheduleDateTime());
    }

    public static AlarmTargetDto alarmToCrew(Reservation reservation) {
        Crew crew = reservation.getCrew();
        Coach coach = reservation.getCoach();
        return new AlarmTargetDto(crew.getName(), coach.getName(), crew.getSlackId(),
                reservation.getScheduleDateTime());
    }
}
