package com.woowacourse.teatime.controller;

import com.woowacourse.teatime.controller.dto.request.CoachSaveRequest;
import com.woowacourse.teatime.controller.dto.response.CoachFindResponse;
import com.woowacourse.teatime.controller.dto.response.CoachReservationsResponse;
import com.woowacourse.teatime.service.CoachService;
import com.woowacourse.teatime.service.ReservationService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/coaches")
public class CoachController {

    private final CoachService coachService;
    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<CoachFindResponse>> findCoaches() {
        List<CoachFindResponse> responses = coachService.findAll();
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody CoachSaveRequest request) {
        coachService.save(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/me/reservations")
    public ResponseEntity<CoachReservationsResponse> findCoachReservations(
            @Valid @RequestHeader("coachId") Long coachId) {
        CoachReservationsResponse response = reservationService.findByCoachId(coachId);
        return ResponseEntity.ok(response);
    }
}
