package com.woowacourse.teatime.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.teatime.domain.Reservation;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CoachMainReservationDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime dateTime;

    private String crewName;

    private String crewImage;

    public static CoachMainReservationDto from(Reservation reservation) {
        return new CoachMainReservationDto(reservation.getLocalDateTime(),
                reservation.getCrew().getName(), reservation.getCrew().getImage());
    }
}
