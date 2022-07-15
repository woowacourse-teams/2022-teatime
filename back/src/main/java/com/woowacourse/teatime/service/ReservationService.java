package com.woowacourse.teatime.service;

import com.woowacourse.teatime.NotExistedCrewException;
import com.woowacourse.teatime.NotFoundScheduleException;
import com.woowacourse.teatime.domain.Crew;
import com.woowacourse.teatime.domain.Reservation;
import com.woowacourse.teatime.domain.Schedule;
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

    public Reservation save(Long crewId, Long coachId, Long scheduleId) {
        Crew crew = crewRepository.findById(crewId)
                .orElseThrow(NotExistedCrewException::new);
        Schedule schedule = scheduleRepository.findByIdAndCoachId(scheduleId, coachId)
                .orElseThrow(NotFoundScheduleException::new);

        schedule.reserve();
        return reservationRepository.save(new Reservation(schedule, crew));
    }
}

