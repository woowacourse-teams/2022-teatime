package com.woowacourse.teatime.teatime.controller.dto.response;

import static com.woowacourse.teatime.teatime.domain.SheetStatus.WRITING;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.domain.Schedule;
import com.woowacourse.teatime.teatime.domain.Sheet;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CoachFindCrewHistoryResponse {

    private Long reservationId;

    private String coachName;

    private String coachImage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime dateTime;

    private List<SheetDto> sheets;

    public static CoachFindCrewHistoryResponse from(Reservation reservation, List<Sheet> sheets) {
        Schedule schedule = reservation.getSchedule();
        Coach coach = schedule.getCoach();
        if (WRITING.equals(reservation.getSheetStatus())) {
            return new CoachFindCrewHistoryResponse(reservation.getId(), coach.getName(), coach.getImage(),
                    schedule.getLocalDateTime(), SheetDto.generateEmptySheet(sheets));
        }
        return new CoachFindCrewHistoryResponse(reservation.getId(), coach.getName(), coach.getImage(),
                schedule.getLocalDateTime(), SheetDto.from(sheets));
    }
}
