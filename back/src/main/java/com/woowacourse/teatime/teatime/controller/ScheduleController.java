package com.woowacourse.teatime.teatime.controller;

import com.woowacourse.teatime.auth.support.CoachAuthenticationPrincipal;
import com.woowacourse.teatime.auth.support.CrewAuthenticationPrincipal;
import com.woowacourse.teatime.teatime.controller.dto.request.ScheduleFindRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.ScheduleUpdateRequest;
import com.woowacourse.teatime.teatime.controller.dto.response.ScheduleFindResponse;
import com.woowacourse.teatime.teatime.service.ScheduleService;
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
@RequestMapping("/api/v2/coaches")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/{coachId}/schedules")
    public ResponseEntity<List<ScheduleFindResponse>> findCoachSchedules(@CrewAuthenticationPrincipal Long crewId,
                                                                         @PathVariable @NotNull Long coachId,
                                                                         @Valid @ModelAttribute ScheduleFindRequest request) {
        List<ScheduleFindResponse> scheduleFindResponse = scheduleService.find(coachId, request);
        return ResponseEntity.ok(scheduleFindResponse);
    }

    @GetMapping("/me/schedules")
    public ResponseEntity<List<ScheduleFindResponse>> findOwnSchedules(@CoachAuthenticationPrincipal Long coachId,
                                                                       @Valid @ModelAttribute ScheduleFindRequest request) {
        List<ScheduleFindResponse> scheduleFindResponse = scheduleService.find(coachId, request);
        return ResponseEntity.ok(scheduleFindResponse);
    }

    @PutMapping("/me/schedules")
    public ResponseEntity<Void> updateSchedules(@CoachAuthenticationPrincipal Long coachId,
                                                @RequestBody List<ScheduleUpdateRequest> requests) {
        scheduleService.update(coachId, requests);
        return ResponseEntity.noContent().build();
    }
}
