package com.woowacourse.teatime.teatime.repository;

import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.repository.dto.CoachWithPossible;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CoachRepository extends JpaRepository<Coach, Long> {

    Optional<Coach> findByEmail(String email);

    @Query(value = "SELECT c.id AS id, c.name AS name, c.description AS description, c.image AS image, EXISTS ("
            + "SELECT * FROM schedule s2 WHERE s2.coach_id = id AND s2.is_possible = TRUE) AS possible "
            + "FROM coach c "
            + "LEFT JOIN schedule s "
            + "ON s.coach_id = c.id "
            + "GROUP BY c.id", nativeQuery = true)
    List<CoachWithPossible> findCoaches();
}
