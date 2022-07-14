package com.woowacourse.teatime.controller.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CrewIdRequest {

    @NotNull
    @Min(1)
    private Long id;

    private CrewIdRequest() {
    }

    public CrewIdRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
