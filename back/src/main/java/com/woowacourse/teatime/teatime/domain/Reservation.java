package com.woowacourse.teatime.teatime.domain;

import com.woowacourse.teatime.teatime.exception.AlreadyApprovedException;
import com.woowacourse.teatime.teatime.exception.UnCancellableReservationException;
import com.woowacourse.teatime.teatime.exception.UnableToDoneReservationException;
import com.woowacourse.teatime.teatime.exception.UnableToSubmitSheetException;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        this.reservationStatus = ReservationStatus.BEFORE_APPROVED;
        this.sheetStatus = SheetStatus.WRITING;
    }

    public void confirm(boolean isApproved) {
        if (!isReservationStatus(ReservationStatus.BEFORE_APPROVED)) {
            throw new AlreadyApprovedException();
        }
        if (isApproved) {
            reservationStatus = ReservationStatus.APPROVED;
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
        return role.isCoach() && isReservationStatus(ReservationStatus.BEFORE_APPROVED);
    }

    private boolean isCancelInProgressByCrew(Role role) {
        return isReservationStatus(ReservationStatus.IN_PROGRESS) && role.isCrew();
    }

    public boolean isSameCrew(Long crewId) {
        return crew.getId().equals(crewId);
    }

    public void updateReservationStatusToInProgress() {
        if (isReservationStatus(ReservationStatus.APPROVED) && isTimePassed()) {
            reservationStatus = ReservationStatus.IN_PROGRESS;
        }
    }

    private boolean isTimePassed() {
        log.info(LocalDateTime.now().toString());
        return LocalDateTime.now().isAfter(getScheduleDateTime());
    }

    public void updateReservationStatusToDone() {
        if (!isReservationStatus(ReservationStatus.IN_PROGRESS)) {
            throw new UnableToDoneReservationException();
        }
        reservationStatus = ReservationStatus.DONE;
    }

    public boolean isReservationStatus(ReservationStatus status) {
        return this.reservationStatus.equals(status);
    }

    public void updateSheetStatusToSubmitted() {
        if (sheetStatus.equals(SheetStatus.SUBMITTED)) {
            throw new UnableToSubmitSheetException();
        }
        sheetStatus = SheetStatus.SUBMITTED;
    }

    public LocalDateTime getScheduleDateTime() {
        return schedule.getLocalDateTime();
    }
}

