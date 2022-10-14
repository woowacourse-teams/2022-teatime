package com.woowacourse.teatime.auth.service;

import com.woowacourse.teatime.auth.controller.dto.GenerateTokenDto;
import com.woowacourse.teatime.auth.domain.UserAuthInfo;
import com.woowacourse.teatime.auth.exception.WrongTokenException;
import com.woowacourse.teatime.auth.infrastructure.JwtTokenProvider;
import com.woowacourse.teatime.auth.repository.UserAuthInfoRepository;
import com.woowacourse.teatime.teatime.domain.Role;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserAuthService {

    private final UserAuthInfoRepository userAuthInfoRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public void save(UserAuthInfo userAuthInfo) {
        userAuthInfoRepository.save(userAuthInfo);
    }

    public GenerateTokenDto generateToken(Cookie cookie, String accessToken) {
        String refreshToken = getRefreshToken(cookie);
        UserAuthInfo userAuthInfo = find(refreshToken);

        validateUserAuthInfo(userAuthInfo, refreshToken, accessToken);
        userAuthInfoRepository.delete(userAuthInfo);

        String newRefreshToken = UUID.randomUUID().toString();
        String newAccessToken = generateAccessToken(userAuthInfo);

        save(new UserAuthInfo(newRefreshToken, newAccessToken, userAuthInfo.getUserId(), userAuthInfo.getRole()));

        return new GenerateTokenDto(newAccessToken, newRefreshToken);
    }

    private String getRefreshToken(Cookie cookie) {
        if (cookie == null) {
            throw new WrongTokenException();
        }
        return cookie.getValue();
    }

    public UserAuthInfo find(String refreshToken) {
        return userAuthInfoRepository.findById(refreshToken)
                .orElseThrow(WrongTokenException::new);
    }

    private void validateUserAuthInfo(UserAuthInfo userAuthInfo, String refreshToken, String accessToken) {
        if (!userAuthInfo.isSameToken(refreshToken, accessToken)) {
            throw new WrongTokenException();
        }
    }

    private String generateAccessToken(UserAuthInfo userAuthInfo) {
        Map<String, Object> claims =
                Map.of("id", userAuthInfo.getUserId(), "role", Role.valueOf(userAuthInfo.getRole()));
        return jwtTokenProvider.createToken(claims);
    }
}
