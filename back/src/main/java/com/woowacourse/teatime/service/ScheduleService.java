package com.woowacourse.teatime.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.teatime.NotFoundCoachException;
import com.woowacourse.teatime.controller.dto.ScheduleRequest;
import com.woowacourse.teatime.controller.dto.ScheduleResponse;
import com.woowacourse.teatime.controller.dto.ScheduleUpdateRequest;
import com.woowacourse.teatime.domain.Coach;
import com.woowacourse.teatime.domain.Schedule;
import com.woowacourse.teatime.domain.Schedules;
import com.woowacourse.teatime.repository.CoachRepository;
import com.woowacourse.teatime.repository.ScheduleRepository;
import com.woowacourse.teatime.util.Date;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CoachRepository coachRepository;

    @Transactional(readOnly = true)
    public List<ScheduleResponse> find(Long id, ScheduleRequest request) {
        validateCoachId(id);

        LocalDateTime start = Date.findToday(request.getYear(), request.getMonth());
        LocalDateTime end = Date.findLastDay(request.getYear(), request.getMonth());
        Schedules schedules = getSchedules(id, start, end);

        return getScheduleResponses(schedules, schedules.findDays());
    }

    private void validateCoachId(Long id) {
        if (!coachRepository.existsById(id)) {
            throw new NotFoundCoachException();
        }
    }

    private Schedules getSchedules(Long id, LocalDateTime start, LocalDateTime end) {
        List<Schedule> schedules
                = scheduleRepository.findByCoachIdAndLocalDateTimeBetweenOrderByLocalDateTime(id, start, end);
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

    public void update(Long id, ScheduleUpdateRequest request) {
        deleteAllByCoachAndDate(id, request);
        saveAllByCoachAndDate(id, request);
    }

    private void deleteAllByCoachAndDate(Long id, ScheduleUpdateRequest request) {
        LocalDate date = request.getDate();
        LocalDateTime start = Date.findFirstTime(date);
        LocalDateTime end = Date.findLastTime(date);
        scheduleRepository.deleteAllByCoachIdAndLocalDateTimeBetween(id, start, end);
    }

    private void saveAllByCoachAndDate(Long id, ScheduleUpdateRequest request) {
        Coach coach = coachRepository.findById(id)
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
