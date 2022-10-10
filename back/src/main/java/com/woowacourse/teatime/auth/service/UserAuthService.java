package com.woowacourse.teatime.auth.service;

import com.woowacourse.teatime.auth.controller.dto.GenerateTokenDto;
import com.woowacourse.teatime.auth.domain.UserAuthInfo;
import com.woowacourse.teatime.auth.exception.WrongCookieTokenException;
import com.woowacourse.teatime.auth.infrastructure.JwtTokenProvider;
import com.woowacourse.teatime.auth.repository.UserAuthInfoRepository;
import com.woowacourse.teatime.auth.support.dto.UserRoleDto;
import com.woowacourse.teatime.exception.UnAuthorizedException;
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

    public GenerateTokenDto generateToken(Cookie cookie, UserRoleDto userRoleDto) {
        String refreshToken = getRefreshToken(cookie);
        UserAuthInfo userAuthInfo = find(refreshToken);

        validateUserAuthInfo(userAuthInfo, refreshToken, userRoleDto);
        userAuthInfoRepository.delete(userAuthInfo);

        String newRefreshToken = UUID.randomUUID().toString();
        save(new UserAuthInfo(newRefreshToken, userRoleDto.getId(), userRoleDto.getRole()));

        String accessToken = generateAccessToken(userRoleDto);
        return new GenerateTokenDto(accessToken, newRefreshToken);
    }

    private String getRefreshToken(Cookie cookie) {
        if (cookie == null) {
            throw new WrongCookieTokenException();
        }
        return cookie.getValue();
    }

    private UserAuthInfo find(String refreshToken) {
        return userAuthInfoRepository.findById(refreshToken)
                .orElseThrow(WrongCookieTokenException::new);
    }

    private void validateUserAuthInfo(UserAuthInfo userAuthInfo, String refreshToken, UserRoleDto userRoleDto) {
        if (!userAuthInfo.isSameInfo(refreshToken, userRoleDto.getId(), userRoleDto.getRole())) {
            throw new UnAuthorizedException();
        }
    }

    private String generateAccessToken(UserRoleDto userRoleDto) {
        Map<String, Object> claims =
                Map.of("id", userRoleDto.getId(), "role", Role.valueOf(userRoleDto.getRole()));
        return jwtTokenProvider.createToken(claims);
    }
}
