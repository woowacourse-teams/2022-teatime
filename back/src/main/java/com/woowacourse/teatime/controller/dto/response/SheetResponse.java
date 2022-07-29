package com.woowacourse.teatime.controller.dto.response;

import com.woowacourse.teatime.domain.Coach;
import com.woowacourse.teatime.domain.Reservation;
import com.woowacourse.teatime.domain.Schedule;
import com.woowacourse.teatime.domain.Sheet;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SheetResponse {

    private LocalDateTime dateTime;
    private String coachName;
    private String coachImage;
    private List<SheetDto> sheets;

    public static SheetResponse from(Reservation reservation, List<Sheet> sheets) {
        Schedule schedule = reservation.getSchedule();
        Coach coach = schedule.getCoach();
        List<SheetDto> sheetDtos = SheetDto.of(sheets);
        return new SheetResponse(schedule.getLocalDateTime(), coach.getName(), coach.getImage(), sheetDtos);
    }
}
