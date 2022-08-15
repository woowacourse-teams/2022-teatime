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

    private List<CoachMainReservationDto> beforeApproved;
    private List<CoachMainReservationDto> approved;
    private List<CoachMainReservationDto> inProgress;

    public static CoachReservationsResponse of(List<Reservation> beforeApproved, List<Reservation> approved,
                                               List<Reservation> inProgress) {
        List<CoachMainReservationDto> beforeApprovedDtos = CoachMainReservationDto.from(beforeApproved);
        List<CoachMainReservationDto> approvedDtos = CoachMainReservationDto.from(approved);
        List<CoachMainReservationDto> inProgressDtos = CoachMainReservationDto.from(inProgress);

        return new CoachReservationsResponse(beforeApprovedDtos, approvedDtos, inProgressDtos);
    }
}
