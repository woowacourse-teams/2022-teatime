package com.woowacourse.teatime.service;

import com.woowacourse.teatime.controller.dto.request.ScheduleFindRequest;
import com.woowacourse.teatime.controller.dto.request.ScheduleUpdateRequest;
import com.woowacourse.teatime.controller.dto.response.ScheduleFindResponse;
import com.woowacourse.teatime.domain.Coach;
import com.woowacourse.teatime.domain.Schedule;
import com.woowacourse.teatime.exception.NotFoundCoachException;
import com.woowacourse.teatime.exception.UnableToUpdateSchedule;
import com.woowacourse.teatime.repository.CoachRepository;
import com.woowacourse.teatime.repository.ScheduleRepository;
import com.woowacourse.teatime.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

        LocalDateTime start = Date.findFirstDay(request.getYear(), request.getMonth());
        LocalDateTime end = Date.findLastDay(request.getYear(), request.getMonth());
        List<Schedule> schedules
                = scheduleRepository.findByCoachIdAndLocalDateTimeBetweenOrderByLocalDateTime(coachId, start, end);
        return ScheduleFindResponse.from(schedules);
    }

    public void update(Long coachId, ScheduleUpdateRequest request) {
        deleteAllByCoachAndDate(coachId, request);
        saveAllByCoachAndDate(coachId, request);
    }

    private void deleteAllByCoachAndDate(Long coachId, ScheduleUpdateRequest request) {
        LocalDate date = request.getDate();
        LocalDateTime start = Date.findFirstTime(date);
        LocalDateTime end = Date.findLastTime(date);
        validateDeletable(coachId, request, start, end);

        scheduleRepository.deleteAllByCoachIdAndLocalDateTimeBetween(coachId, start, end);
    }

    private void validateDeletable(Long coachId, ScheduleUpdateRequest request, LocalDateTime start,
                                   LocalDateTime end) {
        Coach coach = findCoach(coachId);
        List<Schedule> newSchedules = toSchedules(request, coach);
        List<Schedule> oldSchedules = scheduleRepository
                .findByCoachIdAndLocalDateTimeBetweenOrderByLocalDateTime(coachId, start, end);

        boolean isImpossibleToDelete = oldSchedules.stream()
                .anyMatch(schedule -> isReserved(newSchedules, schedule));
        if (isImpossibleToDelete) {
            throw new UnableToUpdateSchedule();
        }
    }

    private boolean isReserved(List<Schedule> newSchedules, Schedule schedule) {
        return !(newSchedules.contains(schedule) || schedule.isPossible());
    }

    private void saveAllByCoachAndDate(Long coachId, ScheduleUpdateRequest request) {
        Coach coach = findCoach(coachId);
        List<Schedule> schedules = toSchedules(request, coach);
        scheduleRepository.saveAll(schedules);
    }

    private Coach findCoach(Long id) {
        return coachRepository.findById(id)
                .orElseThrow(NotFoundCoachException::new);
    }

    private List<Schedule> toSchedules(ScheduleUpdateRequest request, Coach coach) {
        return request.getSchedules().stream()
                .map(schedule -> new Schedule(coach, schedule))
                .collect(Collectors.toList());
    }
}
