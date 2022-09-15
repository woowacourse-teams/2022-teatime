package com.woowacourse.teatime.teatime.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.teatime.teatime.domain.CanceledReservation;
import com.woowacourse.teatime.teatime.domain.Coach;
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
public class CrewFindOwnHistoryResponse {

    private Long reservationId;

    private String coachName;

    private String coachImage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime dateTime;

    private ReservationStatus status;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime updatedAt;

    public static List<CrewFindOwnHistoryResponse> of(List<Reservation> reservations,
                                                      List<CanceledReservation> canceledReservations) {
        List<CrewFindOwnHistoryResponse> response = reservations.stream()
                .map(CrewFindOwnHistoryResponse::from)
                .collect(Collectors.toList());
        response.addAll(canceledReservations.stream()
                .map(CrewFindOwnHistoryResponse::from)
                .collect(Collectors.toList()));

        response.sort(Comparator.comparing(CrewFindOwnHistoryResponse::getUpdatedAt).reversed());
        return response;
    }

    private static CrewFindOwnHistoryResponse from(Reservation reservation) {
        Schedule schedule = reservation.getSchedule();
        Coach coach = reservation.getCoach();
        return new CrewFindOwnHistoryResponse(reservation.getId(), coach.getName(), coach.getImage(),
                schedule.getLocalDateTime(), reservation.getReservationStatus(), reservation.getUpdatedAt());
    }

    private static CrewFindOwnHistoryResponse from(CanceledReservation reservation) {
        Coach coach = reservation.getCoach();
        return new CrewFindOwnHistoryResponse(reservation.getOriginId(), coach.getName(), coach.getImage(),
                reservation.getOriginSchedule(), ReservationStatus.CANCELED, reservation.getCreatedAt());
    }
}
