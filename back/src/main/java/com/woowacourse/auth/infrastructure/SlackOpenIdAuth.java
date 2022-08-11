package com.woowacourse.auth.infrastructure;


import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.impl.MethodsClientImpl;
import com.slack.api.methods.request.openid.connect.OpenIDConnectTokenRequest;
import com.slack.api.methods.request.openid.connect.OpenIDConnectUserInfoRequest;
import com.slack.api.methods.response.openid.connect.OpenIDConnectTokenResponse;
import com.slack.api.methods.response.openid.connect.OpenIDConnectUserInfoResponse;
import com.woowacourse.auth.service.UserInfoDto;
import com.woowacourse.auth.exception.SlackException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SlackOpenIdAuth implements OpenIdAuth {

    private final MethodsClientImpl methodsClient;

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
            OpenIDConnectTokenResponse response = methodsClient.openIDConnectToken(request);
            return response.getAccessToken();
        } catch (SlackApiException | IOException e) {
            throw new SlackException();
        }
    }

    public UserInfoDto getUserInfo(String accessToken) {
        OpenIDConnectUserInfoRequest request = OpenIDConnectUserInfoRequest.builder()
                .token(accessToken)
                .build();

        try {
            OpenIDConnectUserInfoResponse response = methodsClient.openIDConnectUserInfo(request);
            return new UserInfoDto(response.getName(), response.getEmail(), response.getUserImage24());
        } catch (SlackApiException | IOException e) {
            throw new SlackException();
        }
    }
}
