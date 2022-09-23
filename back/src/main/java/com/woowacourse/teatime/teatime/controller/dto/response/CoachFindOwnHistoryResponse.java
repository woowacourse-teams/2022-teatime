package com.woowacourse.teatime.teatime.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.teatime.teatime.domain.CanceledReservation;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.domain.ReservationStatus;
import com.woowacourse.teatime.teatime.domain.Schedule;
import java.time.LocalDateTime;
import java.util.Comparator;
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

    private Long crewId;

    private String crewName;

    private String crewImage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime dateTime;

    private ReservationStatus status;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime updatedAt;

    public static List<CoachFindOwnHistoryResponse> of(List<Reservation> reservations,
                                                       List<CanceledReservation> canceledReservations) {
        List<CoachFindOwnHistoryResponse> response = reservations.stream()
                .map(CoachFindOwnHistoryResponse::from)
                .collect(Collectors.toList());
        response.addAll(canceledReservations.stream()
                .map(CoachFindOwnHistoryResponse::from)
                .collect(Collectors.toList()));

        response.sort(Comparator.comparing(CoachFindOwnHistoryResponse::getUpdatedAt).reversed());
        return response;
    }

    private static CoachFindOwnHistoryResponse from(Reservation reservation) {
        Schedule schedule = reservation.getSchedule();
        Crew crew = reservation.getCrew();
        return new CoachFindOwnHistoryResponse(reservation.getId(), crew.getId(), crew.getName(), crew.getImage(),
                schedule.getLocalDateTime(), reservation.getReservationStatus(), reservation.getUpdatedAt());
    }

    private static CoachFindOwnHistoryResponse from(CanceledReservation reservation) {
        Crew crew = reservation.getCrew();
        return new CoachFindOwnHistoryResponse(reservation.getOriginId(), crew.getId(), crew.getName(), crew.getImage(),
                reservation.getOriginSchedule(), ReservationStatus.CANCELED, reservation.getCreatedAt());
    }
}
