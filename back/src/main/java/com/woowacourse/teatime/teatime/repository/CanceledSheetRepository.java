package com.woowacourse.teatime.teatime.repository;

import com.woowacourse.teatime.teatime.domain.CanceledSheet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CanceledSheetRepository extends JpaRepository<CanceledSheet, Long> {

    @Query("SELECT s FROM CanceledSheet AS s "
            + "INNER JOIN s.canceledReservation AS r "
            + "ON r.originId = :originReservationId "
            + "ORDER BY s.number")
    List<CanceledSheet> findAllByOriginId(Long originReservationId);
}
