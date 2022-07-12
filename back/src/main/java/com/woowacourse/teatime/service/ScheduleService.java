package com.woowacourse.teatime.service;

import com.woowacourse.teatime.controller.dto.ScheduleRequest;
import com.woowacourse.teatime.controller.dto.ScheduleResponse;
import com.woowacourse.teatime.domain.Schedule;
import com.woowacourse.teatime.domain.Schedules;
import com.woowacourse.teatime.repository.ScheduleRepository;
import com.woowacourse.teatime.util.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public List<ScheduleResponse> find(Long id, ScheduleRequest request) {
        LocalDateTime start = Date.findFirstDay(request.getYear(), request.getMonth());
        LocalDateTime end = Date.findEndDay(request.getYear(), request.getMonth());
        Schedules schedules = getSchedules(id, start, end);

        return getScheduleResponses(schedules, schedules.findDays());
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
}
