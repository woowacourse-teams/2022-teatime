package com.woowacourse.teatime.teatime.repository;

import com.woowacourse.teatime.teatime.domain.Poke;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokeRepository extends JpaRepository<Poke, Long> {
}
