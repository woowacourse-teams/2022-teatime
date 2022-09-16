package com.woowacourse.teatime.teatime.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.domain.SheetStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CoachReservationDtoWithSheetStatus {

    private Long reservationId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime dateTime;

    private Long crewId;

    private String crewName;

    private String crewImage;

    private SheetStatus sheetStatus;

    public static List<CoachReservationDtoWithSheetStatus> from(List<Reservation> reservations) {
        return reservations.stream()
                .map(CoachReservationDtoWithSheetStatus::from)
                .collect(Collectors.toList());
    }

    private static CoachReservationDtoWithSheetStatus from(Reservation reservation) {
        return new CoachReservationDtoWithSheetStatus(
                reservation.getId(),
                reservation.getScheduleDateTime(),
                reservation.getCrew().getId(),
                reservation.getCrew().getName(),
                reservation.getCrew().getImage(),
                reservation.getSheetStatus()
        );
    }
}
