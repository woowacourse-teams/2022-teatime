package com.woowacourse.teatime.teatime.service;

import com.woowacourse.teatime.teatime.controller.dto.request.ScheduleFindRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.ScheduleUpdateRequest;
import com.woowacourse.teatime.teatime.controller.dto.response.ScheduleFindResponse;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Schedule;
import com.woowacourse.teatime.teatime.exception.NotFoundCoachException;
import com.woowacourse.teatime.teatime.exception.UnableToUpdateScheduleException;
import com.woowacourse.teatime.teatime.repository.CoachRepository;
import com.woowacourse.teatime.teatime.repository.ScheduleRepository;
import com.woowacourse.teatime.teatime.repository.jdbc.ScheduleDao;
import com.woowacourse.teatime.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleDao scheduleDao;
    private final CoachRepository coachRepository;

    public Long save(Long coachId, LocalDateTime dateTime) {
        Coach coach = coachRepository.findById(coachId)
                .orElseThrow(NotFoundCoachException::new);
        Schedule schedule = scheduleRepository.save(new Schedule(coach, dateTime));
        return schedule.getId();
    }

    @Transactional(readOnly = true)
    public List<ScheduleFindResponse> find(Long coachId, ScheduleFindRequest request) {
        findCoach(coachId);

        LocalDateTime start = Date.findFirstDateTime(request.getYear(), request.getMonth());
        LocalDateTime end = Date.findLastDateTime(request.getYear(), request.getMonth());
        List<Schedule> schedules
                = scheduleRepository.findAllByCoachIdBetween(coachId, start, end);
        return ScheduleFindResponse.from(schedules);
    }

    public void update(Long coachId, List<ScheduleUpdateRequest> requests) {
        List<LocalDateTime> localDateTimes = requests.stream()
                .map(ScheduleUpdateRequest::getSchedules)
                .flatMap(Collection::stream)
                .sorted()
                .collect(Collectors.toList());
        validateDeletable(coachId, localDateTimes);

        List<LocalDate> days = requests.stream()
                .map(ScheduleUpdateRequest::getDate)
                .collect(Collectors.toList());

        deleteAllByCoachAndDate(coachId, days);
        saveAllByCoachAndDate(coachId, localDateTimes);
    }

    private void deleteAllByCoachAndDate(Long coachId, List<LocalDate> localDates) {
        List<String> days = localDates.stream()
                .map(String::valueOf)
                .collect(Collectors.toList());

        scheduleRepository.deleteAllReservableByCoachIdBetween(coachId, days);
    }

    private void validateDeletable(Long coachId, List<LocalDateTime> localDateTimes) {
        boolean result = scheduleRepository.isExistReservedSchedules(coachId, localDateTimes);
        if (result) {
            throw new UnableToUpdateScheduleException();
        }
    }

    private void saveAllByCoachAndDate(Long coachId, List<LocalDateTime> localDateTimes) {
        Coach coach = findCoach(coachId);
        List<Schedule> schedules = toSchedules(localDateTimes, coach);
        scheduleDao.saveAll(schedules);
    }

    private Coach findCoach(Long id) {
        return coachRepository.findById(id)
                .orElseThrow(NotFoundCoachException::new);
    }

    private List<Schedule> toSchedules(List<LocalDateTime> localDateTimes, Coach coach) {
        return localDateTimes.stream()
                .map(schedule -> new Schedule(coach, schedule))
                .collect(Collectors.toList());
    }
}
