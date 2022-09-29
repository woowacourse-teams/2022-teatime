package com.woowacourse.teatime.auth.service;


import static com.woowacourse.teatime.teatime.domain.Role.COACH;
import static com.woowacourse.teatime.teatime.domain.Role.CREW;

import com.woowacourse.teatime.auth.controller.dto.LoginRequest;
import com.woowacourse.teatime.auth.controller.dto.LoginResponse;
import com.woowacourse.teatime.auth.infrastructure.JwtTokenProvider;
import com.woowacourse.teatime.auth.infrastructure.OpenIdAuth;
import com.woowacourse.teatime.teatime.controller.dto.request.SheetQuestionUpdateDto;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.repository.CoachRepository;
import com.woowacourse.teatime.teatime.repository.CrewRepository;
import com.woowacourse.teatime.teatime.service.QuestionService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {

    private static final String COACH_EMAIL_DOMAIN = "woowahan";
    private static final String ADMIN_EMAIL = "teatime";
    private static final String DEFAULT_QUESTION_1 = "이번 면담을 통해 논의하고 싶은 내용";
    private static final String DEFAULT_QUESTION_2 = "최근에 자신이 긍정적으로 보는 시도와 변화";
    private static final String DEFAULT_QUESTION_3 = "이번 면담을 통해 생기기를 원하는 변화";

    private final OpenIdAuth openIdAuth;
    private final CrewRepository crewRepository;
    private final CoachRepository coachRepository;
    private final QuestionService questionService;
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

        if (COACH_EMAIL_DOMAIN.equals(emailDomain) || email.contains(ADMIN_EMAIL)) {
            return getCoachLoginResponse(userInfo);
        }
        return getCrewLoginResponse(userInfo);
    }

    private LoginResponse getCoachLoginResponse(UserInfoDto userInfo) {
        Coach coach = coachRepository.findByEmail(userInfo.getEmail())
                .orElseGet(() -> saveCoachAndDefaultQuestions(userInfo));
        coach.setSlackId(userInfo.getSlackId());
        coach.setImage(userInfo.getImage());

        Map<String, Object> claims = Map.of("id", coach.getId(), "role", COACH);
        String token = jwtTokenProvider.createToken(claims);
        return new LoginResponse(token, COACH, coach.getImage(), coach.getName());
    }

    @NotNull
    private Coach saveCoachAndDefaultQuestions(UserInfoDto userInfo) {
        Coach coach = coachRepository.save(new Coach(
                userInfo.getSlackId(),
                userInfo.getName(),
                userInfo.getEmail(),
                userInfo.getImage()));

        List<SheetQuestionUpdateDto> defaultQuestionDtos = List.of(
                new SheetQuestionUpdateDto(1, DEFAULT_QUESTION_1),
                new SheetQuestionUpdateDto(2, DEFAULT_QUESTION_2),
                new SheetQuestionUpdateDto(3, DEFAULT_QUESTION_3));

        questionService.updateQuestions(coach.getId(), defaultQuestionDtos);
        return coach;
    }

    private LoginResponse getCrewLoginResponse(UserInfoDto userInfo) {
        Crew crew = crewRepository.findByEmail(userInfo.getEmail())
                .orElseGet(() -> saveCrew(userInfo));
        crew.setSlackId(userInfo.getSlackId());
        crew.setImage(userInfo.getImage());

        Map<String, Object> claims = Map.of("id", crew.getId(), "role", CREW);
        String token = jwtTokenProvider.createToken(claims);
        return new LoginResponse(token, CREW, crew.getImage(), crew.getName());
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
