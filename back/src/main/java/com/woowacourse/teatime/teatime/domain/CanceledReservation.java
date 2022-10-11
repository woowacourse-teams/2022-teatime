package com.woowacourse.teatime.teatime.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class CanceledReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long originId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coach_id")
    private Coach coach;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crew_id")
    private Crew crew;

    @Column(nullable = false)
    private LocalDateTime originSchedule;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    private CanceledReservation(Long originId, Coach coach, Crew crew, LocalDateTime originSchedule) {
        this.originId = originId;
        this.coach = coach;
        this.crew = crew;
        this.originSchedule = originSchedule;
    }

    public static CanceledReservation from(Reservation reservation) {
        return new CanceledReservation(
                reservation.getId(),
                reservation.getCoach(),
                reservation.getCrew(),
                reservation.getScheduleDateTime()
        );
    }

    public boolean isSameCrew(Long crewId) {
        return crew.getId().equals(crewId);
    }
}
