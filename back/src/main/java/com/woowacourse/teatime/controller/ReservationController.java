package com.woowacourse.teatime.controller;

import com.woowacourse.teatime.controller.dto.ReservationApproveRequest;
import com.woowacourse.teatime.controller.dto.ReservationRequest;
import com.woowacourse.teatime.service.ReservationService;
import com.woowacourse.teatime.service.SheetService;
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
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final SheetService sheetService;

    @PostMapping
    public ResponseEntity<Void> reserve(@Valid @RequestBody ReservationRequest reservationRequest) {
        Long reservationId = reservationService.save(reservationRequest);
        sheetService.save(reservationRequest.getCoachId(), reservationId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{reservationId}")
    public ResponseEntity<Void> approve(@PathVariable @NotNull Long reservationId,
                                        @Valid @RequestBody ReservationApproveRequest reservationApproveRequest) {
        reservationService.approve(reservationId, reservationApproveRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
