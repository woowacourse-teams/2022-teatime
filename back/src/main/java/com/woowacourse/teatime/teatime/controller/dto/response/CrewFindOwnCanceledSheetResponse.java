package com.woowacourse.teatime.teatime.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.teatime.teatime.domain.CanceledReservation;
import com.woowacourse.teatime.teatime.domain.CanceledSheet;
import com.woowacourse.teatime.teatime.domain.Coach;
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
public class CrewFindOwnCanceledSheetResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime dateTime;

    private String coachName;

    private String coachImage;

    private SheetStatus sheetStatus;

    private List<SheetDto> sheets;

    public static CrewFindOwnCanceledSheetResponse of(CanceledReservation reservation, List<CanceledSheet> sheets) {
        LocalDateTime scheduledAt = reservation.getOriginSchedule();
        Coach coach = reservation.getCoach();
        List<SheetDto> sheetDtos = SheetDto.fromCanceled(sheets);
        return new CrewFindOwnCanceledSheetResponse(scheduledAt, coach.getName(), coach.getImage(),
                SheetStatus.SUBMITTED, sheetDtos);
    }
}
