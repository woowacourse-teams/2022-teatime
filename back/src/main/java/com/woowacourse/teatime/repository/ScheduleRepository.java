package com.woowacourse.teatime.repository;

import com.woowacourse.teatime.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
