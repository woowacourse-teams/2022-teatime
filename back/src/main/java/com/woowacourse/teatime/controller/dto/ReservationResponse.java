package com.woowacourse.teatime.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.teatime.domain.Coach;
import com.woowacourse.teatime.domain.Reservation;
import com.woowacourse.teatime.domain.Schedule;
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
public class ReservationResponse {

    private Long reservationId;

    private String coachName;

    private String coachImage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime dateTime;

    public static List<ReservationResponse> from(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
    }

    private static ReservationResponse from(Reservation reservation) {
        Schedule schedule = reservation.getSchedule();
        Coach coach = schedule.getCoach();
        return new ReservationResponse(reservation.getId(), coach.getName(), coach.getImage(),
                schedule.getLocalDateTime());
    }
}
