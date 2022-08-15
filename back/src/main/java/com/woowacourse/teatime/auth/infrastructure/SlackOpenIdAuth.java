package com.woowacourse.teatime.auth.infrastructure;


import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.openid.connect.OpenIDConnectTokenRequest;
import com.slack.api.methods.request.openid.connect.OpenIDConnectUserInfoRequest;
import com.slack.api.methods.response.openid.connect.OpenIDConnectTokenResponse;
import com.slack.api.methods.response.openid.connect.OpenIDConnectUserInfoResponse;
import com.woowacourse.teatime.auth.service.UserInfoDto;
import com.woowacourse.teatime.auth.exception.SlackException;
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

        log.info("token request : " + request.getClientId() + request.getClientSecret() + request.getCode() + request.getRedirectUri());
        try {
            OpenIDConnectTokenResponse response = slackClient.openIDConnectToken(request);
            log.info("token response error : " + response.getError());
            log.info("token response : " + response.getAccessToken());
            return response.getAccessToken();
        } catch (SlackApiException | IOException e) {
            throw new SlackException();
        }
    }

    public UserInfoDto getUserInfo(String accessToken) {
        OpenIDConnectUserInfoRequest request = OpenIDConnectUserInfoRequest.builder()
                .token(accessToken)
                .build();

        log.info("user info request : " + request.getToken());
        try {
            OpenIDConnectUserInfoResponse response = slackClient.openIDConnectUserInfo(request);
            log.info("user info response error : " + response.getError());
            log.info("user info response : " + response.getName() + response.getEmail() + response.getUserImage24());
            return new UserInfoDto(response.getName(), response.getEmail(), response.getUserImage24());
        } catch (SlackApiException | IOException e) {
            throw new SlackException();
        }
    }
}
