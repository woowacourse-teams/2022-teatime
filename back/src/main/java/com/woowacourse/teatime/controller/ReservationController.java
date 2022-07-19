package com.woowacourse.teatime.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.teatime.controller.dto.CrewIdRequest;
import com.woowacourse.teatime.service.ReservationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/coaches/{id}")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> reserve(@PathVariable @NotNull Long id, @PathVariable @NotNull Long scheduleId,
            @Valid @RequestBody CrewIdRequest crewIdRequest) {
        reservationService.save(crewIdRequest.getId(), id, scheduleId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> approve(@PathVariable @NotNull Long id, @PathVariable @NotNull Long reservationId) {
        reservationService.approve(id, reservationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
