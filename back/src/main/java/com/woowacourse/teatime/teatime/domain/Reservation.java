package com.woowacourse.teatime.teatime.domain;

import com.woowacourse.teatime.teatime.exception.AlreadyApprovedException;
import com.woowacourse.teatime.teatime.exception.UnableToCancelReservationException;
import com.woowacourse.teatime.teatime.exception.UnableToDoneReservationException;
import com.woowacourse.teatime.teatime.exception.UnableToInProgressReservationException;
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

    private static final String NOT_COME_RESERVATION_TIME_MESSAGE = "면담 시간이 아직 되지 않아 진행중으로 상태를 변경할 수 없습니다.";
    private static final String NOT_APPROVED_ERROR_MESSAGE = "승인된 상태가 아니기에 진행중으로 상태를 변경할 수 없습니다.";

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
        reservationStatus = ReservationStatus.CANCELED;
        schedule.init();
    }

    public void cancel(Role role) {
        if (isCancelBeforeApprovedByCoach(role) || isCancelInProgressByCrew(role)) {
            throw new UnableToCancelReservationException();
        }
        reservationStatus = ReservationStatus.CANCELED;
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

    public boolean isSameCoach(Long coachId) {
        return getCoach().getId().equals(coachId);
    }

    public void updateReservationStatusToInProgress() {
        validateCanUpdateToInProgress();
        reservationStatus = ReservationStatus.IN_PROGRESS;
    }

    private void validateCanUpdateToInProgress() {
        if (!isBeforeFromNow()) {
            throw new UnableToInProgressReservationException(NOT_COME_RESERVATION_TIME_MESSAGE);
        }
        if (!isReservationStatus(ReservationStatus.APPROVED)) {
            throw new UnableToInProgressReservationException(NOT_APPROVED_ERROR_MESSAGE);
        }
    }

    public boolean isBeforeFromNow() {
        return getScheduleDateTime().isBefore(LocalDateTime.now());
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

    public Coach getCoach() {
        return schedule.getCoach();
    }
}

