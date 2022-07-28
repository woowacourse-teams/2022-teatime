package com.woowacourse.teatime.domain;

import com.woowacourse.teatime.exception.AlreadyApprovedException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "crew_id")
    private Crew crew;

    @Column(name = "reservation_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public Reservation(Schedule schedule, Crew crew) {
        this.schedule = schedule;
        this.crew = crew;
        this.status = ReservationStatus.BEFORE_APPROVED;
    }

    public void confirm(boolean isApproved) {
        if (!ReservationStatus.isBeforeApproved(status)) {
            throw new AlreadyApprovedException();
        }
        if (isApproved) {
            status = ReservationStatus.APPROVED;
            return;
        }
        schedule.init();
    }
}

