package com.woowacourse.teatime.controller;

import com.woowacourse.teatime.controller.dto.ScheduleRequest;
import com.woowacourse.teatime.controller.dto.ScheduleResponse;
import com.woowacourse.teatime.controller.dto.ScheduleUpdateRequest;
import com.woowacourse.teatime.service.ScheduleService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/coaches/{coachId}/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> findSchedules(@PathVariable @NotNull Long coachId,
                                                                @Valid @ModelAttribute ScheduleRequest request) {
        List<ScheduleResponse> scheduleResponses = scheduleService.find(coachId, request);
        return ResponseEntity.ok(scheduleResponses);
    }

    @PutMapping
    public ResponseEntity<Void> updateSchedules(@PathVariable @NotNull Long coachId,
                                                @RequestBody ScheduleUpdateRequest request) {
        scheduleService.update(coachId, request);
        return ResponseEntity.ok().build();
    }
}
