package com.woowacourse.teatime.teatime.infrastructure;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.model.Attachment;
import com.woowacourse.teatime.teatime.exception.SlackAlarmException;
import java.io.IOException;
import java.util.List;
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
    public void sendMessage(String userId, String title, String message, String barColor) {
        try {
            List<Attachment> attachments = List.of(
                    Attachment.builder()
                            .text(message)
                            .color(barColor)
                            .build());

            slackClient.chatPostMessage(ChatPostMessageRequest.builder()
                    .token(token)
                    .channel(userId)
                    .text(title)
                    .attachments(attachments)
                    .build());
        } catch (IOException | SlackApiException e) {
            log.error("send message request error : " + e.getMessage());
            throw new SlackAlarmException();
        }
    }

    @Override
    public void sendGroupMessage(List<String> userIds, String title, String message, String barColor) {
        for (String userId : userIds) {
            sendMessage(userId, title, message, barColor);
        }
    }
}
