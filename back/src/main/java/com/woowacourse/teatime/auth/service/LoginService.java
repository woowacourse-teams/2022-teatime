package com.woowacourse.teatime.auth.service;


import static com.woowacourse.teatime.teatime.domain.Role.COACH;
import static com.woowacourse.teatime.teatime.domain.Role.CREW;

import com.woowacourse.teatime.auth.controller.dto.LoginRequest;
import com.woowacourse.teatime.auth.controller.dto.UserAuthDto;
import com.woowacourse.teatime.auth.domain.UserAuthInfo;
import com.woowacourse.teatime.auth.infrastructure.JwtTokenProvider;
import com.woowacourse.teatime.auth.infrastructure.OpenIdAuth;
import com.woowacourse.teatime.teatime.controller.dto.request.SheetQuestionUpdateRequest;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.repository.CoachRepository;
import com.woowacourse.teatime.teatime.repository.CrewRepository;
import com.woowacourse.teatime.teatime.service.QuestionService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class LoginService {

    private static final String COACH_EMAIL_DOMAIN = "woowahan";
    private static final String DEFAULT_QUESTION_1 = "이번 면담을 통해 논의하고 싶은 내용";
    private static final String DEFAULT_QUESTION_2 = "최근에 자신이 긍정적으로 보는 시도와 변화";
    private static final String DEFAULT_QUESTION_3 = "이번 면담을 통해 생기기를 원하는 변화";

    private final OpenIdAuth openIdAuth;
    private final CrewRepository crewRepository;
    private final CoachRepository coachRepository;
    private final QuestionService questionService;
    private final UserAuthService userAuthService;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${coaches}")
    private List<String> emails;

    public UserAuthDto login(LoginRequest loginRequest) {
        String code = loginRequest.getCode();
        String accessToken = openIdAuth.getAccessToken(code);
        UserInfoDto userInfo = openIdAuth.getUserInfo(accessToken);
        return getLoginResponse(userInfo);
    }

    private UserAuthDto getLoginResponse(UserInfoDto userInfo) {
        String email = userInfo.getEmail();
        String emailDomain = StringUtils.substringBetween(email, "@", ".");

        List<String> emails = getEmails();
        if (COACH_EMAIL_DOMAIN.equals(emailDomain) || emails.contains(email)) {
            return getCoachLoginResponse(userInfo);
        }
        return getCrewLoginResponse(userInfo);
    }

    private List<String> getEmails() {
        return emails.stream()
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private UserAuthDto getCoachLoginResponse(UserInfoDto userInfo) {
        Coach coach = coachRepository.findByEmail(userInfo.getEmail())
                .orElseGet(() -> saveCoachAndDefaultQuestions(userInfo));
        coach.setSlackId(userInfo.getSlackId());
        coach.setImage(userInfo.getImage());

        Map<String, Object> claims = Map.of("id", coach.getId(), "role", COACH);
        String accessToken = jwtTokenProvider.createToken(claims);
        String refreshToken = UUID.randomUUID().toString();
        userAuthService.save(new UserAuthInfo(refreshToken, accessToken, coach.getId(), COACH.name()));
        return new UserAuthDto(accessToken, refreshToken, COACH, coach.getImage(), coach.getName());
    }

    @NotNull
    private Coach saveCoachAndDefaultQuestions(UserInfoDto userInfo) {
        Coach coach = coachRepository.save(new Coach(
                userInfo.getSlackId(),
                userInfo.getName(),
                userInfo.getEmail(),
                userInfo.getImage()));

        List<SheetQuestionUpdateRequest> defaultQuestionDtos = List.of(
                new SheetQuestionUpdateRequest(1, DEFAULT_QUESTION_1, true),
                new SheetQuestionUpdateRequest(2, DEFAULT_QUESTION_2, true),
                new SheetQuestionUpdateRequest(3, DEFAULT_QUESTION_3, true));

        questionService.update(coach.getId(), defaultQuestionDtos);
        return coach;
    }

    private UserAuthDto getCrewLoginResponse(UserInfoDto userInfo) {
        Crew crew = crewRepository.findByEmail(userInfo.getEmail())
                .orElseGet(() -> saveCrew(userInfo));
        crew.setSlackId(userInfo.getSlackId());
        crew.setImage(userInfo.getImage());

        Map<String, Object> claims = Map.of("id", crew.getId(), "role", CREW);
        String accessToken = jwtTokenProvider.createToken(claims);
        String refreshToken = UUID.randomUUID().toString();
        userAuthService.save(new UserAuthInfo(refreshToken, accessToken, crew.getId(), CREW.name()));
        return new UserAuthDto(accessToken, refreshToken, CREW, crew.getImage(), crew.getName());
    }

    @NotNull
    private Crew saveCrew(UserInfoDto userInfo) {
        Crew newCrew = new Crew(
                userInfo.getSlackId(),
                userInfo.getName(),
                userInfo.getEmail(),
                userInfo.getImage());
        return crewRepository.save(newCrew);
    }
}
