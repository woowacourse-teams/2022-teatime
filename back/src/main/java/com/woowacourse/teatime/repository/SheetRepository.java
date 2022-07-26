package com.woowacourse.teatime.repository;

import com.woowacourse.teatime.domain.Sheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SheetRepository extends JpaRepository<Sheet, Long> {

}
