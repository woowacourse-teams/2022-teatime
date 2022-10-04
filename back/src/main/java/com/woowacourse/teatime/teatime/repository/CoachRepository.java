package com.woowacourse.teatime.teatime.repository;

import com.woowacourse.teatime.teatime.domain.Coach;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachRepository extends JpaRepository<Coach, Long> {

    Optional<Coach> findByEmail(String email);
}
