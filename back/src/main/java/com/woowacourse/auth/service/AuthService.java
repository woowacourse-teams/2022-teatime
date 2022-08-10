package com.woowacourse.auth.service;

import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.impl.MethodsClientImpl;
import com.slack.api.methods.request.openid.connect.OpenIDConnectTokenRequest;
import com.slack.api.methods.request.openid.connect.OpenIDConnectUserInfoRequest;
import com.slack.api.methods.response.openid.connect.OpenIDConnectTokenResponse;
import com.slack.api.methods.response.openid.connect.OpenIDConnectUserInfoResponse;
import com.woowacourse.auth.controller.dto.TokenResponse;
import com.woowacourse.auth.support.JwtTokenProvider;
import com.woowacourse.teatime.domain.Crew;
import com.woowacourse.teatime.exception.SlackException;
import com.woowacourse.teatime.repository.CrewRepository;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {

    private final CrewRepository crewRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final MethodsClientImpl methodsClient;

    @Value("${slack.clientId}")
    private String clientId;

    @Value("${slack.clientSecret}")
    private String clientSecret;

    @Value("${slack.redirectUrl}")
    private String redirectUrl;

    public TokenResponse login(String code) {
        OpenIDConnectTokenResponse openIdTokenResponse = getTokenResponse(code);
        OpenIDConnectUserInfoResponse openIdUserInfoResponse = getUserInfoResponse(
                openIdTokenResponse.getAccessToken());

        Crew crew = findCrew(openIdUserInfoResponse);

        Map<String, Object> claims = Map.of("id", crew.getId(), "email", crew.getEmail());
        String token = jwtTokenProvider.createToken(claims);
        return new TokenResponse(token);
    }

    private OpenIDConnectTokenResponse getTokenResponse(String code) {
        OpenIDConnectTokenRequest tokenRequest = OpenIDConnectTokenRequest.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .code(code)
                .redirectUri(redirectUrl)
                .build();

        try {
            return methodsClient.openIDConnectToken(tokenRequest);
        } catch (SlackApiException | IOException e) {
            throw new SlackException();
        }
    }

    private OpenIDConnectUserInfoResponse getUserInfoResponse(String accessToken) {
        OpenIDConnectUserInfoRequest userInfoRequest = OpenIDConnectUserInfoRequest.builder()
                .token(accessToken)
                .build();

        try {
            return methodsClient.openIDConnectUserInfo(userInfoRequest);
        } catch (SlackApiException | IOException e) {
            throw new SlackException();
        }
    }

    private Crew findCrew(OpenIDConnectUserInfoResponse openIdUserInfoResponse) {
        String email = openIdUserInfoResponse.getEmail();
        return crewRepository.findByEmail(email)
                .orElseGet(() -> {
                    Crew newCrew = new Crew(
                            openIdUserInfoResponse.getName(),
                            email,
                            openIdUserInfoResponse.getUserImage24());
                    return crewRepository.save(newCrew);
                });
    }
}
