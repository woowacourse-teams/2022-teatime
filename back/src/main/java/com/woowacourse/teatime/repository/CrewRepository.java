package com.woowacourse.teatime.repository;

import com.woowacourse.teatime.domain.Crew;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewRepository extends JpaRepository<Crew, Long> {

    Optional<Crew> findByEmail(String email);
}
