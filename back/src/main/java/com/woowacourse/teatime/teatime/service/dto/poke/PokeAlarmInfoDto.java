package com.woowacourse.teatime.teatime.service.dto.poke;

import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PokeAlarmInfoDto {

    private final String crewName;
    private final String coachSlackId;

    public static PokeAlarmInfoDto of(Crew crew, Coach coach) {
        return new PokeAlarmInfoDto(crew.getName(), coach.getSlackId());
    }
}
