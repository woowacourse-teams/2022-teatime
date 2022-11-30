package com.woowacourse.teatime.teatime.repository;

import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.repository.dto.CoachWithPossible;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CoachRepository extends JpaRepository<Coach, Long> {

    Optional<Coach> findByEmail(String email);

    @Query("SELECT c.id AS id, c.name AS name, c.description AS description, c.image AS image, c.isPokable AS pokable, EXISTS ("
            + "SELECT s2.id FROM Schedule s2 WHERE s2.coach.id = c.id AND s2.localDateTime > NOW() AND s2.isPossible = TRUE ) AS possible "
            + "FROM Coach c")
    List<CoachWithPossible> findCoaches();
}
