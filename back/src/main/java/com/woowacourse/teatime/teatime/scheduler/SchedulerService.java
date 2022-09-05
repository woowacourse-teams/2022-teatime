package com.woowacourse.teatime.teatime.scheduler;

import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.domain.ReservationStatus;
import com.woowacourse.teatime.teatime.domain.Role;
import com.woowacourse.teatime.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class SchedulerService {

    private static final long ONE_DAY = 1L;
    private final ReservationRepository reservationRepository;

    @Scheduled(cron = "0 0/30 10-18 * * *")
    public void updateReservationStatusToInProgress() {
        log.info("{}, 승인된 예약을 진행중인 예약으로 변경하는 스케쥴러 실행", LocalDateTime.now());
        List<Reservation> reservations
                = reservationRepository.findAllByReservationStatus(ReservationStatus.APPROVED);

        for (Reservation reservation : reservations) {
            reservation.updateReservationStatusToInProgress();
        }
    }

    @Scheduled(cron = "59 59 23 * * *")
    public void cancelReservationNotSubmitted() {
        LocalDateTime now = LocalDateTime.now();
        log.info("{}, 전날까지 면담내용을 제출하지 않았다면 면담을 취소하는 스케쥴러 실행", now);

        LocalDate nowDate = now.toLocalDate();
        LocalDate nextDay = nowDate.plusDays(ONE_DAY);
        LocalDateTime firstTime = Date.findFirstTime(nextDay);
        LocalDateTime lastTime = Date.findLastTime(nextDay);

        List<Reservation> reservations
                = reservationRepository.findAllShouldBeCanceled(firstTime, lastTime);

        for (Reservation reservation : reservations) {
            reservation.cancel(Role.COACH);
            reservationRepository.delete(reservation);
        }
    }
}
