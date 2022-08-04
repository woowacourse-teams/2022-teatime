package com.woowacourse.teatime.repository;

import com.woowacourse.teatime.domain.Reservation;
import com.woowacourse.teatime.domain.ReservationStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findById(Long id);

    List<Reservation> findByCrewIdOrderByScheduleLocalDateTimeDesc(Long crewId);

    List<Reservation> findByScheduleCoachIdAndReservationStatusNot(Long coachId, ReservationStatus status);

    List<Reservation> findByCrewIdAndReservationStatusOrderByScheduleLocalDateTimeDesc(Long crewId,
                                                                                       ReservationStatus status);
}
