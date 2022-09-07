package com.woowacourse.teatime.teatime.domain;

import static com.woowacourse.teatime.teatime.domain.ReservationStatus.DONE;
import static com.woowacourse.teatime.teatime.domain.ReservationStatus.IN_PROGRESS;
import static com.woowacourse.teatime.teatime.domain.SheetStatus.SUBMITTED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.teatime.teatime.exception.AlreadyApprovedException;
import com.woowacourse.teatime.teatime.exception.UnableToCancelReservationException;
import com.woowacourse.teatime.teatime.exception.UnableToDoneReservationException;
import com.woowacourse.teatime.teatime.exception.UnableToInProgressReservationException;
import com.woowacourse.teatime.teatime.exception.UnableToSubmitSheetException;
import com.woowacourse.teatime.teatime.fixture.DomainFixture;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    private static final boolean 승인을_한다 = true;
    private static final boolean 승인을_거절한다 = false;

    private Reservation reservation;
    private Schedule schedule;

    @BeforeEach
    void setUp() {
        schedule = new Schedule(DomainFixture.COACH_BROWN, DomainFixture.DATE_TIME);
        this.reservation = new Reservation(schedule, DomainFixture.CREW);
    }

    @DisplayName("면담을 승인한다.")
    @Test
    void confirm_approve() {
        reservation.confirm(승인을_한다);

        assertThat(reservation.getReservationStatus()).isEqualTo(ReservationStatus.APPROVED);
    }

    @DisplayName("승인 전, 면담을 취소한다.")
    @Test
    void conform_denyApproval() {
        reservation.confirm(승인을_거절한다);

        assertThat(schedule.getIsPossible()).isTrue();
    }

    @DisplayName("승인이 되어 있는 상태에서 승인 요청을 하면 에러가 발생한다.")
    @Test
    void confirm_invalid() {
        reservation.confirm(승인을_한다);

        assertThatThrownBy(() -> reservation.confirm(승인을_한다))
                .isInstanceOf(AlreadyApprovedException.class);
    }

    @DisplayName("코치가 예약을 취소할 수 있다.")
    @Test
    void cancel_coach() {
        reservation.confirm(승인을_한다);
        reservation.cancel(Role.COACH);

        assertThat(schedule.getIsPossible()).isTrue();
    }

    @DisplayName("크루가 예약을 취소할 수 있다.")
    @Test
    void cancel_crew() {
        reservation.confirm(승인을_한다);
        reservation.cancel(Role.CREW);

        assertThat(schedule.getIsPossible()).isTrue();
    }

    @DisplayName("크루가 승인되지 않은 상태의 예약을 취소할 수 있다.")
    @Test
    void cancel_notApprovedReservation() {
        reservation.cancel(Role.CREW);

        assertThat(schedule.getIsPossible()).isTrue();
    }

    @DisplayName("코치가 예약을 취소할 때, 상태가 승인상태가 아닌 경우 에러가 발생한다.")
    @Test
    void cancel_invalidCancelException() {
        assertThatThrownBy(() -> reservation.cancel(Role.COACH))
                .isInstanceOf(UnableToCancelReservationException.class);

    }

    @DisplayName("코치가 진행 중인 면담을 취소할 수 있다.")
    @Test
    void cancel_byCoach_inProgressReservation() {
        Reservation reservation = new Reservation(schedule, DomainFixture.CREW);
        reservation.confirm(승인을_한다);
        reservation.updateReservationStatusToInProgress();

        reservation.cancel(Role.COACH);

        assertThat(schedule.getIsPossible()).isTrue();
    }

    @DisplayName("크루가 진행 중인 면담을 취소할 수 없다.")
    @Test
    void cancel_byCrew_inProgressReservation() {
        Reservation reservation = new Reservation(schedule, DomainFixture.CREW);
        reservation.confirm(승인을_한다);
        reservation.updateReservationStatusToInProgress();

        assertThatThrownBy(() -> reservation.cancel(Role.CREW))
                .isInstanceOf(UnableToCancelReservationException.class);
    }

    @DisplayName("예약 시간이 되면 승인된 면담이 진행중인 상태로 업데이트 된다.")
    @Test
    void updateReservationStatusToInProgress() {
        Reservation reservation = new Reservation(schedule, DomainFixture.CREW);
        reservation.confirm(승인을_한다);

        reservation.updateReservationStatusToInProgress();

        assertThat(reservation.isReservationStatus(IN_PROGRESS)).isTrue();
    }

    @DisplayName("예약 시간이 되어도 승인되지 않은 면담은 진행중인 상태로 업데이트 되지 않는다.")
    @Test
    void updateReservationStatusToInProgress_unapprovedReservation() {
        Reservation reservation = new Reservation(schedule, DomainFixture.CREW);

        assertThatThrownBy(reservation::updateReservationStatusToInProgress)
                .isInstanceOf(UnableToInProgressReservationException.class);
    }

    @DisplayName("승인된 면담이 아직 시간이 안됐으면 진행중인 상태로 업데이트 되지 않는다.")
    @Test
    void updateReservationStatusToInProgress_noTimeYet() {
        Schedule schedule = new Schedule(DomainFixture.COACH_BROWN, LocalDateTime.now().plusDays(1L));
        Reservation reservation = new Reservation(schedule, DomainFixture.CREW);
        reservation.confirm(승인을_한다);

        assertThatThrownBy(reservation::updateReservationStatusToInProgress)
                .isInstanceOf(UnableToInProgressReservationException.class);
    }

    @DisplayName("진행중인 면담을 종료하면 완료된 상태로 업데이트 된다.")
    @Test
    void updateReservationStatusToDone() {
        Reservation reservation = new Reservation(schedule, DomainFixture.CREW);
        reservation.confirm(승인을_한다);
        reservation.updateReservationStatusToInProgress();

        reservation.updateReservationStatusToDone();

        assertThat(reservation.isReservationStatus(DONE)).isTrue();
    }

    @DisplayName("승인되지 않은 면담을 종료하면 예외가 발생한다.")
    @Test
    void updateReservationStatusToDone_unableToDoneException_unapproved() {
        Reservation reservation = new Reservation(schedule, DomainFixture.CREW);

        assertThatThrownBy(reservation::updateReservationStatusToDone)
                .isInstanceOf(UnableToDoneReservationException.class);
    }

    @DisplayName("승인되었지만 진행중이지 않은 면담을 종료하면 예외가 발생한다.")
    @Test
    void updateReservationStatusToDone_unableToDoneException_approved() {
        Reservation reservation = new Reservation(schedule, DomainFixture.CREW);
        reservation.confirm(승인을_한다);

        assertThatThrownBy(reservation::updateReservationStatusToDone)
                .isInstanceOf(UnableToDoneReservationException.class);
    }

    @DisplayName("이미 종료된 면담을 종료하면 예외가 발생한다.")
    @Test
    void updateReservationStatusToDone_unableToDoneException_done() {
        Reservation reservation = new Reservation(schedule, DomainFixture.CREW);
        reservation.confirm(승인을_한다);
        reservation.updateReservationStatusToInProgress();
        reservation.updateReservationStatusToDone();

        assertThatThrownBy(reservation::updateReservationStatusToDone)
                .isInstanceOf(UnableToDoneReservationException.class);
    }

    @DisplayName("이미 제출된 면담 시트를 제출하면 예외를 반환한다.")
    @Test
    void updateSheetStatusToSubmitted_alreadySubmitted() {
        Reservation reservation = new Reservation(schedule, DomainFixture.CREW);
        reservation.updateSheetStatusToSubmitted();

        assertThatThrownBy(reservation::updateSheetStatusToSubmitted)
                .isInstanceOf(UnableToSubmitSheetException.class);
    }

    @DisplayName("면담 시트를 제출된 상태로 변경한다.")
    @Test
    void updateSheetStatusToSubmitted() {
        Reservation reservation = new Reservation(schedule, DomainFixture.CREW);

        reservation.updateSheetStatusToSubmitted();

        assertThat(reservation.getSheetStatus()).isEqualTo(SUBMITTED);
    }
}
