package com.woowacourse.teatime.teatime.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.domain.ReservationStatus;
import com.woowacourse.teatime.teatime.domain.Schedule;
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
public class CoachFindOwnHistoryResponse {

    private Long reservationId;

    private String crewName;

    private String crewImage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime dateTime;

    private ReservationStatus status;

    public static List<CoachFindOwnHistoryResponse> from(List<Reservation> reservations) {
        return reservations.stream()
                .map(CoachFindOwnHistoryResponse::from)
                .collect(Collectors.toList());
    }

    private static CoachFindOwnHistoryResponse from(Reservation reservation) {
        Schedule schedule = reservation.getSchedule();
        Crew crew = reservation.getCrew();
        return new CoachFindOwnHistoryResponse(reservation.getId(), crew.getName(), crew.getImage(),
                schedule.getLocalDateTime(), reservation.getReservationStatus());
    }
}
