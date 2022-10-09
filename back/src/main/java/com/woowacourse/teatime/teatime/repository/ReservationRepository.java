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

    @Query("SELECT r FROM Reservation AS r "
            + "INNER JOIN r.crew AS c "
            + "ON c.id = :crewId "
            + "INNER JOIN r.schedule AS s "
            + "ORDER BY s.localDateTime DESC")
    List<Reservation> findAllByCrewIdLatestOrder(Long crewId);

    @Query("SELECT r FROM Reservation AS r "
            + "JOIN FETCH r.schedule AS s "
            + "JOIN FETCH r.crew AS cr "
            + "INNER JOIN s.coach AS co "
            + "ON co.id = :coachId "
            + "WHERE r.reservationStatus NOT IN :status")
    List<Reservation> findAllByCoachIdAndStatusNot(Long coachId, ReservationStatus status);

    @Query("SELECT r FROM Reservation AS r "
            + "JOIN FETCH r.schedule AS s "
            + "JOIN FETCH s.coach AS co "
            + "INNER JOIN r.crew AS cr "
            + "ON cr.id = :crewId "
            + "WHERE r.reservationStatus = :status "
            + "ORDER BY s.localDateTime DESC")
    List<Reservation> findAllByCrewIdAndReservationStatus(Long crewId, ReservationStatus status);

    @Query("SELECT r FROM Reservation AS r "
            + "INNER JOIN r.schedule AS s "
            + "WHERE r.reservationStatus = 'APPROVED' "
            + "AND s.localDateTime < :endTime")
    List<Reservation> findAllApprovedReservationsBefore(LocalDateTime endTime);

    @Query("SELECT r FROM Reservation AS r "
            + "INNER JOIN r.schedule AS s "
            + "WHERE r.reservationStatus = 'APPROVED' "
            + "AND s.localDateTime >= :startTime "
            + "AND s.localDateTime < :endTime")
    List<Reservation> findAllApprovedReservationsBetween(LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT r FROM Reservation AS r "
            + "JOIN FETCH r.schedule AS s "
            + "JOIN FETCH r.crew AS cr "
            + "INNER JOIN s.coach AS co "
            + "ON co.id = :coachId "
            + "WHERE r.reservationStatus = :status")
    List<Reservation> findAllByCoachIdAndStatus(Long coachId, ReservationStatus status);
}
