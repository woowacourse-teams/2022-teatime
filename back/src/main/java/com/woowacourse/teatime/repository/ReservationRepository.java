package com.woowacourse.teatime.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowacourse.teatime.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findById(Long id);
}
