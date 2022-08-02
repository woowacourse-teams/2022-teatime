package com.woowacourse.teatime.controller;

import com.woowacourse.teatime.controller.dto.ReservationCancelRequest;
import com.woowacourse.teatime.controller.dto.request.ReservationApproveRequest;
import com.woowacourse.teatime.controller.dto.request.ReservationReserveRequest;
import com.woowacourse.teatime.service.ReservationService;
import com.woowacourse.teatime.service.SheetService;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<Void> reserve(@Valid @RequestBody ReservationReserveRequest request) {
        Long reservationId = reservationService.save(request);
        sheetService.save(request.getCoachId(), reservationId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{reservationId}")
    public ResponseEntity<Void> approve(@PathVariable @NotNull Long reservationId,
                                        @Valid @RequestBody ReservationApproveRequest request) {
        reservationService.approve(reservationId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> cancel(@PathVariable @NotNull Long reservationId,
                                       @Valid @RequestBody ReservationCancelRequest reservationCancelRequest) {
        reservationService.cancel(reservationId, reservationCancelRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<Void> finish(@PathVariable @NotNull Long reservationId) {
        reservationService.updateStatusToDone(reservationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
