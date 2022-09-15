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

    List<Reservation> findByScheduleCoachIdAndReservationStatusNotIn(Long coachId, List<ReservationStatus> statuses);

    @Query("SELECT r FROM Reservation AS r "
            + "WHERE r.schedule.coach.id = :coachId "
            + "AND r.reservationStatus NOT IN :status")
    List<Reservation> findAllByCoachIdAndStatusNot(Long coachId, ReservationStatus status);

    @Query("SELECT r FROM Reservation AS r "
            + "WHERE r.crew.id = :crewId "
            + "AND r.reservationStatus = :status "
            + "ORDER BY r.schedule.localDateTime DESC")
    List<Reservation> findByCrewIdAndReservationStatus(Long crewId,
                                                       ReservationStatus status);

    @Query("SELECT r FROM Reservation AS r "
            + "WHERE r.reservationStatus = 'APPROVED' "
            + "AND r.schedule.localDateTime < :endTime")
    List<Reservation> findAllApprovedReservationsBetween(LocalDateTime endTime);

    @Query("SELECT r FROM Reservation AS r "
            + "WHERE r.schedule.coach.id = :coachId "
            + "AND r.reservationStatus = :status")
    List<Reservation> findAllByCoachIdAndStatus(Long coachId, ReservationStatus status);

    @Query("SELECT r FROM Reservation AS r "
            + "WHERE r.reservationStatus = 'APPROVED' "
            + "AND r.sheetStatus = 'WRITING' "
            + "AND r.schedule.localDateTime >= :startTime "
            + "AND r.schedule.localDateTime < :endTime")
    List<Reservation> findAllShouldBeCanceled(LocalDateTime startTime, LocalDateTime endTime);
}
