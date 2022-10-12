package com.woowacourse.teatime.teatime.service;

import static com.woowacourse.teatime.util.Date.findFirstTime;
import static com.woowacourse.teatime.util.Date.findLastTime;

import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.exception.SlackAlarmException;
import com.woowacourse.teatime.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.teatime.service.dto.AlarmInfoDto;
import com.woowacourse.teatime.teatime.service.dto.AlarmTargetDto;
import com.woowacourse.teatime.teatime.service.dto.SlackAlarmDto;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AlarmService {

    @Value("${slack.bot.secret-key}")
    private String botSecretKey;
    private final WebClient botClient;
    private final ReservationRepository reservationRepository;

    @Async
    public void send(AlarmInfoDto alarmInfoDto, AlarmTitle alarmTitle) {
        List<SlackAlarmDto> alarmDtos = SlackAlarmDto.of(alarmInfoDto, alarmTitle);
        for (SlackAlarmDto alarmDto : alarmDtos) {
            requestAlarm(alarmDto);
        }
    }

    public void remindReservation() {
        LocalDate date = LocalDate.now().plusDays(1);
        List<Reservation> reservations = reservationRepository.findAllApprovedReservationsBetween(findFirstTime(date),
                findLastTime(date));
        for (Reservation reservation : reservations) {
            requestAlarm(SlackAlarmDto.alarmToCoach(reservation, AlarmTitle.REMIND_COACH));
            requestAlarm(SlackAlarmDto.alarmToCrew(reservation, AlarmTitle.REMIND_CREW));
        }
    }

    @Async
    public void alertSheetSubmitted(List<AlarmTargetDto> alarmTargets) {
        for (AlarmTargetDto alarmTarget : alarmTargets) {
            SlackAlarmDto crewAlarmDto = SlackAlarmDto.alarmTo(alarmTarget, AlarmTitle.SUBMIT_SHEET_TO_CREW);
            requestAlarm(crewAlarmDto);
        }
    }

    private void requestAlarm(SlackAlarmDto alarmDto) {
        try {
            botClient.post()
                    .uri("/api/send")
                    .header("Authorization", botSecretKey)
                    .bodyValue(alarmDto)
                    .retrieve()
                    .bodyToMono(SlackAlarmDto.class)
                    .then()
                    .subscribe();
        } catch (WebClientException e) {
            throw new SlackAlarmException();
        }
    }
}
