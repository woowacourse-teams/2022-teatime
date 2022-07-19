package com.woowacourse.teatime.controller;

import com.woowacourse.teatime.controller.dto.CoachResponse;
import com.woowacourse.teatime.controller.dto.CoachSaveRequest;
import com.woowacourse.teatime.service.CoachService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/coaches")
public class CoachController {

    private final CoachService coachService;

    @GetMapping
    public ResponseEntity<List<CoachResponse>> findCoachList() {
        List<CoachResponse> responses = coachService.findAll();
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody CoachSaveRequest coachSaveRequest) {
        coachService.save(coachSaveRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
