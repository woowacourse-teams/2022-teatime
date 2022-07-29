package com.woowacourse.teatime.controller;

import com.woowacourse.teatime.controller.dto.response.SheetResponse;
import com.woowacourse.teatime.controller.dto.response.CrewFindOwnReservationResponse;
import com.woowacourse.teatime.service.ReservationService;
import com.woowacourse.teatime.service.SheetService;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/crews")
public class CrewController {

    private final ReservationService reservationService;
    private final SheetService sheetService;

    @GetMapping("/me/reservations")
    public ResponseEntity<List<CrewFindOwnReservationResponse>> findOwnReservations(@RequestBody @NotNull Long crewId) {
        List<CrewFindOwnReservationResponse> responses = reservationService.findByCrew(crewId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{crewId}/reservations")
    public ResponseEntity<List<CrewFindOwnReservationResponse>> findCrewReservations(@PathVariable @NotNull Long crewId) {
        List<CrewFindOwnReservationResponse> responses = reservationService.findByCrew(crewId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/me/reservations/{reservationId}")
    public ResponseEntity<SheetResponse> findOwnSheets(@PathVariable @NotNull Long reservationId) {
        SheetResponse response = sheetService.findByReservation(reservationId);
        return ResponseEntity.ok(response);
    }
}
