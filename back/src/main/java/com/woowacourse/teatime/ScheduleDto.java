package com.woowacourse.teatime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.teatime.domain.Schedule;
import java.time.LocalDateTime;

public class ScheduleDto {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime dateTime;

    private Boolean isPossible;

    private ScheduleDto() {
    }

    public ScheduleDto(Schedule schedule) {
        this.id = schedule.getId();
        this.dateTime = schedule.getLocalDateTime();
        this.isPossible = schedule.getIsPossible();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Boolean getIsPossible() {
        return isPossible;
    }
}
