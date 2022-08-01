package com.woowacourse.teatime.controller.dto.response;

import com.woowacourse.teatime.domain.Reservation;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CoachReservationsResponse {

    private List<CoachMainReservationDto> beforeApproved;
    private List<CoachMainReservationDto> approved;
    private List<CoachMainReservationDto> inProgress;

    public static CoachReservationsResponse of(List<Reservation> beforeApproved, List<Reservation> approved,
                                               List<Reservation> inProgress) {
        List<CoachMainReservationDto> beforeApprovedDtos = getCoachMainReservationDtos(beforeApproved);
        List<CoachMainReservationDto> approvedDtos = getCoachMainReservationDtos(approved);
        List<CoachMainReservationDto> inProgressDtos = getCoachMainReservationDtos(inProgress);
        return new CoachReservationsResponse(beforeApprovedDtos, approvedDtos, inProgressDtos);
    }

    private static List<CoachMainReservationDto> getCoachMainReservationDtos(List<Reservation> reservations) {
        return reservations.stream()
                .map(CoachMainReservationDto::from)
                .collect(Collectors.toList());
    }
}
