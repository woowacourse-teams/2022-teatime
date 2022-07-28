package com.woowacourse.teatime.service;

import com.woowacourse.teatime.controller.dto.ReservationApproveRequest;
import com.woowacourse.teatime.controller.dto.ReservationCancelRequest;
import com.woowacourse.teatime.controller.dto.ReservationRequest;
import com.woowacourse.teatime.domain.Crew;
import com.woowacourse.teatime.domain.Reservation;
import com.woowacourse.teatime.domain.Role;
import com.woowacourse.teatime.domain.Schedule;
import com.woowacourse.teatime.exception.NotFoundCrewException;
import com.woowacourse.teatime.exception.NotFoundReservationException;
import com.woowacourse.teatime.exception.NotFoundScheduleException;
import com.woowacourse.teatime.repository.CrewRepository;
import com.woowacourse.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.repository.ScheduleRepository;
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

    public Long save(ReservationRequest reservationRequest) {
        Crew crew = crewRepository.findById(reservationRequest.getCrewId())
                .orElseThrow(NotFoundCrewException::new);
        Schedule schedule = scheduleRepository.findByIdAndCoachId(
                reservationRequest.getScheduleId(), reservationRequest.getCoachId())
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
        reservation.cancel();
        reservationRepository.delete(reservation);
    }

    private void validateAuthorization(ReservationCancelRequest reservationCancelRequest, Role role,
                                       Reservation reservation) {
        if (Role.isCoach(role)) {
            Long coachId = reservationCancelRequest.getApplicantId();
            validateIsSameCoach(coachId, reservation);
        }
        if (Role.isCrew(role)) {
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
}

