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

        LocalDateTime start = Date.findFirstDateTime(request.getYear(), request.getMonth());
        LocalDateTime end = Date.findLastDay(request.getYear(), request.getMonth());
        List<Schedule> schedules
                = scheduleRepository.findByCoachIdBetween(coachId, start, end);
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

        scheduleRepository.deleteAllReservableByCoachIdBetween(coachId, start, end);
    }

    private void validateDeletable(Long coachId, ScheduleUpdateRequest request, LocalDateTime start,
                                   LocalDateTime end) {
        Coach coach = findCoach(coachId);
        List<Schedule> newSchedules = toSchedules(request, coach);
        List<Schedule> oldSchedules = scheduleRepository
                .findByCoachIdBetween(coachId, start, end);

        for (Schedule schedule : oldSchedules) {
            validateIsReserved(newSchedules, schedule);
        }
    }

    private void validateIsReserved(List<Schedule> newSchedules, Schedule schedule) {
        if ((newSchedules.contains(schedule) && !schedule.isPossible())) {
            throw new UnableToUpdateScheduleException();
        }
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
