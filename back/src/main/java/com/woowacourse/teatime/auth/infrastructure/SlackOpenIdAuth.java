package com.woowacourse.teatime.auth.infrastructure;


import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.openid.connect.OpenIDConnectTokenRequest;
import com.slack.api.methods.request.openid.connect.OpenIDConnectUserInfoRequest;
import com.slack.api.methods.response.openid.connect.OpenIDConnectTokenResponse;
import com.slack.api.methods.response.openid.connect.OpenIDConnectUserInfoResponse;
import com.woowacourse.teatime.auth.exception.SlackLoginException;
import com.woowacourse.teatime.auth.service.UserInfoDto;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SlackOpenIdAuth implements OpenIdAuth {

    private final MethodsClient slackClient;

    @Value("${slack.clientId}")
    private String clientId;

    @Value("${slack.clientSecret}")
    private String clientSecret;

    @Value("${slack.redirectUrl}")
    private String redirectUrl;

    public String getAccessToken(String code) {
        OpenIDConnectTokenRequest request = OpenIDConnectTokenRequest.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .code(code)
                .redirectUri(redirectUrl)
                .build();

        try {
            OpenIDConnectTokenResponse response = slackClient.openIDConnectToken(request);
            return response.getAccessToken();
        } catch (SlackApiException | IOException e) {
            log.warn("token response error : " + e.getMessage());
            throw new SlackLoginException();
        }
    }

    public UserInfoDto getUserInfo(String accessToken) {
        OpenIDConnectUserInfoRequest request = OpenIDConnectUserInfoRequest.builder()
                .token(accessToken)
                .build();

        try {
            OpenIDConnectUserInfoResponse response = slackClient.openIDConnectUserInfo(request);
            return new UserInfoDto(response.getUserId(), response.getName(), response.getEmail(), response.getUserImage192());
        } catch (SlackApiException | IOException e) {
            log.warn("user info response error : " + e.getMessage());
            throw new SlackLoginException();
        }
    }
}
