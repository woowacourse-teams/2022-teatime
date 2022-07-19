package com.woowacourse.teatime.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.teatime.domain.Crew;
import com.woowacourse.teatime.domain.Reservation;
import com.woowacourse.teatime.domain.Schedule;
import com.woowacourse.teatime.exception.NotExistedCrewException;
import com.woowacourse.teatime.exception.NotFoundReservationException;
import com.woowacourse.teatime.exception.NotFoundScheduleException;
import com.woowacourse.teatime.repository.CrewRepository;
import com.woowacourse.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.repository.ScheduleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CrewRepository crewRepository;
    private final ScheduleRepository scheduleRepository;

    public Reservation save(Long crewId, Long coachId, Long scheduleId) {
        Crew crew = crewRepository.findById(crewId)
                .orElseThrow(NotExistedCrewException::new);
        Schedule schedule = scheduleRepository.findByIdAndCoachId(scheduleId, coachId)
                .orElseThrow(NotFoundScheduleException::new);

        schedule.reserve();
        return reservationRepository.save(new Reservation(schedule, crew));
    }

    public void approve(Long coachId, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(NotFoundReservationException::new);
        validateReservation(coachId, reservation);

        reservation.approve();
    }

    private void validateReservation(Long coachId, Reservation reservation) {
        Schedule schedule = reservation.getSchedule();
        if (!schedule.isSameCoach(coachId)) {
            throw new NotFoundReservationException();
        }
    }
}

