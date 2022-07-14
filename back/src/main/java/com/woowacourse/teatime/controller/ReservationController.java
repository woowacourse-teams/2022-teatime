package com.woowacourse.teatime.controller;

import com.woowacourse.teatime.controller.dto.CrewIdRequest;
import com.woowacourse.teatime.service.ReservationService;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/coaches")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/{id}/schedules/{scheduleId}")
    public ResponseEntity<Void> reserve(@PathVariable @NotNull Long id, @PathVariable @NotNull Long scheduleId,
                                        @Valid @RequestBody CrewIdRequest crewIdRequest) {
        reservationService.save(crewIdRequest.getId(), id, scheduleId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
