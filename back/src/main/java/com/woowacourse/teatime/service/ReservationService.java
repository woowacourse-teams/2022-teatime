package com.woowacourse.teatime.service;

import static com.woowacourse.teatime.domain.ReservationStatus.APPROVED;
import static com.woowacourse.teatime.domain.ReservationStatus.BEFORE_APPROVED;
import static com.woowacourse.teatime.domain.ReservationStatus.DONE;
import static com.woowacourse.teatime.domain.ReservationStatus.IN_PROGRESS;

import com.woowacourse.teatime.controller.dto.ReservationCancelRequest;
import com.woowacourse.teatime.controller.dto.request.ReservationApproveRequest;
import com.woowacourse.teatime.controller.dto.request.ReservationReserveRequest;
import com.woowacourse.teatime.controller.dto.response.CoachReservationsResponse;
import com.woowacourse.teatime.controller.dto.response.CrewFindOwnReservationResponse;
import com.woowacourse.teatime.domain.Crew;
import com.woowacourse.teatime.domain.Reservation;
import com.woowacourse.teatime.domain.ReservationStatus;
import com.woowacourse.teatime.domain.Reservations;
import com.woowacourse.teatime.domain.Role;
import com.woowacourse.teatime.domain.Schedule;
import com.woowacourse.teatime.exception.NotFoundCoachException;
import com.woowacourse.teatime.exception.NotFoundCrewException;
import com.woowacourse.teatime.exception.NotFoundReservationException;
import com.woowacourse.teatime.exception.NotFoundScheduleException;
import com.woowacourse.teatime.repository.CoachRepository;
import com.woowacourse.teatime.repository.CrewRepository;
import com.woowacourse.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.repository.ScheduleRepository;
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

    public void approve(Long reservationId, ReservationApproveRequest reservationApproveRequest) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(NotFoundReservationException::new);
        validateIsSameCoach(reservationApproveRequest.getCoachId(), reservation);

        reservation.confirm(reservationApproveRequest.getIsApproved());
        if (!reservationApproveRequest.getIsApproved()) {
            reservationRepository.delete(reservation);
        }
    }

    public void cancel(Long reservationId, ReservationCancelRequest reservationCancelRequest) {
        Role role = Role.search(reservationCancelRequest.getRole());
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(NotFoundReservationException::new);

        validateAuthorization(reservationCancelRequest, role, reservation);
        reservation.cancel(role);
        reservationRepository.delete(reservation);
    }

    private void validateAuthorization(ReservationCancelRequest reservationCancelRequest, Role role,
                                       Reservation reservation) {
        if (role.isCoach()) {
            Long coachId = reservationCancelRequest.getApplicantId();
            validateIsSameCoach(coachId, reservation);
        }

        if (role.isCrew()) {
            Long crewId = reservationCancelRequest.getApplicantId();
            validateIsSameCrew(crewId, reservation);
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

    public List<CrewFindOwnReservationResponse> findByCrew(Long crewId) {
        validateCrewId(crewId);
        List<Reservation> reservations = reservationRepository.findByCrewId(crewId);
        return CrewFindOwnReservationResponse.from(reservations);
    }

    private void validateCrewId(Long crewId) {
        crewRepository.findById(crewId)
                .orElseThrow(NotFoundCrewException::new);
    }

    public CoachReservationsResponse findByCoachId(Long coachId) {
        validateCoachId(coachId);
        List<Reservation> reservations = reservationRepository.findByScheduleCoachIdAndStatusNot(coachId, DONE);
        new Reservations(reservations).updateStatus();
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
}

