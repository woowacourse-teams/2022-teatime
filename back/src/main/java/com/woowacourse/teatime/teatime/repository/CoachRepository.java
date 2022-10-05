package com.woowacourse.teatime.teatime.repository;

import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.repository.dto.CoachWithPossible;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CoachRepository extends JpaRepository<Coach, Long> {

    Optional<Coach> findByEmail(String email);

    @Query(name = "findCoaches", nativeQuery = true)
    List<CoachWithPossible> findCoaches();
}
