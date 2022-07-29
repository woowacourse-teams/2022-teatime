package com.woowacourse.teatime.controller;

import com.woowacourse.teatime.controller.dto.response.CrewFindOwnReservationResponse;
import com.woowacourse.teatime.service.ReservationService;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/crews")
public class CrewController {

    private final ReservationService reservationService;

    @GetMapping("/me/reservations")
    public ResponseEntity<List<CrewFindOwnReservationResponse>> findOwnReservations(@RequestBody @NotNull Long crewId) {
        List<CrewFindOwnReservationResponse> responses = reservationService.findByCrew(crewId);
        return ResponseEntity.ok(responses);
    }
}
