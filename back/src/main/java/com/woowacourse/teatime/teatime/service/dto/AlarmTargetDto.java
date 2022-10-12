package com.woowacourse.teatime.teatime.service.dto;

import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.Reservation;
import java.time.LocalDateTime;
import java.util.List;
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

    public static List<AlarmTargetDto> from(Reservation reservation) {
        Crew crew = reservation.getCrew();
        Coach coach = reservation.getCoach();
        LocalDateTime dateTime = reservation.getScheduleDateTime();
        return List.of(alarmToCoach(crew, coach, dateTime), alarmToCrew(crew, coach, dateTime));
    }

    private static AlarmTargetDto alarmToCoach(Crew crew, Coach coach, LocalDateTime dateTime) {
        return new AlarmTargetDto(crew.getName(), coach.getName(), coach.getSlackId(), dateTime);
    }

    private static AlarmTargetDto alarmToCrew(Crew crew, Coach coach, LocalDateTime dateTime) {
        return new AlarmTargetDto(crew.getName(), coach.getName(), crew.getSlackId(), dateTime);
    }
}
