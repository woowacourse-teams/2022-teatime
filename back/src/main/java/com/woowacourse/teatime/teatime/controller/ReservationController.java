package com.woowacourse.teatime.teatime.controller;

import com.woowacourse.teatime.auth.support.CoachAuthenticationPrincipal;
import com.woowacourse.teatime.auth.support.CrewAuthenticationPrincipal;
import com.woowacourse.teatime.auth.support.UserAuthenticationPrincipal;
import com.woowacourse.teatime.auth.support.dto.UserRoleDto;
import com.woowacourse.teatime.teatime.controller.dto.request.ReservationApproveRequestV2;
import com.woowacourse.teatime.teatime.controller.dto.request.ReservationReserveRequestV2;
import com.woowacourse.teatime.teatime.service.ReservationService;
import com.woowacourse.teatime.teatime.service.SheetService;
import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final SheetService sheetService;

    @PostMapping
    public ResponseEntity<Void> reserve(@CrewAuthenticationPrincipal Long crewId,
                                        @Valid @RequestBody ReservationReserveRequestV2 request) {
        Long reservationId = reservationService.save(crewId, request);
        sheetService.save(reservationId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reservationId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{reservationId}")
    public ResponseEntity<Void> approve(@CoachAuthenticationPrincipal Long coachId,
                                        @PathVariable @NotNull Long reservationId,
                                        @Valid @RequestBody ReservationApproveRequestV2 request) {
        reservationService.approve(coachId, reservationId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> cancel(@PathVariable @NotNull Long reservationId,
                                       @UserAuthenticationPrincipal UserRoleDto userRoleDto) {
        reservationService.cancel(reservationId, userRoleDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<Void> finish(@CoachAuthenticationPrincipal Long coachId,
                                       @PathVariable @NotNull Long reservationId) {
        reservationService.updateReservationStatusToDoneV2(coachId, reservationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
