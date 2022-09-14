package com.woowacourse.teatime.teatime.infrastructure;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.woowacourse.teatime.teatime.exception.SlackAlarmException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SlackAlarm implements Alarm {

    private final MethodsClient slackClient;

    @Value("${slack.token}")
    private String token;

    @Override
    public void sendMessage(String userId, String message) {
        try {
            slackClient.chatPostMessage(ChatPostMessageRequest.builder()
                    .token(token)
                    .channel(userId)
                    .text(message)
                    .build());
        } catch (IOException | SlackApiException e) {
            log.warn("send message request error : " + e.getMessage());
            throw new SlackAlarmException();
        }
    }
}
