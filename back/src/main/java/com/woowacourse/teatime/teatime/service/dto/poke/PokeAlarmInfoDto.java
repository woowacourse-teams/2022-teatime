package com.woowacourse.teatime.teatime.service.dto.poke;

import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PokeAlarmInfoDto {

    private final String coachSlackId;
    private final String crewName;

    public static PokeAlarmInfoDto of(Coach coach, Crew crew) {
        return new PokeAlarmInfoDto(coach.getSlackId(), crew.getName());
    }
}
