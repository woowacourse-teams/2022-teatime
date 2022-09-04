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

    private List<CoachReservationDtoWithSheetStatus> beforeApproved;
    private List<CoachReservationDtoWithSheetStatus> approved;
    private List<CoachReservationDtoWithoutSheetStatus> inProgress;

    public static CoachReservationsResponse of(List<Reservation> beforeApproved, List<Reservation> approved,
                                               List<Reservation> inProgress) {
        List<CoachReservationDtoWithSheetStatus> beforeApprovedDtos = CoachReservationDtoWithSheetStatus.from(beforeApproved);
        List<CoachReservationDtoWithSheetStatus> approvedDtos = CoachReservationDtoWithSheetStatus.from(approved);
        List<CoachReservationDtoWithoutSheetStatus> inProgressDtos = CoachReservationDtoWithoutSheetStatus.from(inProgress);

        return new CoachReservationsResponse(beforeApprovedDtos, approvedDtos, inProgressDtos);
    }
}
