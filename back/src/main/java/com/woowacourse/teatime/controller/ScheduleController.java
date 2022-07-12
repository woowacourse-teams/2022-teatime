package com.woowacourse.teatime.controller;


import com.woowacourse.teatime.ScheduleRequest;
import com.woowacourse.teatime.ScheduleResponse;
import com.woowacourse.teatime.service.ScheduleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/coaches")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/{id}/schedules")
    public ResponseEntity<List<ScheduleResponse>> findCoachList(@PathVariable Long id,
                                                                @ModelAttribute ScheduleRequest request) {
        List<ScheduleResponse> scheduleResponses = scheduleService.find(id, request);
        return ResponseEntity.ok(scheduleResponses);
    }
}
