package com.woowacourse.teatime.repository;

import com.woowacourse.teatime.domain.Schedule;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByCoachIdAndLocalDateTimeBetweenOrderByLocalDateTime(Long coachId, LocalDateTime start,
                                                                            LocalDateTime end);

    void deleteAllByCoachIdAndLocalDateTimeBetween(Long coachId, LocalDateTime start, LocalDateTime end);

    Optional<Schedule> findByIdAndCoachId(Long scheduleId, Long coachId);
}
