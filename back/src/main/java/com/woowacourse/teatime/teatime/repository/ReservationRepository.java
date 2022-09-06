package com.woowacourse.teatime.teatime.repository;

import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.domain.ReservationStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findById(Long id);

    List<Reservation> findByCrewIdOrderByScheduleLocalDateTimeDesc(Long crewId);

    List<Reservation> findByScheduleCoachIdAndReservationStatusNot(Long coachId, ReservationStatus status);

    List<Reservation> findByCrewIdAndReservationStatusOrderByScheduleLocalDateTimeDesc(Long crewId,
                                                                                       ReservationStatus status);

    List<Reservation> findAllByReservationStatus(ReservationStatus status);

    @Query("SELECT r FROM Reservation AS r WHERE r.reservationStatus = 'APPROVED' AND r.sheetStatus = 'WRITING' AND r.schedule.localDateTime >= :startTime AND r.schedule.localDateTime < :endTime")
    List<Reservation> findAllShouldBeCanceled(LocalDateTime startTime, LocalDateTime endTime);
}
