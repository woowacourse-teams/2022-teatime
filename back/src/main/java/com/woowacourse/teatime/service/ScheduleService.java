package com.woowacourse.teatime.service;

import com.woowacourse.teatime.controller.dto.ScheduleRequest;
import com.woowacourse.teatime.controller.dto.ScheduleResponse;
import com.woowacourse.teatime.controller.dto.ScheduleUpdateRequest;
import com.woowacourse.teatime.domain.Coach;
import com.woowacourse.teatime.domain.Schedule;
import com.woowacourse.teatime.domain.Schedules;
import com.woowacourse.teatime.exception.NotFoundCoachException;
import com.woowacourse.teatime.repository.CoachRepository;
import com.woowacourse.teatime.repository.ScheduleRepository;
import com.woowacourse.teatime.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public List<ScheduleResponse> find(Long coachId, ScheduleRequest request) {
        validateCoachId(coachId);

        LocalDateTime start = Date.findToday(request.getYear(), request.getMonth());
        LocalDateTime end = Date.findLastDay(request.getYear(), request.getMonth());
        Schedules schedules = getSchedules(coachId, start, end);

        return getScheduleResponses(schedules, schedules.findDays());
    }

    private void validateCoachId(Long id) {
        if (!coachRepository.existsById(id)) {
            throw new NotFoundCoachException();
        }
    }

    private Schedules getSchedules(Long coachId, LocalDateTime start, LocalDateTime end) {
        List<Schedule> schedules
                = scheduleRepository.findByCoachIdAndLocalDateTimeBetweenOrderByLocalDateTime(coachId, start, end);
        return new Schedules(schedules);
    }

    private List<ScheduleResponse> getScheduleResponses(Schedules schedules, List<Integer> days) {
        List<ScheduleResponse> scheduleResponses = new ArrayList<>();
        for (Integer day : days) {
            List<Schedule> schedulesByDay = schedules.findByDay(day);
            scheduleResponses.add(ScheduleResponse.of(day, schedulesByDay));
        }
        return scheduleResponses;
    }

    public void update(Long coachId, ScheduleUpdateRequest request) {
        deleteAllByCoachAndDate(coachId, request);
        saveAllByCoachAndDate(coachId, request);
    }

    private void deleteAllByCoachAndDate(Long coachId, ScheduleUpdateRequest request) {
        LocalDate date = request.getDate();
        LocalDateTime start = Date.findFirstTime(date);
        LocalDateTime end = Date.findLastTime(date);
        scheduleRepository.deleteAllByCoachIdAndLocalDateTimeBetween(coachId, start, end);
    }

    private void saveAllByCoachAndDate(Long coachId, ScheduleUpdateRequest request) {
        Coach coach = coachRepository.findById(coachId)
                .orElseThrow(NotFoundCoachException::new);
        List<Schedule> schedules = toSchedules(request, coach);
        scheduleRepository.saveAll(schedules);
    }

    private List<Schedule> toSchedules(ScheduleUpdateRequest request, Coach coach) {
        return request.getSchedules().stream()
                .map(schedule -> new Schedule(coach, schedule))
                .collect(Collectors.toList());
    }

}
