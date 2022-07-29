package com.woowacourse.teatime.controller.dto.response;

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
public class CrewHistoryFindResponse {

    private Long reservationId;

    private String coachName;

    private String coachImage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime dateTime;

    public static List<CrewHistoryFindResponse> from(List<Reservation> reservations) {
        return reservations.stream()
                .map(CrewHistoryFindResponse::from)
                .collect(Collectors.toList());
    }

    private static CrewHistoryFindResponse from(Reservation reservation) {
        Schedule schedule = reservation.getSchedule();
        Coach coach = schedule.getCoach();
        return new CrewHistoryFindResponse(reservation.getId(), coach.getName(), coach.getImage(),
                schedule.getLocalDateTime());
    }
}
