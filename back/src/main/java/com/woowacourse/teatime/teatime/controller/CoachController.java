package com.woowacourse.teatime.teatime.controller;

import com.woowacourse.teatime.auth.support.CoachAuthenticationPrincipal;
import com.woowacourse.teatime.auth.support.CrewAuthenticationPrincipal;
import com.woowacourse.teatime.teatime.controller.dto.request.CoachUpdateProfileRequest;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachFindOwnHistoryResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachFindResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachReservationsResponse;
import com.woowacourse.teatime.teatime.service.CoachService;
import com.woowacourse.teatime.teatime.service.ReservationService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/coaches")
public class CoachController {

    private final CoachService coachService;
    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<CoachFindResponse>> findCoaches(@CrewAuthenticationPrincipal Long crewId) {
        List<CoachFindResponse> responses = coachService.findAll();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/me/reservations")
    public ResponseEntity<CoachReservationsResponse> findCoachReservations(
            @CoachAuthenticationPrincipal Long coachId) {
        CoachReservationsResponse response = reservationService.findByCoachId(coachId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me/history")
    public ResponseEntity<List<CoachFindOwnHistoryResponse>> findCoachHistory(
            @CoachAuthenticationPrincipal Long coachId) {
        List<CoachFindOwnHistoryResponse> response = reservationService.findOwnHistoryByCoach(coachId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/me/profile")
    public ResponseEntity<Void> updateProfile(
            @CoachAuthenticationPrincipal Long coachId, @Valid @RequestBody CoachUpdateProfileRequest request) {
        coachService.updateProfile(coachId, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
