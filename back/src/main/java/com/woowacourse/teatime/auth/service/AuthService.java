package com.woowacourse.teatime.auth.service;


import static com.woowacourse.teatime.teatime.domain.Role.COACH;
import static com.woowacourse.teatime.teatime.domain.Role.CREW;

import com.woowacourse.teatime.auth.controller.dto.LoginRequest;
import com.woowacourse.teatime.auth.controller.dto.LoginResponse;
import com.woowacourse.teatime.auth.infrastructure.JwtTokenProvider;
import com.woowacourse.teatime.auth.infrastructure.OpenIdAuth;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.repository.CoachRepository;
import com.woowacourse.teatime.teatime.repository.CrewRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {

    private static final String COACH_EMAIL_DOMAIN = "woowahan";

    private final OpenIdAuth openIdAuth;
    private final CrewRepository crewRepository;
    private final CoachRepository coachRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse login(LoginRequest loginRequest) {
        String code = loginRequest.getCode();
        String accessToken = openIdAuth.getAccessToken(code);
        UserInfoDto userInfo = openIdAuth.getUserInfo(accessToken);
        return getLoginResponse(userInfo);
    }

    private LoginResponse getLoginResponse(UserInfoDto userInfo) {
        String email = userInfo.getEmail();
        String emailDomain = StringUtils.substringBetween(email, "@", ".");

        if (COACH_EMAIL_DOMAIN.equals(emailDomain)) {
            return new LoginResponse(getCoachToken(userInfo), COACH);
        }
        return new LoginResponse(getCrewToken(userInfo), CREW);
    }

    private String getCoachToken(UserInfoDto userInfo) {
        Coach coach = coachRepository.findByEmail(userInfo.getEmail())
                .orElseGet(() -> {
                    Coach newCoach = new Coach(
                            userInfo.getName(),
                            userInfo.getEmail(),
                            userInfo.getImage());
                    return coachRepository.save(newCoach);
                });
        Map<String, Object> claims = Map.of("id", coach.getId(), "role", COACH);
        return jwtTokenProvider.createToken(claims);
    }

    private String getCrewToken(UserInfoDto userInfo) {
        Crew crew = crewRepository.findByEmail(userInfo.getEmail())
                .orElseGet(() -> {
                    Crew newCrew = new Crew(
                            userInfo.getName(),
                            userInfo.getEmail(),
                            userInfo.getImage());
                    return crewRepository.save(newCrew);
                });
        Map<String, Object> claims = Map.of("id", crew.getId(), "role", CREW);
        return jwtTokenProvider.createToken(claims);
    }
}
