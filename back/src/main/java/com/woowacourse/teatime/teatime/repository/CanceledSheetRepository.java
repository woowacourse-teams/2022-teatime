package com.woowacourse.teatime.teatime.repository;

import com.woowacourse.teatime.teatime.domain.CanceledSheet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CanceledSheetRepository extends JpaRepository<CanceledSheet, Long> {

    @Query("SELECT s FROM CanceledSheet AS s "
            + "WHERE s.reservation.originId = :originId ORDER BY s.number")
    List<CanceledSheet> findByOriginId(Long originId);
}
