package com.woowacourse.teatime.controller.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ReservationApproveRequest {

    @NotNull
    @Min(1)
    private Long coachId;

    @NotNull
    private Boolean isApproved;

    private ReservationApproveRequest() {
    }

    public ReservationApproveRequest(Long coachId, Boolean isApproved) {
        this.coachId = coachId;
        this.isApproved = isApproved;
    }

    public Long getCoachId() {
        return coachId;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }
}
