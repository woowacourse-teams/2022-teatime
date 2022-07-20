package com.woowacourse.teatime.service;

import com.woowacourse.teatime.domain.Crew;
import com.woowacourse.teatime.domain.Reservation;
import com.woowacourse.teatime.domain.Schedule;
import com.woowacourse.teatime.exception.AlreadyApprovedException;
import com.woowacourse.teatime.exception.NotExistedCrewException;
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

    public Long save(Long crewId, Long coachId, Long scheduleId) {
        Crew crew = crewRepository.findById(crewId)
                .orElseThrow(NotExistedCrewException::new);
        Schedule schedule = scheduleRepository.findByIdAndCoachId(scheduleId, coachId)
                .orElseThrow(NotFoundScheduleException::new);

        schedule.reserve();
        Reservation reservation = reservationRepository.save(new Reservation(schedule, crew));
        return reservation.getId();
    }

    public void approve(Long coachId, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(NotFoundReservationException::new);
        validateStatus(reservation);
        validateReservation(coachId, reservation);

        reservation.approve();
    }

    private void validateStatus(Reservation reservation) {
        if (reservation.isApproved()) {
            throw new AlreadyApprovedException();
        }
    }

    private void validateReservation(Long coachId, Reservation reservation) {
        Schedule schedule = reservation.getSchedule();
        if (!schedule.isSameCoach(coachId)) {
            throw new NotFoundReservationException();
        }
    }
}

