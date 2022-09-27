package com.woowacourse.teatime.teatime.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.domain.ReservationStatus;
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
public class CrewFindOwnSheetResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime dateTime;

    private String coachName;

    private String coachImage;

    private SheetStatus sheetStatus;

    private ReservationStatus reservationStatus;

    private List<SheetDto> sheets;

    public static CrewFindOwnSheetResponse of(Reservation reservation, List<Sheet> sheets) {
        Schedule schedule = reservation.getSchedule();
        Coach coach = schedule.getCoach();
        List<SheetDto> sheetDtos = SheetDto.from(sheets);
        return new CrewFindOwnSheetResponse(schedule.getLocalDateTime(), coach.getName(), coach.getImage(),
                reservation.getSheetStatus(), reservation.getReservationStatus(), sheetDtos);
    }
}
