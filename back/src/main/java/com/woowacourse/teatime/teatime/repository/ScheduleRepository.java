package com.woowacourse.teatime.teatime.repository;

import com.woowacourse.teatime.teatime.domain.Schedule;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByCoachIdAndLocalDateTimeBetweenOrderByLocalDateTime(Long coachId, LocalDateTime start,
                                                                            LocalDateTime end);

    void deleteAllByCoachIdAndLocalDateTimeBetween(Long coachId, LocalDateTime start, LocalDateTime end);

    void deleteAllByCoachIdAndLocalDateTimeBetweenAndIsPossibleNot(Long coachId, LocalDateTime start, LocalDateTime end,
                                                                   boolean isPossible);
}
