package com.woowacourse.teatime.teatime.controller.dto.response;

import static com.woowacourse.teatime.teatime.domain.SheetStatus.WRITING;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.domain.Schedule;
import com.woowacourse.teatime.teatime.domain.Sheet;
import com.woowacourse.teatime.teatime.domain.SheetStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CoachFindCrewSheetResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime dateTime;
    private String crewName;
    private String crewImage;
    private SheetStatus status;
    private List<SheetDto> sheets;

    public static CoachFindCrewSheetResponse of(Reservation reservation, List<Sheet> sheets) {
        Schedule schedule = reservation.getSchedule();
        Crew crew = reservation.getCrew();
        if (WRITING.equals(reservation.getSheetStatus())) {
            return new CoachFindCrewSheetResponse(schedule.getLocalDateTime(), crew.getName(), crew.getImage(),
                    reservation.getSheetStatus(), SheetDto.generateEmptySheet(sheets));
        }
        return new CoachFindCrewSheetResponse(schedule.getLocalDateTime(), crew.getName(), crew.getImage(),
                reservation.getSheetStatus(), SheetDto.from(sheets));
    }
}
