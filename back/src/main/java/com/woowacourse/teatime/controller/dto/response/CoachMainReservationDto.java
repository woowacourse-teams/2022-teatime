package com.woowacourse.teatime.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.teatime.domain.Reservation;
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
public class CoachMainReservationDto {

    private Long reservationId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime dateTime;

    private Long crewId;

    private String crewName;

    private String crewImage;

    public static List<CoachMainReservationDto> from(List<Reservation> reservations) {
        return reservations.stream()
                .map(CoachMainReservationDto::from)
                .collect(Collectors.toList());
    }

    private static CoachMainReservationDto from(Reservation reservation) {
        return new CoachMainReservationDto(
                reservation.getId(),
                reservation.getLocalDateTime(),
                reservation.getCrew().getId(),
                reservation.getCrew().getName(),
                reservation.getCrew().getImage()
        );
    }
}
