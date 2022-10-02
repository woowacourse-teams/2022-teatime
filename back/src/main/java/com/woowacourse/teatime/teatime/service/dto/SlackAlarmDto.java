package com.woowacourse.teatime.teatime.service.dto;

import static com.woowacourse.teatime.teatime.service.AlarmTitle.REMIND_COACH;
import static com.woowacourse.teatime.teatime.service.AlarmTitle.REMIND_CREW;

import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.service.AlarmTitle;
import com.woowacourse.teatime.util.Date;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SlackAlarmDto {

    private static final String DIRECT_LINK = "[티타임 바로가기](https://teatime.pe.kr/)";

    private String channel;
    private String text;
    private List<AttachmentDto> attachments;

    public static List<SlackAlarmDto> of(AlarmInfoDto alarmInfoDto, AlarmTitle alarmTitle) {
        String message = getMessage(alarmInfoDto.getCrewName(), alarmInfoDto.getCoachName(), alarmInfoDto.getTime());
        AttachmentDto attachmentDto = new AttachmentDto(alarmTitle.getBarColor(), message);
        return List.of(
                new SlackAlarmDto(alarmInfoDto.getCoachSlackId(), alarmTitle.getMessage(), List.of(attachmentDto)),
                new SlackAlarmDto(alarmInfoDto.getCrewSlackId(), alarmTitle.getMessage(), List.of(attachmentDto)));
    }

    public static SlackAlarmDto remindCoach(Reservation reservation) {
        Crew crew = reservation.getCrew();
        Coach coach = reservation.getCoach();
        String message = getMessage(crew.getName(), coach.getName(), reservation.getScheduleDateTime());
        AttachmentDto attachmentDto = new AttachmentDto(REMIND_COACH.getBarColor(), message);
        return new SlackAlarmDto(coach.getSlackId(), REMIND_COACH.getMessage(), List.of(attachmentDto));
    }

    public static SlackAlarmDto remindCrew(Reservation reservation) {
        Crew crew = reservation.getCrew();
        Coach coach = reservation.getCoach();
        String message = getMessage(crew.getName(), coach.getName(), reservation.getScheduleDateTime());
        AttachmentDto attachmentDto = new AttachmentDto(REMIND_CREW.getBarColor(), message);
        return new SlackAlarmDto(crew.getSlackId(), REMIND_CREW.getMessage(), List.of(attachmentDto));
    }

    private static String getMessage(String crewName, String coachName, LocalDateTime dateTime) {
        return String.join("\r\n",
                DIRECT_LINK,
                "크루: " + crewName,
                "코치: " + coachName,
                "티타임: " + Date.formatDate("yyyy-MM-dd HH:mm", dateTime)
        );
    }
}
