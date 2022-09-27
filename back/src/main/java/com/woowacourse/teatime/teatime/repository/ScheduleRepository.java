package com.woowacourse.teatime.teatime.repository;

import com.woowacourse.teatime.teatime.domain.Schedule;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule AS s "
            + "INNER JOIN s.coach AS c "
            + "ON c.id = :coachId "
            + "WHERE s.localDateTime >= :start "
            + "AND s.localDateTime < :end")
    List<Schedule> findByCoachIdBetween(Long coachId, LocalDateTime start, LocalDateTime end);

    @Modifying
    @Query("DELETE FROM Schedule AS s "
            + "WHERE s.coach.id = :coachId "
            + "AND s.isPossible = true "
            + "AND s.localDateTime >= :start "
            + "AND s.localDateTime < :end")
    void deleteAllReservedByCoachIdBetween(Long coachId, LocalDateTime start, LocalDateTime end);
}
