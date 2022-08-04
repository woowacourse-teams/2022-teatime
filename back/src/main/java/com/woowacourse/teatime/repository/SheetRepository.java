package com.woowacourse.teatime.repository;

import com.woowacourse.teatime.domain.Sheet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SheetRepository extends JpaRepository<Sheet, Long> {

    List<Sheet> findByReservationIdOrderByNumber(Long reservationId);
}
