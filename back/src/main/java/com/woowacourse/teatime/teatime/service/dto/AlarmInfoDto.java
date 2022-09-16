package com.woowacourse.teatime.teatime.service.dto;

import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AlarmInfoDto {

    private final String crewSlackId;
    private final String coachSlackId;
    private final String crewName;
    private final String coachName;
    private final LocalDateTime time;

    public static AlarmInfoDto of(Coach coach, Crew crew, LocalDateTime time) {
        return new AlarmInfoDto(crew.getSlackId(), coach.getSlackId(), crew.getName(), coach.getName(), time);
    }
}
