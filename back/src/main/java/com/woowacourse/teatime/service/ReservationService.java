package com.woowacourse.teatime.service;

import com.woowacourse.teatime.controller.dto.request.ReservationApproveRequest;
import com.woowacourse.teatime.controller.dto.request.ReservationReserveRequest;
import com.woowacourse.teatime.controller.dto.response.CrewFindOwnReservationResponse;
import com.woowacourse.teatime.domain.Crew;
import com.woowacourse.teatime.domain.Reservation;
import com.woowacourse.teatime.domain.Schedule;
import com.woowacourse.teatime.exception.NotFoundCrewException;
import com.woowacourse.teatime.exception.NotFoundReservationException;
import com.woowacourse.teatime.exception.NotFoundScheduleException;
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
        validateReservation(reservationApproveRequest.getCoachId(), reservation);

        reservation.confirm(reservationApproveRequest.getIsApproved());
        if (!reservationApproveRequest.getIsApproved()) {
            reservationRepository.delete(reservation);
        }
    }

    private void validateReservation(Long coachId, Reservation reservation) {
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
}

