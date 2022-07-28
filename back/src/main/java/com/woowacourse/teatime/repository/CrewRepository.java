package com.woowacourse.teatime.repository;

import com.woowacourse.teatime.domain.Crew;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewRepository extends JpaRepository<Crew, Long> {
}
