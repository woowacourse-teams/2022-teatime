package com.woowacourse.teatime.teatime.service;

import com.woowacourse.teatime.teatime.exception.SlackAlarmException;
import com.woowacourse.teatime.teatime.service.dto.poke.PokeAlarmInfoDto;
import com.woowacourse.teatime.teatime.service.dto.poke.PokeSlackAlarmDto;
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
public class PokeAlarmService {

    @Value("${slack.bot.secret-key}")
    private String botSecretKey;
    private final WebClient botClient;

    @Async
    public void sendPoke(PokeAlarmInfoDto pokeAlarmDto) {
        PokeSlackAlarmDto pokeSlackAlarmDto = PokeSlackAlarmDto.pokeToCoach(pokeAlarmDto);
        requestAlarm(pokeSlackAlarmDto);
    }

    private void requestAlarm(PokeSlackAlarmDto alarmDto) {
        try {
            botClient.post()
                    .uri("/api/send")
                    .header("Authorization", botSecretKey)
                    .bodyValue(alarmDto)
                    .retrieve()
                    .bodyToMono(PokeSlackAlarmDto.class)
                    .then()
                    .subscribe();
        } catch (WebClientException e) {
            throw new SlackAlarmException();
        }
    }
}
