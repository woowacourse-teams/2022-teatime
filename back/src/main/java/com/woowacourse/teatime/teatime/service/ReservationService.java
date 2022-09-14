package com.woowacourse.teatime.teatime.service;

import static com.woowacourse.teatime.teatime.domain.ReservationStatus.APPROVED;
import static com.woowacourse.teatime.teatime.domain.ReservationStatus.BEFORE_APPROVED;
import static com.woowacourse.teatime.teatime.domain.ReservationStatus.CANCELED;
import static com.woowacourse.teatime.teatime.domain.ReservationStatus.DONE;
import static com.woowacourse.teatime.teatime.domain.ReservationStatus.IN_PROGRESS;
import static com.woowacourse.teatime.teatime.domain.SheetStatus.SUBMITTED;

import com.woowacourse.teatime.auth.support.dto.UserRoleDto;
import com.woowacourse.teatime.exception.UnAuthorizedException;
import com.woowacourse.teatime.teatime.controller.dto.request.ReservationApproveRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.ReservationReserveRequest;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachFindCrewHistoryResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachFindOwnHistoryResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachReservationsResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.CrewFindOwnHistoryResponse;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.domain.ReservationStatus;
import com.woowacourse.teatime.teatime.domain.Role;
import com.woowacourse.teatime.teatime.domain.Schedule;
import com.woowacourse.teatime.teatime.domain.Sheet;
import com.woowacourse.teatime.teatime.domain.SheetStatus;
import com.woowacourse.teatime.teatime.exception.NotFoundCoachException;
import com.woowacourse.teatime.teatime.exception.NotFoundCrewException;
import com.woowacourse.teatime.teatime.exception.NotFoundReservationException;
import com.woowacourse.teatime.teatime.exception.NotFoundScheduleException;
import com.woowacourse.teatime.teatime.repository.CoachRepository;
import com.woowacourse.teatime.teatime.repository.CrewRepository;
import com.woowacourse.teatime.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.teatime.repository.ScheduleRepository;
import com.woowacourse.teatime.teatime.repository.SheetRepository;
import com.woowacourse.teatime.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CrewRepository crewRepository;
    private final ScheduleRepository scheduleRepository;
    private final CoachRepository coachRepository;
    private final SheetRepository sheetRepository;

    public Long save(Long crewId, ReservationReserveRequest reservationReserveRequest) {
        Crew crew = crewRepository.findById(crewId)
                .orElseThrow(NotFoundCrewException::new);
        Schedule schedule = scheduleRepository.findById(reservationReserveRequest.getScheduleId())
                .orElseThrow(NotFoundScheduleException::new);

        schedule.reserve();
        Reservation reservation = reservationRepository.save(new Reservation(schedule, crew));
        return reservation.getId();
    }

    public void approve(Long coachId, Long reservationId, ReservationApproveRequest reservationApproveRequest) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(NotFoundReservationException::new);
        validateIsSameCoach(coachId, reservation);

        reservation.confirm(reservationApproveRequest.getIsApproved());
    }

    public void cancel(Long reservationId, UserRoleDto userRoleDto) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(NotFoundReservationException::new);

        Role role = Role.search(userRoleDto.getRole());
        validateAuthorization(userRoleDto.getId(), role, reservation);
        reservation.cancel(role);
    }

    private void validateAuthorization(Long applicantId, Role role,
                                       Reservation reservation) {
        if (role.isCoach()) {
            validateCoachId(applicantId);
            validateIsSameCoach(applicantId, reservation);
        }

        if (role.isCrew()) {
            validateCrewId(applicantId);
            validateIsSameCrew(applicantId, reservation);
        }
    }

    private void validateIsSameCrew(Long crewId, Reservation reservation) {
        if (!reservation.isSameCrew(crewId)) {
            throw new NotFoundCrewException();
        }
    }

    private void validateIsSameCoach(Long coachId, Reservation reservation) {
        Schedule schedule = reservation.getSchedule();
        if (!schedule.isSameCoach(coachId)) {
            throw new NotFoundReservationException();
        }
    }

    public List<CrewFindOwnHistoryResponse> findOwnHistoryByCrew(Long crewId) {
        validateCrewId(crewId);
        List<Reservation> reservations = reservationRepository.findByCrewIdOrderByScheduleLocalDateTimeDesc(crewId);
        return CrewFindOwnHistoryResponse.from(reservations);
    }

    private void validateCrewId(Long crewId) {
        crewRepository.findById(crewId)
                .orElseThrow(NotFoundCrewException::new);
    }

    public CoachReservationsResponse findByCoachId(Long coachId) {
        validateCoachId(coachId);
        List<Reservation> reservations = reservationRepository.findByScheduleCoachIdAndReservationStatusNotIn(coachId,
                List.of(DONE, CANCELED));
        return classifyReservationsAndReturnDto(reservations);
    }

    private void validateCoachId(Long coachId) {
        coachRepository.findById(coachId)
                .orElseThrow(NotFoundCoachException::new);
    }

    private CoachReservationsResponse classifyReservationsAndReturnDto(List<Reservation> reservations) {
        return CoachReservationsResponse.of(
                ReservationStatus.classifyReservations(BEFORE_APPROVED, reservations),
                ReservationStatus.classifyReservations(APPROVED, reservations),
                ReservationStatus.classifyReservations(IN_PROGRESS, reservations));
    }

    public void updateReservationStatusToDone(Long coachId, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(NotFoundReservationException::new);
        validateCoachAuthorization(coachId, reservation);
        reservation.updateReservationStatusToDone();
    }

    public List<CoachFindCrewHistoryResponse> findCrewHistoryByCoach(Long crewId) {
        validateCrewId(crewId);
        List<Reservation> reservations =
                reservationRepository.findByCrewIdAndReservationStatusOrderByScheduleLocalDateTimeDesc(crewId, DONE);

        List<CoachFindCrewHistoryResponse> response = new ArrayList<>();
        for (Reservation reservation : reservations) {
            List<Sheet> sheets = sheetRepository.findByReservationIdOrderByNumber(
                    reservation.getId());
            response.add(CoachFindCrewHistoryResponse.from(reservation, sheets));
        }

        return response;
    }

    public void updateSheetStatusToSubmitted(Long crewId, Long reservationId, SheetStatus status) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(NotFoundReservationException::new);
        validateCrewAuthorization(crewId, reservation);
        if (SUBMITTED.equals(status)) {
            reservation.updateSheetStatusToSubmitted();
        }
    }

    private void validateCrewAuthorization(Long crewId, Reservation reservation) {
        if (!reservation.isSameCrew(crewId)) {
            throw new UnAuthorizedException();
        }
    }

    private void validateCoachAuthorization(Long coachId, Reservation reservation) {
        if (!reservation.isSameCoach(coachId)) {
            throw new UnAuthorizedException();
        }
    }

    public void updateReservationStatusToInProgress() {
        LocalDate today = LocalDate.now();
        List<Reservation> reservations = reservationRepository.findAllApprovedReservationsBetween(
                Date.findFirstTime(today), Date.findLastTime(today));

        for (Reservation reservation : reservations) {
            updateStartedReservationToInProgress(reservation);
        }
    }

    private void updateStartedReservationToInProgress(Reservation reservation) {
        if (reservation.isBeforeFromNow()) {
            reservation.updateReservationStatusToInProgress();
        }
    }

    public void cancelReservationNotSubmitted() {
        LocalDate today = LocalDateTime.now().toLocalDate();
        LocalDateTime firstTime = Date.findFirstTime(today);
        LocalDateTime lastTime = Date.findLastTime(today);

        List<Reservation> reservations
                = reservationRepository.findAllShouldBeCanceled(firstTime, lastTime);

        for (Reservation reservation : reservations) {
            reservation.cancel(Role.COACH);
        }
    }

    public List<CoachFindOwnHistoryResponse> findOwnHistoryByCoach(Long coachId) {
        validateCoachId(coachId);
        List<Reservation> reservations = reservationRepository.findByScheduleCoachIdAndReservationStatusIn(
                coachId, List.of(DONE, CANCELED));
        return CoachFindOwnHistoryResponse.from(reservations);
    }
}

