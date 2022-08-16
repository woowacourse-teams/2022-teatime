package com.woowacourse.teatime.teatime.service;

import static com.woowacourse.teatime.teatime.domain.ReservationStatus.APPROVED;
import static com.woowacourse.teatime.teatime.domain.ReservationStatus.BEFORE_APPROVED;
import static com.woowacourse.teatime.teatime.domain.ReservationStatus.DONE;
import static com.woowacourse.teatime.teatime.domain.ReservationStatus.IN_PROGRESS;
import static com.woowacourse.teatime.teatime.domain.SheetStatus.SUBMITTED;

import com.woowacourse.teatime.exception.UnAuthorizedException;
import com.woowacourse.teatime.teatime.controller.dto.request.ReservationApproveRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.ReservationApproveRequestV2;
import com.woowacourse.teatime.teatime.controller.dto.request.ReservationReserveRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.ReservationReserveRequestV2;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachFindCrewHistoryResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachReservationsResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.CrewFindOwnHistoryResponse;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.domain.ReservationStatus;
import com.woowacourse.teatime.teatime.domain.Role;
import com.woowacourse.teatime.teatime.domain.Schedule;
import com.woowacourse.teatime.teatime.domain.SheetStatus;
import com.woowacourse.teatime.teatime.exception.NotFoundCoachException;
import com.woowacourse.teatime.teatime.exception.NotFoundCrewException;
import com.woowacourse.teatime.teatime.exception.NotFoundReservationException;
import com.woowacourse.teatime.teatime.exception.NotFoundScheduleException;
import com.woowacourse.teatime.teatime.repository.CoachRepository;
import com.woowacourse.teatime.teatime.repository.CrewRepository;
import com.woowacourse.teatime.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.teatime.repository.ScheduleRepository;
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

    public Long save(ReservationReserveRequest reservationReserveRequest) {
        Crew crew = crewRepository.findById(reservationReserveRequest.getCrewId())
                .orElseThrow(NotFoundCrewException::new);
        Schedule schedule = scheduleRepository.findByIdAndCoachId(
                        reservationReserveRequest.getScheduleId(), reservationReserveRequest.getCoachId())
                .orElseThrow(NotFoundScheduleException::new);

        schedule.reserve();
        Reservation reservation = reservationRepository.save(new Reservation(schedule, crew));
        return reservation.getId();
    }

    public Long saveV2(Long crewId, ReservationReserveRequestV2 reservationReserveRequest) {
        Crew crew = crewRepository.findById(crewId)
                .orElseThrow(NotFoundCrewException::new);
        Schedule schedule = scheduleRepository.findById(reservationReserveRequest.getScheduleId())
                .orElseThrow(NotFoundScheduleException::new);

        schedule.reserve();
        Reservation reservation = reservationRepository.save(new Reservation(schedule, crew));
        return reservation.getId();
    }

    public void approve(Long reservationId, ReservationApproveRequest reservationApproveRequest) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(NotFoundReservationException::new);
        validateIsSameCoach(reservationApproveRequest.getCoachId(), reservation);

        reservation.confirm(reservationApproveRequest.getIsApproved());
        if (!reservationApproveRequest.getIsApproved()) {
            reservationRepository.delete(reservation);
        }
    }

    public void approveV2(Long coachId, Long reservationId, ReservationApproveRequestV2 reservationApproveRequest) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(NotFoundReservationException::new);
        validateIsSameCoach(coachId, reservation);

        reservation.confirm(reservationApproveRequest.getIsApproved());
        if (!reservationApproveRequest.getIsApproved()) {
            reservationRepository.delete(reservation);
        }
    }

    public void cancel(Long reservationId, Long applicantId, String role) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(NotFoundReservationException::new);

        validateAuthorization(applicantId, Role.search(role), reservation);
        reservation.cancel(Role.search(role));
        reservationRepository.delete(reservation);
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
        List<Reservation> reservations = reservationRepository.findByScheduleCoachIdAndReservationStatusNot(coachId,
                DONE);
        updateReservationStatusToInProgress(reservations);
        return classifyReservationsAndReturnDto(reservations);
    }

    private void updateReservationStatusToInProgress(List<Reservation> reservations) {
        for (Reservation reservation : reservations) {
            reservation.updateReservationStatusToInProgress();
        }
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

    public void updateReservationStatusToDone(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(NotFoundReservationException::new);
        reservation.updateReservationStatusToDone();
    }

    public void updateReservationStatusToDoneV2(Long coachId, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(NotFoundReservationException::new);
        validateCoachAuthorization(coachId, reservation);
        reservation.updateReservationStatusToDone();
    }

    public List<CoachFindCrewHistoryResponse> findCrewHistoryByCoach(Long crewId) {
        validateCrewId(crewId);
        List<Reservation> reservations =
                reservationRepository.findByCrewIdAndReservationStatusOrderByScheduleLocalDateTimeDesc(crewId, DONE);
        return CoachFindCrewHistoryResponse.from(reservations);
    }

    public void updateSheetStatusToSubmitted(Long reservationId, SheetStatus status) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(NotFoundReservationException::new);
        if (SUBMITTED.equals(status)) {
            reservation.updateSheetStatusToSubmitted();
        }
    }

    public void updateSheetStatusToSubmittedV2(Long crewId, Long reservationId, SheetStatus status) {
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
}

