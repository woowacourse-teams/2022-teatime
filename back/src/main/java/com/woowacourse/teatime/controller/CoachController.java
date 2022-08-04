package com.woowacourse.teatime.controller;

import com.woowacourse.teatime.controller.dto.request.CoachSaveRequest;
import com.woowacourse.teatime.controller.dto.response.CoachFindResponse;
import com.woowacourse.teatime.controller.dto.response.CoachReservationsResponse;
import com.woowacourse.teatime.service.CoachService;
import com.woowacourse.teatime.service.ReservationService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
        Long coachId = coachService.save(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(coachId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/me/reservations")
    public ResponseEntity<CoachReservationsResponse> findCoachReservations(
            @RequestHeader("coachId") Long coachId) {
        CoachReservationsResponse response = reservationService.findByCoachId(coachId);
        return ResponseEntity.ok(response);
    }
}
