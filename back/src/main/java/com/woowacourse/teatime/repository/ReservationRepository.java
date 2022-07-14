package com.woowacourse.teatime.repository;

import com.woowacourse.teatime.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
