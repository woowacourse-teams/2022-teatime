package com.woowacourse.teatime.teatime.infrastructure;

import com.woowacourse.teatime.teatime.service.ReservationService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class Scheduler {

    private final ReservationService reservationService;

    @Scheduled(cron = "0 0/30 10-18 * * *")
    public void updateReservationStatusToInProgress() {
        log.info("{}, 승인된 예약을 진행중인 예약으로 변경하는 스케쥴러 실행", LocalDateTime.now());
        reservationService.updateReservationStatusToInProgress();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void cancelReservationNotSubmitted() {
        log.info("{}, 전날까지 면담내용을 제출하지 않았다면 면담을 취소하는 스케쥴러 실행", LocalDateTime.now());
        reservationService.cancelReservationNotSubmitted();
    }
}
