package com.woowacourse.teatime.teatime.controller.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ScheduleFindRequest {

    @NotNull
    @Min(2022)
    private Integer year;

    @NotNull
    @Min(1)
    @Max(12)
    private Integer month;
}
