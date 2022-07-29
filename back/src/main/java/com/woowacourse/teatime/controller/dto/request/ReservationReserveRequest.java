package com.woowacourse.teatime.controller.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ReservationReserveRequest {

    @NotNull
    @Min(1)
    private Long crewId;

    @NotNull
    @Min(1)
    private Long coachId;

    @NotNull
    @Min(1)
    private Long scheduleId;
}
