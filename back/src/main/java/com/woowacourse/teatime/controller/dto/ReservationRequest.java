package com.woowacourse.teatime.controller.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ReservationRequest {

    @NotNull
    @Min(1)
    private Long crewId;

    @NotNull
    @Min(1)
    private Long coachId;

    @NotNull
    @Min(1)
    private Long scheduleId;

    private ReservationRequest() {
    }

    public ReservationRequest(Long crewId, Long coachId, Long scheduleId) {
        this.crewId = crewId;
        this.coachId = coachId;
        this.scheduleId = scheduleId;
    }

    public Long getCrewId() {
        return crewId;
    }

    public Long getCoachId() {
        return coachId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }
}
