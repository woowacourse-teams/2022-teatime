package com.woowacourse.teatime.teatime.controller;

import com.woowacourse.teatime.auth.support.CoachAuthenticationPrincipal;
import com.woowacourse.teatime.auth.support.CrewAuthenticationPrincipal;
import com.woowacourse.teatime.teatime.controller.dto.request.SheetAnswerUpdateRequest;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachFindCrewHistoryResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachFindCrewSheetResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.CrewFindOwnHistoryResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.CrewFindOwnSheetResponse;
import com.woowacourse.teatime.teatime.service.ReservationService;
import com.woowacourse.teatime.teatime.service.SheetService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/crews")
public class CrewController {

    private final ReservationService reservationService;
    private final SheetService sheetService;

    @GetMapping("/me/reservations")
    public ResponseEntity<List<CrewFindOwnHistoryResponse>> findOwnReservations(
            @CrewAuthenticationPrincipal Long crewId) {
        List<CrewFindOwnHistoryResponse> responses = reservationService.findOwnHistoryByCrew(crewId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{crewId}/reservations")
    public ResponseEntity<List<CoachFindCrewHistoryResponse>> findCrewReservations(
            @CoachAuthenticationPrincipal Long coachId,
            @PathVariable @NotNull Long crewId) {
        List<CoachFindCrewHistoryResponse> responses = reservationService.findCrewHistoryByCoach(crewId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/me/reservations/{reservationId}")
    public ResponseEntity<CrewFindOwnSheetResponse> findOwnSheets(@CrewAuthenticationPrincipal Long crewId,
                                                                  @PathVariable @NotNull Long reservationId) {
        CrewFindOwnSheetResponse response = sheetService.findOwnSheetByCrew(crewId, reservationId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{crewId}/reservations/{reservationId}")
    public ResponseEntity<CoachFindCrewSheetResponse> findCrewSheets(@CoachAuthenticationPrincipal Long coachId,
                                                                     @PathVariable @NotNull Long crewId,
                                                                     @PathVariable @NotNull Long reservationId) {
        CoachFindCrewSheetResponse response = sheetService.findCrewSheetByCoach(crewId, reservationId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/me/reservations/{reservationId}")
    public ResponseEntity<Void> updateSheetAnswer(@CrewAuthenticationPrincipal Long crewId,
                                                  @PathVariable @NotNull Long reservationId,
                                                  @Valid @RequestBody SheetAnswerUpdateRequest request) {
        sheetService.updateAnswer(crewId, reservationId, request);
        reservationService.updateSheetStatusToSubmittedV2(crewId, reservationId, request.getStatus());
        return ResponseEntity.ok().build();
    }
}
