package com.woowacourse.teatime.teatime.service;

import static com.woowacourse.teatime.util.Date.findLastTime;

import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.teatime.service.dto.AlarmInfoDto;
import com.woowacourse.teatime.teatime.service.dto.SlackAlarmDto;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Transactional
@Service
public class AlarmService {

    @Value("${slack.bot.secret-key}")
    private String botSecretKey;
    private final WebClient botClient;
    private final ReservationRepository reservationRepository;

    public void send(AlarmInfoDto alarmInfoDto, AlarmTitle alarmTitle) {
        List<SlackAlarmDto> alarmDtos = SlackAlarmDto.of(alarmInfoDto, alarmTitle);
        for (SlackAlarmDto alarmDto : alarmDtos) {
            requestAlarm(alarmDto);
        }
    }

    public void remindReservation() {
        LocalDate date = LocalDate.now().plusDays(1);
        List<Reservation> reservations = reservationRepository.findAllApprovedReservationsBefore(findLastTime(date));
        for (Reservation reservation : reservations) {
            requestAlarm(SlackAlarmDto.remindCoach(reservation));
            requestAlarm(SlackAlarmDto.remindCrew(reservation));
        }
    }

    private void requestAlarm(SlackAlarmDto alarmDto) {
        botClient.post()
                .uri("/api/send")
                .header("Authorization", botSecretKey)
                .bodyValue(alarmDto)
                .retrieve()
                .bodyToMono(SlackAlarmDto.class)
                .block();
    }
}
