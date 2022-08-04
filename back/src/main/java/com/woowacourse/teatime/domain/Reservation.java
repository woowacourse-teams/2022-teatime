package com.woowacourse.teatime.domain;

import static com.woowacourse.teatime.domain.ReservationStatus.APPROVED;
import static com.woowacourse.teatime.domain.ReservationStatus.BEFORE_APPROVED;
import static com.woowacourse.teatime.domain.ReservationStatus.DONE;
import static com.woowacourse.teatime.domain.ReservationStatus.IN_PROGRESS;
import static com.woowacourse.teatime.domain.SheetStatus.WRITING;

import com.woowacourse.teatime.exception.AlreadyApprovedException;
import com.woowacourse.teatime.exception.UnCancellableReservationException;
import com.woowacourse.teatime.exception.UnableToDoneReservationException;
import java.time.LocalDateTime;
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SheetStatus sheetStatus;

    public Reservation(Schedule schedule, Crew crew) {
        this.schedule = schedule;
        this.crew = crew;
        this.reservationStatus = BEFORE_APPROVED;
        this.sheetStatus = WRITING;
    }

    public void confirm(boolean isApproved) {
        if (!isSameStatus(BEFORE_APPROVED)) {
            throw new AlreadyApprovedException();
        }
        if (isApproved) {
            reservationStatus = APPROVED;
            return;
        }
        schedule.init();
    }

    public void cancel(Role role) {
        if (isCancelBeforeApprovedByCoach(role) || isCancelInProgressByCrew(role)) {
            throw new UnCancellableReservationException();
        }
        schedule.init();
    }

    private boolean isCancelBeforeApprovedByCoach(Role role) {
        return role.isCoach() && isSameStatus(BEFORE_APPROVED);
    }

    private boolean isCancelInProgressByCrew(Role role) {
        return isSameStatus(IN_PROGRESS) && role.isCrew();
    }

    public boolean isSameCrew(Long crewId) {
        return crew.getId().equals(crewId);
    }

    public void updateStatusToInProgress() {
        if (isSameStatus(APPROVED) && isTimePassed()) {
            reservationStatus = IN_PROGRESS;
        }
    }

    private boolean isTimePassed() {
        return LocalDateTime.now().isAfter(getScheduleDateTime());
    }

    public void updateStatusToDone() {
        if (!isSameStatus(IN_PROGRESS)) {
            throw new UnableToDoneReservationException();
        }
        reservationStatus = DONE;
    }

    public boolean isSameStatus(ReservationStatus status) {
        return this.reservationStatus.equals(status);
    }

    public LocalDateTime getScheduleDateTime() {
        return schedule.getLocalDateTime();
    }
}

