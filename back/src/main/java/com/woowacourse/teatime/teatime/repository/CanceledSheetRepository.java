package com.woowacourse.teatime.teatime.repository;

import com.woowacourse.teatime.teatime.domain.CanceledSheet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CanceledSheetRepository extends JpaRepository<CanceledSheet, Long> {

    List<CanceledSheet> findByReservationIdOrderByNumber(Long reservationId);
}
