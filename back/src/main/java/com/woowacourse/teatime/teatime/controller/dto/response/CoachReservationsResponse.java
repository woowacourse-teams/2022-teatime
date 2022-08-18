package com.woowacourse.teatime.teatime.controller.dto.response;

import com.woowacourse.teatime.teatime.domain.Reservation;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CoachReservationsResponse {

    private List<CoachReservationDto> beforeApproved;
    private List<CoachApprovedReservationDto> approved;
    private List<CoachReservationDto> inProgress;

    public static CoachReservationsResponse of(List<Reservation> beforeApproved, List<Reservation> approved,
                                               List<Reservation> inProgress) {
        List<CoachReservationDto> beforeApprovedDtos = CoachReservationDto.from(beforeApproved);
        List<CoachApprovedReservationDto> approvedDtos = CoachApprovedReservationDto.from(approved);
        List<CoachReservationDto> inProgressDtos = CoachReservationDto.from(inProgress);

        return new CoachReservationsResponse(beforeApprovedDtos, approvedDtos, inProgressDtos);
    }
}
