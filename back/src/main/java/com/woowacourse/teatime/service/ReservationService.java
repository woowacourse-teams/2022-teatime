package com.woowacourse.teatime.service;

import com.woowacourse.teatime.NotExistedCrewException;
import com.woowacourse.teatime.NotExistedScheduleException;
import com.woowacourse.teatime.NotMatchedIdException;
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

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(NotExistedScheduleException::new);

        schedule.reserve();

        if (!schedule.isSameCoach(coachId)) {
            throw new NotMatchedIdException("해당 스케줄의 코치 아이디가 아닙니다.");
        }

        return reservationRepository.save(new Reservation(schedule, crew));
    }
}

